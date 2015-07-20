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

import npp.dto.JobCategoryDto;
import npp.faces.JobCategoryListModel;
import npp.spec.manager.SessionManager;
import npp.spec.service.JobCategoryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Named
@SessionScoped
public class JobCategoryControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 469282104365892582L;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private List<JobCategoryDto> baseList = null;
	private JobCategoryListModel listModel = null;

	private int first;
	private boolean addMode;
	private boolean editMode;
	private boolean deleteMode;

	private List<JobCategoryDto> selecteds;
	private JobCategoryDto editDto;

	@Inject
	private JobCategoryService jobCategoryService;
	@Inject
	private SessionManager sessionManager;

	@PostConstruct
	public void init() {
		addMode = false;
		editMode = false;
		deleteMode = false;
		editDto = new JobCategoryDto();
	}

	public JobCategoryListModel getListModel() {
		if (baseList == null) {
			try {
				baseList = jobCategoryService.getAllList();
				listModel = new JobCategoryListModel(baseList);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return baseList == null ? new JobCategoryListModel(
				new ArrayList<JobCategoryDto>()) : listModel;
	}

	public void nameValidate(FacesContext context,
			UIComponent component, Object value) {
		String name = (String) value;
		if (baseList == null) {
			try {
				baseList = jobCategoryService.getAllList();
				listModel = new JobCategoryListModel(baseList);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		for (JobCategoryDto dto : baseList) {
			if (dto.getName().equals(name)) {
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_FATAL,
						"Job Category has existed.", null));
			}
		}
	}

	public void startAdd()
	{
		editDto = new JobCategoryDto();
		addMode = true;
	}
	public void startEdit(ActionEvent e)
	{
		JobCategoryDto selected = (JobCategoryDto)e.getComponent().getAttributes().get("jobCategory");
		editDto = new JobCategoryDto(selected);
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
			jobCategoryService.add(editDto);
		else if(editMode)
			jobCategoryService.update(editDto);
		clear();
	}

	public void clear()
	{
		baseList = null;
		editDto = new JobCategoryDto();
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
			for (JobCategoryDto status : selecteds) {
				jobCategoryService.delete(status.getSeq());
			}
			clear();
		} catch (IOException e) {
			logger.error("Employee Date delete error.", e);
			sessionManager.addGlobalMessageFatal("Employee Data delete error.",
					null);
			throw e;
		}
	}

	public int getListLength() {
		if (listModel == null)
			return 0;
		return listModel.getRowCount();
	}

	public List<JobCategoryDto> getBaseList() throws IOException {
		if (baseList == null)
			baseList = jobCategoryService.getAllList();
		return baseList;
	}

	public void setBaseList(List<JobCategoryDto> baseList) {
		this.baseList = baseList;
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

	public List<JobCategoryDto> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<JobCategoryDto> selecteds) {
		this.selecteds = selecteds;
	}

	public JobCategoryDto getEditDto() {
		return editDto;
	}

	public void setEditDto(JobCategoryDto editDto) {
		this.editDto = editDto;
	}

	public boolean isDeleteMode() {
		return deleteMode;
	}

	public void setDeleteMode(boolean deleteMode) {
		this.deleteMode = deleteMode;
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
