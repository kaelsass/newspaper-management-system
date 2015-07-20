package npp.impl.controler;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import npp.condition.DynamicQuery;
import npp.condition.Parameter;
import npp.dto.ActivityDto;
import npp.dto.TaskDto;
import npp.dto.TimesheetDto;
import npp.dto.TimesheetPerWeekDto;
import npp.faces.TimesheetPerWeekListModel;
import npp.spec.manager.SessionManager;
import npp.spec.service.ActivityService;
import npp.spec.service.TaskService;
import npp.spec.service.TimesheetPerWeekService;
import npp.spec.service.TimesheetService;
import npp.utils.DateUtil;
import npp.utils.DialogUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Named
@SessionScoped
public class TimesheetPerWeekControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6156728038843400463L;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private List<TimesheetPerWeekDto> baseList = null;
	private TimesheetPerWeekListModel listModel = null;

	private boolean editMode;
	private boolean deleteMode;

	private TimesheetDto timesheet;
	private TimesheetDto addTimesheet;
	private List<TimesheetPerWeekDto> selecteds;
	private TimesheetPerWeekDto editDto;
	private Date time;

	@Inject
	private TimesheetPerWeekService timesheetPerWeekService;
	@Inject
	private SessionManager sessionManager;

	@Inject
	private ActivityService activityService;
	@Inject
	private TimesheetService timesheetService;
	@Inject
	private TaskService taskService;

	private List<TimesheetDto> timesheetList;
	private List<TaskDto> taskList;
	private List<ActivityDto> activityList;

	private int[] hours;

	private int[] minutes;

	@PostConstruct
	public void init() {
		timesheetList = null;
		editMode = false;
		deleteMode = false;
		editDto = new TimesheetPerWeekDto();
	}

	public TimesheetPerWeekListModel getListModel() {
		if (baseList == null) {
			try {
				baseList = timesheetPerWeekService.findByTimesheetSeq(timesheet.getSeq());
				listModel = new TimesheetPerWeekListModel(baseList);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return baseList == null ? new TimesheetPerWeekListModel(
				new ArrayList<TimesheetPerWeekDto>()) : listModel;
	}
	public List<TimesheetPerWeekDto> getBaseList() {
		if (baseList == null) {
			try {
				baseList = timesheetPerWeekService.findByTimesheetSeq(timesheet.getSeq());
				listModel = new TimesheetPerWeekListModel(baseList);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return baseList == null ? new ArrayList<TimesheetPerWeekDto>() : baseList;
	}

	public void startAdd()
	{
		editDto = new TimesheetPerWeekDto();
		editDto.setTimesheet(timesheet);
	}
	public void startAddTimeSheet()
	{
		addTimesheet = new TimesheetDto();
		addTimesheet.setEmployee(timesheet.getEmployee());
	}
	public void startEditTimesheet(ActionEvent e)
	{
		TimesheetDto selected = (TimesheetDto)e.getComponent().getAttributes().get("timesheet");
		timesheet = new TimesheetDto(selected);
		editDto = new TimesheetPerWeekDto();
		editDto.setTimesheet(timesheet);
		System.out.println("employee: " + timesheet.getEmployee().getId() + " : " + timesheet.getEmployee().getName());
		clear();
	}
	public void changeTimesheet() throws IOException
	{
		this.timesheet = timesheetService.findBySeq(timesheet.getSeq());
		editDto = new TimesheetPerWeekDto();
		editDto.setTimesheet(timesheet);
		clear();
	}

	public void startEdit(ActionEvent e)
	{
		TimesheetPerWeekDto dto = (TimesheetPerWeekDto)e.getComponent().getAttributes().get("record");
		editDto = new TimesheetPerWeekDto(dto);
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
		if(editDto.getTask().getSeq() == 0 || editDto.getActivity().getSeq() == 0)
		{
			DialogUtil.showDialog("errorDialog1_w");
			return;
		}
//		ActivityDto activity = activityService.findBySeq(editDto.getActivity().getSeq());
//		editDto.setActivity(activity);
		if(editMode)
			timesheetPerWeekService.update(editDto);
		else
			timesheetPerWeekService.add(editDto);
		DialogUtil.hideDialog("editRecord_w");
		clear();
	}

	public void clear()
	{
		baseList = null;
		editDto = new TimesheetPerWeekDto();
		selecteds = null;
		editMode = false;
		deleteMode = false;
		taskList = null;
		activityList = null;
		timesheetList = null;

	}
	public void delete() throws IOException {
		try {
			for (TimesheetPerWeekDto dto : selecteds) {
				timesheetPerWeekService.delete(dto.getSeq());
			}
			clear();
		} catch (IOException e) {
			logger.error("Article Date delete error.", e);
			sessionManager.addGlobalMessageFatal("Article Data delete error.",
					null);
			throw e;
		}
	}

	public int getStatusListLength() {
		if (listModel == null)
			return 0;
		return listModel.getRowCount();
	}

	public List<TimesheetPerWeekDto> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<TimesheetPerWeekDto> selecteds) {
		this.selecteds = selecteds;
	}

	public boolean isDeleteMode() {
		return deleteMode;
	}

	public void setDeleteMode(boolean deleteMode) {
		this.deleteMode = deleteMode;
	}
//	public void search() throws IOException
//	{
//		clear();
//
//		WorkInfoCondition wc = new WorkInfoCondition();
//		wc.setName(condition.getEmployeeName());
//		List<WorkInfoDto> employees = activityService.getAllList(wc.genQuery());
//		for(WorkInfoDto dto : employees)
//		{
//			condition.getEmployees().add(dto);
//		}
//	}

	public TimesheetPerWeekDto getEditDto() {
		return editDto;
	}

	public void setEditDto(TimesheetPerWeekDto editDto) {
		this.editDto = editDto;
	}

	public TimesheetDto getTimesheet() {
		return timesheet;
	}

	public void setTimesheet(TimesheetDto timesheet) {
		this.timesheet = timesheet;
	}

	public List<TimesheetDto> getTimesheetList() {
		if(timesheetList == null)
		{
			DynamicQuery query = new DynamicQuery();
			query.addParameter(new Parameter("employee_id", "=", timesheet.getEmployee().getId()));
			System.out.println("employee: " + timesheet.getEmployee().getId() + ":" + timesheet.getEmployee().getName());
			try {
				timesheetList = timesheetService.getAllList(query);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return timesheetList == null ? new ArrayList<TimesheetDto>() : timesheetList;
	}

	public void setTimesheetList(List<TimesheetDto> timesheetList) {
		this.timesheetList = timesheetList;
	}

	public TimesheetDto getAddTimesheet() {
		return addTimesheet;
	}

	public void setAddTimesheet(TimesheetDto addTimesheet) {
		this.addTimesheet = addTimesheet;
	}

	public List<TaskDto> getTaskList() {
		if(taskList == null)
		{
			try {
				taskList = taskService.getAllList(new DynamicQuery());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return taskList == null ? new ArrayList<TaskDto>() : taskList;
	}

	public void setTaskList(List<TaskDto> taskList) {
		this.taskList = taskList;
	}

	public List<ActivityDto> getActivityList() {
		if(activityList == null || editDto.getActivity().getSeq() > activityList.size())
		{
			try {
				activityList = activityService.findByTaskSeq(editDto.getTask().getSeq());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return activityList == null ? new ArrayList<ActivityDto>() : activityList;
	}

	public void setActivityList(List<ActivityDto> activityList) {
		this.activityList = activityList;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}
	public void changeTask() throws IOException
	{
		editDto.setTask(taskService.findBySeq(editDto.getTask().getSeq()));
		editDto.setActivity(new ActivityDto());
		activityList = null;
	}
	public void applyTimeSheet() throws IOException
	{
		Date startDate = DateUtil.getFirstDayOfWeek(time);
		Date endDate = DateUtil.getLastDayOfWeek(time);
		for(TimesheetDto ts : getTimesheetList())
		{
			if(ts.getStartDate().compareTo(startDate) == 0)
			{
				DialogUtil.showDialog("errorDialog_w");
				return;
			}
		}

		TimesheetDto dto = new TimesheetDto();
		dto.setEmployee(timesheet.getEmployee());
		dto.setStartDate(startDate);
		dto.setEndDate(endDate);
		timesheetService.add(dto);
		timesheet = dto;
		timesheet.setSeq(timesheetService.getNewInsertedSeq());
		DialogUtil.hideDialog("addTimesheet_w");
		clear();
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	public String getTotalTime(int i)
	{
		initTotalTime();

		return String.format("%02d:%02d", hours[i-1], minutes[i-1]);
	}
	public String getTotalTime()
	{
		initTotalTime();
		int totalHours = 0;
		int totalMinutes = 0;
		for(int i = 0; i < 7; i ++)
		{
			totalHours += hours[i];
			totalMinutes += minutes[i];
		}
		totalHours += totalMinutes/60;
		totalMinutes = totalMinutes%60;
		return String.format("%02d:%02d", totalHours, totalMinutes);
	}

	private void initTotalTime() {
		hours = new int[7];
		minutes = new int[7];
		for(TimesheetPerWeekDto dto : getBaseList())
		{
			hours[0] += dto.getTime1().getHours();
			hours[1] += dto.getTime2().getHours();
			hours[2] += dto.getTime3().getHours();
			hours[3] += dto.getTime4().getHours();
			hours[4] += dto.getTime5().getHours();
			hours[5] += dto.getTime6().getHours();
			hours[6] += dto.getTime7().getHours();
			minutes[0] += dto.getTime1().getMinutes();
			minutes[1] += dto.getTime2().getMinutes();
			minutes[2] += dto.getTime3().getMinutes();
			minutes[3] += dto.getTime4().getMinutes();
			minutes[4] += dto.getTime5().getMinutes();
			minutes[5] += dto.getTime6().getMinutes();
			minutes[6] += dto.getTime7().getMinutes();
		}
		for(int i = 0; i < 7; i ++)
		{
			hours[i] += minutes[i]/60;
			minutes[i] = minutes[i] % 60;
		}
	}

}
