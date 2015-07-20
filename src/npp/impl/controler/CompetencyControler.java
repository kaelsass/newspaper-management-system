package npp.impl.controler;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import npp.dto.CompetencyDto;
import npp.faces.CompetencyListModel;
import npp.spec.manager.SessionManager;
import npp.spec.service.CompetencyService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Named
@SessionScoped
public class CompetencyControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private List<CompetencyDto> baseList = null;
	private CompetencyListModel listModel = null;

	private CompetencyDto editDto;

	private List<CompetencyDto> selecteds;

	private boolean addMode = false;
	private boolean editMode = false;
	private boolean deleteMode = false;

	@Inject
	private CompetencyService competencyService;
	@Inject
	private SessionManager sessionManager;

	@PostConstruct
	public void init()
	{
		editDto = new CompetencyDto();
	}

	public CompetencyListModel getListModel() {
		if (baseList == null) {
			try {
				baseList = competencyService.getAllList();
				listModel = new CompetencyListModel(baseList);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return baseList == null ? new CompetencyListModel(
				new ArrayList<CompetencyDto>()) : listModel;
	}

	public void startAdd() {
		addMode = true;
		editDto = new CompetencyDto();
	}
	public void startDelete()
	{
		editMode = true;
		deleteMode = true;
	}

	public void startEdit(ActionEvent event) {
		CompetencyDto selectedDto = (CompetencyDto) event.getComponent()
				.getAttributes().get("competency");
		editDto = new CompetencyDto(selectedDto);
		editMode = true;
	}

	public void nameValidate(FacesContext context,
			UIComponent component, Object value) throws IOException {
		String name = (String) value;
		if(editDto.getName().equals(name)) //编辑时直接点保存会报name has existed error
			return;
		for (CompetencyDto dto : getBaseList()) {
			if (dto.getName().equals(name)) {
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_FATAL,
						"Competency Name has existed.", null));
			}
		}
	}

	public void apply() throws IOException
	{
		if(addMode)
			competencyService.add(editDto);
		else if(editMode)
			competencyService.update(editDto);
		clear();
	}

	public void delete() throws IOException {
		try {
			for (CompetencyDto dto : selecteds) {
				competencyService.delete(dto.getSeq());
			}
			clear();
		} catch (IOException e) {
			logger.error("Employee Date delete error.", e);
			sessionManager.addGlobalMessageFatal("Employee Data delete error.",
					null);
			throw e;
		}
		finally{
			deleteMode = false;
		}
	}
	public void endDelete()
	{
		deleteMode = false;
	}

	public void clear() {
		selecteds = null;
		editDto = new CompetencyDto();
		baseList = null;
		addMode = false;
		editMode = false;
		deleteMode = false;

	}

	public int getStatusListLength() {
		if (listModel == null)
			return 0;
		return listModel.getRowCount();
	}

	public List<CompetencyDto> getBaseList() {
		if (baseList == null) {
			try {
				baseList = competencyService.getAllList();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			listModel = new CompetencyListModel(baseList);
		}
		return baseList;
	}

	public CompetencyDto getEditJobTitle() {
		return editDto;
	}

	public void setEditJobTitle(CompetencyDto editJobTitle) {
		this.editDto = editJobTitle;
	}

	public void setListModel(CompetencyListModel listModel) {
		this.listModel = listModel;
	}

	public void setBaseList(List<CompetencyDto> baseList) {
		this.baseList = baseList;
	}

	public List<CompetencyDto> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<CompetencyDto> selecteds) {
		this.selecteds = selecteds;
	}

	public boolean isDeleteMode() {
		return deleteMode;
	}

	public void setDeleteMode(boolean deleteMode) {
		this.deleteMode = deleteMode;
	}

	public boolean isAddMode() {
		return addMode;
	}

	public void setAddMode(boolean addMode) {
		this.addMode = addMode;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public CompetencyDto getEditDto() {
		return editDto;
	}

	public void setEditDto(CompetencyDto editDto) {
		this.editDto = editDto;
	}

	// public EmployeeListModel getExampleList() {
	// ArrayList<EmployeeDto> exlist = new ArrayList<EmployeeDto>();
	// exlist.add(new EmployeeDto("10001", "Allen", "Jackson", new SexDto(1,
	// "M"), 26, "15100001111", "allen@mail.worksap.com",
	// new DepartmentDto(1, "Technical Division"), new CompetencyDto(1,
	// "In Service")));
	// return new EmployeeListModel(exlist);
	//
	// }
}
