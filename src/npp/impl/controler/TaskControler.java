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
import npp.condition.TaskCondition;
import npp.dto.ActivityDto;
import npp.dto.TaskAdminDto;
import npp.dto.TaskDto;
import npp.dto.WorkInfoDto;
import npp.faces.ActivityListModel;
import npp.faces.TaskListModel;
import npp.spec.manager.SessionManager;
import npp.spec.service.ActivityService;
import npp.spec.service.TaskAdminService;
import npp.spec.service.TaskService;
import npp.spec.service.WorkInfoService;

import org.primefaces.model.DualListModel;

@Named
@SessionScoped
public class TaskControler implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 6100364088420414584L;
	private List<TaskDto> baseList = null;
	private TaskListModel listModel = null;
	private ActivityListModel activityListModel = null;

	private List<TaskDto> allList = null;
	private DualListModel<WorkInfoDto> admins;

	private boolean addMode;
	private boolean editMode;
	private boolean deleteMode;

	private boolean addActivityMode;
	private boolean editActivityMode;
	private boolean deleteActivityMode;

	private TaskCondition condition;

	private List<TaskDto> selecteds;
	private List<ActivityDto> selectedActivities;

	private TaskDto editDto;
	private ActivityDto editActivityDto;

	@Inject
	private WorkInfoService workInfoService;
	@Inject
	private TaskService taskService;

	@Inject
	private SessionManager sessionManager;
	@Inject
	private TaskAdminService taskAdminService;
	@Inject
	private ActivityService activityService;

	@PostConstruct
	public void init(){
		addMode = false;
		editMode = false;
		deleteMode = false;
		editDto = new TaskDto();
		condition = new TaskCondition();
	}

	public TaskListModel getListModel() {
		if (baseList == null) {
			try {
				baseList = taskService.getAllList(condition.genQuery());
				listModel = new TaskListModel(baseList);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return baseList == null ? new TaskListModel(new ArrayList<TaskDto>())
				: listModel;
	}

	public ActivityListModel getActivityListModel() {
		if (activityListModel == null) {
			activityListModel = new ActivityListModel(editDto.getActivities());
		}
		return activityListModel == null ? new ActivityListModel(
				new ArrayList<ActivityDto>()) : activityListModel;
	}

	public void startAdd() {
		editDto = new TaskDto();
		addMode = true;
		editMode = false;
	}

	public void startEdit(ActionEvent e) {
		addMode = false;
		editMode = true;
	}

	public void preEdit(ActionEvent e) {
		TaskDto dto = (TaskDto) e.getComponent().getAttributes().get("task");
		editDto = new TaskDto(dto);
		admins = null;
	}

	public void startDelete() {
		deleteMode = true;
	}

	public void endDelete() {
		deleteMode = false;
	}

	public void startAddActivity() {
		editActivityDto = new ActivityDto();
		addActivityMode = true;
		editActivityMode = false;
	}

	public void startEditActivity(ActionEvent e) {
		ActivityDto dto = (ActivityDto) e.getComponent().getAttributes()
				.get("activity");
		editActivityDto = new ActivityDto(dto);
		addActivityMode = false;
		editActivityMode = true;
	}

	public void startDeleteActivity() {
		deleteActivityMode = true;
	}

	public void endDeleteActivity() {
		deleteActivityMode = false;
	}

	public void apply() throws IOException {

		editDto.setAdmins(getAdmins().getTarget());

		if (addMode)
		{
			taskService.add(editDto);
			editDto.setSeq(taskService.getNewInsertedSeq());
		}
		else if (editMode)
			taskService.update(editDto);

		clear();
	}

	public void applyActivity() throws IOException {

		editActivityDto.setTaskSeq(editDto.getSeq());

		if (addActivityMode)
			activityService.add(editActivityDto);
		else if (editMode)
			activityService.update(editActivityDto);
		clearActivity();
	}

	private void clearActivity() throws IOException {
		editDto.setActivities(activityService.findByTaskSeq(editDto.getSeq()));
		activityListModel = null;
		selectedActivities = null;
		addActivityMode = false;
		editActivityMode = false;
		deleteActivityMode = false;
	}

	public void clear() {
		baseList = null;
		admins = null;
		selecteds = null;
		addMode = false;
		editMode = false;
		deleteMode = false;
		addActivityMode = false;
		editActivityMode = false;
		deleteActivityMode = false;

	}

	public void delete() throws IOException {
		try {
			for (TaskDto dto : selecteds) {
				taskService.delete(dto.getSeq());
			}
			clear();
		} catch (IOException e) {
			sessionManager.addGlobalMessageFatal("Issue Data delete error.",
					null);
			throw e;
		}
	}

	public void deleteActivity() throws IOException {
		try {
			for (ActivityDto dto : selectedActivities) {
				activityService.delete(dto.getSeq());
			}
			clearActivity();
		} catch (IOException e) {
			sessionManager.addGlobalMessageFatal("Issue Data delete error.",
					null);
			throw e;
		}
	}

	public int getStatusListLength() {
		if (listModel == null)
			return 0;
		return listModel.getRowCount();
	}

	public List<TaskDto> getBaseList() throws IOException {
		if (baseList == null) {
			baseList = taskService.getAllList(condition.genQuery());
			listModel = new TaskListModel(baseList);
		}
		return baseList;
	}

	public void setBaseList(List<TaskDto> baseList) {
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

	public boolean isBrowseMode() {
		return !addMode && !editMode;
	}

	public boolean isBrowseActivityMode() {
		return !addActivityMode && !editActivityMode;
	}

	public List<TaskDto> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<TaskDto> selecteds) {
		this.selecteds = selecteds;
	}

	public boolean isDeleteMode() {
		return deleteMode;
	}

	public void setDeleteMode(boolean deleteMode) {
		this.deleteMode = deleteMode;
	}

	public void search() throws IOException {
		clear();
		for (String id : condition.getEmployeeIDs()) {
			List<TaskAdminDto> list = taskAdminService.findByEmployeeID(id);
			for (TaskAdminDto dto : list) {
				if (!condition.getTaskSeqs().contains(dto.getTaskSeq()))
					condition.getTaskSeqs().add(dto.getTaskSeq());
			}
		}
	}

	public TaskDto getEditDto() {
		return editDto;
	}

	public void setEditDto(TaskDto editDto) {
		this.editDto = editDto;
	}

	public DualListModel<WorkInfoDto> getAdmins() {
		if (admins == null) {
			try {
				List<WorkInfoDto> allEmployees = workInfoService
						.getAllList(new DynamicQuery());

				allEmployees.removeAll(editDto.getAdmins());
				admins = new DualListModel<WorkInfoDto>(allEmployees,
						editDto.getAdmins());

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return admins == null ? new DualListModel<WorkInfoDto>(
				new ArrayList<WorkInfoDto>(), new ArrayList<WorkInfoDto>())
				: admins;
	}

	public void setAdmins(DualListModel<WorkInfoDto> admins) {
		this.admins = admins;
	}

	public TaskCondition getCondition() {
		return condition;
	}

	public void setCondition(TaskCondition condition) {
		this.condition = condition;
	}

	public List<String> completeName(String query) {
		ArrayList<String> list = new ArrayList<String>();
		for (TaskDto dto : getAllList()) {
			if (dto.getName().toLowerCase().contains(query.toLowerCase()))
				list.add(dto.getName());
		}
		return list;
	}

	public List<TaskDto> getAllList() {
		if (allList == null) {
			try {
				allList = taskService.getAllList(new DynamicQuery());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return allList == null ? new ArrayList<TaskDto>() : allList;
	}

	public void setAllList(List<TaskDto> allList) {
		this.allList = allList;
	}

	public boolean isAddActivityMode() {
		return addActivityMode;
	}

	public void setAddActivityMode(boolean addActivityMode) {
		this.addActivityMode = addActivityMode;
	}

	public boolean isEditActivityMode() {
		return editActivityMode;
	}

	public void setEditActivityMode(boolean editActivityMode) {
		this.editActivityMode = editActivityMode;
	}

	public boolean isDeleteActivityMode() {
		return deleteActivityMode;
	}

	public void setDeleteActivityMode(boolean deleteActivityMode) {
		this.deleteActivityMode = deleteActivityMode;
	}

	public ActivityDto getEditActivityDto() {
		return editActivityDto;
	}

	public void setEditActivityDto(ActivityDto editActivityDto) {
		this.editActivityDto = editActivityDto;
	}

	public List<ActivityDto> getSelectedActivities() {
		return selectedActivities;
	}

	public void setSelectedActivities(List<ActivityDto> selectedActivities) {
		this.selectedActivities = selectedActivities;
	}

	public void activityNameValidate(FacesContext context,
			UIComponent component, Object value) {
		String name = (String) value;
		for (ActivityDto dto : editDto.getActivities()) {
			if (dto.getName().equals(name))
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Activity Name has existed.", null));
		}
	}
	public void seqValidate(FacesContext context, UIComponent component, Object value)
	{
		int seq = (Integer)value;
		if(seq == 0)
		{
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Task is required.", null));
		}
	}
	public void activitySeqValidate(FacesContext context, UIComponent component, Object value)
	{
		int seq = (Integer)value;
		if(seq == 0)
		{
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Activity is required.", null));
		}
	}

}
