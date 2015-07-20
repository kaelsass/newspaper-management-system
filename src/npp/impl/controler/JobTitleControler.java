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

import npp.dto.JobTitleDto;
import npp.faces.JobTitleListModel;
import npp.spec.manager.SessionManager;
import npp.spec.service.JobTitleService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Named
@SessionScoped
public class JobTitleControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private List<JobTitleDto> baseList = null;
	private JobTitleListModel listModel = null;

	private int first;

	private JobTitleDto editDto;

	private List<JobTitleDto> selecteds;

	private boolean addMode = false;
	private boolean editMode = false;
	private boolean deleteMode = false;

	@Inject
	private JobTitleService jobTitleService;
	@Inject
	private SessionManager sessionManager;

	@PostConstruct
	public void init()
	{
		editDto = new JobTitleDto();
	}

	public JobTitleListModel getListModel() {
		if (baseList == null) {
			try {
				baseList = jobTitleService.getAllList();
				listModel = new JobTitleListModel(baseList);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return baseList == null ? new JobTitleListModel(
				new ArrayList<JobTitleDto>()) : listModel;
	}

	public void startAdd() {
		addMode = true;
		editDto = new JobTitleDto();
	}
	public void startDelete()
	{
		editMode = true;
		deleteMode = true;
	}

	public void startEdit(ActionEvent event) {
		editMode = true;
		JobTitleDto selectedDto = (JobTitleDto) event.getComponent()
				.getAttributes().get("jobTitle");
		editDto = new JobTitleDto(selectedDto);
	}

	public int getFirst() {
		return first;
	}

	public void nameValidate(FacesContext context,
			UIComponent component, Object value) {
		String name = (String) value;
		if(editDto.getName().equals(name))
			return;
		for (JobTitleDto dto : getBaseList()) {
			if (dto.getName().equals(name)) {
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_FATAL,
						"Job Title has existed.", null));
			}
		}
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public void apply() throws IOException
	{
		if(addMode)
			jobTitleService.add(editDto);
		else if(editMode)
			jobTitleService.update(editDto);
		clear();
	}

	public void delete() throws IOException {
		try {
			for (JobTitleDto dto : selecteds) {
				jobTitleService.delete(dto.getSeq());
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
		editDto = new JobTitleDto();
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

	public List<JobTitleDto> getBaseList(){
		if (baseList == null) {
			try {
				baseList = jobTitleService.getAllList();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			listModel = new JobTitleListModel(baseList);
		}
		return baseList;
	}

	public void setListModel(JobTitleListModel listModel) {
		this.listModel = listModel;
	}

	public void setBaseList(List<JobTitleDto> baseList) {
		this.baseList = baseList;
	}

	public List<JobTitleDto> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<JobTitleDto> selecteds) {
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

	public JobTitleDto getEditDto() {
		return editDto;
	}

	public void setEditDto(JobTitleDto editDto) {
		this.editDto = editDto;
	}

	// public EmployeeListModel getExampleList() {
	// ArrayList<EmployeeDto> exlist = new ArrayList<EmployeeDto>();
	// exlist.add(new EmployeeDto("10001", "Allen", "Jackson", new SexDto(1,
	// "M"), 26, "15100001111", "allen@mail.worksap.com",
	// new DepartmentDto(1, "Technical Division"), new JobTitleDto(1,
	// "In Service")));
	// return new EmployeeListModel(exlist);
	//
	// }
}
