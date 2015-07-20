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

import npp.condition.DynamicQuery;
import npp.dto.EducationDto;
import npp.faces.EducationListModel;
import npp.spec.manager.SessionManager;
import npp.spec.service.EducationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Named
@SessionScoped
public class EducationControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 469282104365892582L;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private List<EducationDto> baseList = null;
	private EducationListModel listModel = null;

	private int first;
	private boolean addMode;
	private boolean editMode;
	private boolean deleteMode;

	private List<EducationDto> selecteds;
	private EducationDto editDto;

	@Inject
	private EducationService educationService;
	@Inject
	private SessionManager sessionManager;

	@PostConstruct
	public void init() {
		addMode = false;
		editMode = false;
		deleteMode = false;
		editDto = new EducationDto();
	}

	public EducationListModel getListModel() {
		if (baseList == null) {
			try {
				baseList = educationService.getAllList(new DynamicQuery());
				listModel = new EducationListModel(baseList);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return baseList == null ? new EducationListModel(
				new ArrayList<EducationDto>()) : listModel;
	}

	public void startAdd()
	{
		editDto = new EducationDto();
		addMode = true;
	}
	public void startEdit(ActionEvent e)
	{
		EducationDto selectedStatus = (EducationDto)e.getComponent().getAttributes().get("education");
		editDto = new EducationDto(selectedStatus);
		addMode = false;
		editMode = true;
	}
	public void startDelete()
	{
		deleteMode = true;
	}
	public void endDelete()
	{
		deleteMode = false;
	}
	public void apply() throws IOException
	{
		if(addMode)
			educationService.add(editDto);
		else if(editMode)
			educationService.update(editDto);
		clear();
	}

	public void clear()
	{
		baseList = null;
		editDto = new EducationDto();
		selecteds = null;
		addMode = false;
		editMode = false;
		deleteMode = false;
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public void delete() throws IOException {
		try {
			for (EducationDto status : selecteds) {
				educationService.delete(status.getSeq());
			}
			clear();
		} catch (IOException e) {
			logger.error("Employee Date delete error.", e);
			sessionManager.addGlobalMessageFatal("Employee Data delete error.",
					null);
			throw e;
		}
	}

	public int getStatusListLength() {
		if (listModel == null)
			return 0;
		return listModel.getRowCount();
	}

	public List<EducationDto> getBaseList() {
		if (baseList == null)
		{
			try {
				baseList = educationService.getAllList(new DynamicQuery());
				listModel = new EducationListModel(baseList);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return baseList;
	}

	public void setBaseList(List<EducationDto> baseList) {
		this.baseList = baseList;
	}

	public EducationDto getEditStatus() {
		return editDto;
	}

	public void setEditStatus(EducationDto editStatus) {
		this.editDto = editStatus;
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

	public boolean isBrowseMode()
	{
		return !addMode && !editMode;
	}

	public List<EducationDto> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<EducationDto> selecteds) {
		this.selecteds = selecteds;
	}

	public boolean isDeleteMode() {
		return deleteMode;
	}

	public void setDeleteMode(boolean deleteMode) {
		this.deleteMode = deleteMode;
	}

	public void nameValidate(FacesContext context,
			UIComponent component, Object value) {
		String name = (String) value;

		for (EducationDto dto : getBaseList()) {
			if (dto.getName().equals(name)) {
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_FATAL,
						"Employment Status has existed.", null));
			}
		}
	}

	public EducationDto getEditDto() {
		return editDto;
	}

	public void setEditDto(EducationDto editDto) {
		this.editDto = editDto;
	}
}
