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

import npp.condition.TimesheetCondition;
import npp.condition.WorkInfoCondition;
import npp.dto.TimesheetDto;
import npp.dto.WorkInfoDto;
import npp.faces.TimesheetListModel;
import npp.spec.manager.SessionManager;
import npp.spec.service.TimesheetService;
import npp.spec.service.WorkInfoService;
import npp.utils.DateUtil;
import npp.utils.DialogUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Named
@SessionScoped
public class TimesheetControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6156728038843400463L;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private List<TimesheetDto> baseList = null;
	private TimesheetListModel listModel = null;
	private List<TimesheetDto> allList = null;

	private boolean editMode;
	private boolean deleteMode;


	private List<TimesheetDto> selecteds;
	private TimesheetDto editDto;
	private TimesheetCondition condition;
	private Date time;

	@Inject
	private TimesheetService timesheetService;
	@Inject
	private SessionManager sessionManager;

	@Inject
	private WorkInfoService workInfoService;

	@PostConstruct
	public void init() {
		editMode = false;
		deleteMode = false;
		editDto = new TimesheetDto();
		condition = new TimesheetCondition();
		try {
			allList = timesheetService.getAllList(condition.genQuery());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public TimesheetListModel getListModel() {
		if (baseList == null) {
			try {
				baseList = timesheetService.getAllList(condition.genQuery());
				listModel = new TimesheetListModel(baseList);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return baseList == null ? new TimesheetListModel(
				new ArrayList<TimesheetDto>()) : listModel;
	}

	public void startAdd()
	{
		editDto = new TimesheetDto();
	}
	public void startEdit(ActionEvent e)
	{
		TimesheetDto dto = (TimesheetDto)e.getComponent().getAttributes().get("timesheet");
		editDto = new TimesheetDto(dto);
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
		WorkInfoDto employee = workInfoService.findByName(editDto.getEmployee().getName());
		Date startDate = DateUtil.getFirstDayOfWeek(time);
		Date endDate = DateUtil.getLastDayOfWeek(time);
		editDto.setEmployee(employee);
		editDto.setStartDate(startDate);
		editDto.setEndDate(endDate);

		for(TimesheetDto dto : getAllList())
		{
			if(dto.getEmployee().getId().equals(editDto.getEmployee().getId()) && dto.getStartDate().compareTo(editDto.getStartDate()) == 0)
			{
				DialogUtil.showDialog("errorDialog_w");
				return;
			}
		}

		if(editMode)
			timesheetService.update(editDto);
		else
			timesheetService.add(editDto);
		sessionManager.addGlobalMessageInfo("The time sheet is added successfully.", null);
		DialogUtil.hideDialog("addTimesheet_w");
		clear();
	}

	public void clear()
	{
		baseList = null;
		editDto = new TimesheetDto();
		selecteds = null;
		editMode = false;
		deleteMode = false;
		allList = null;
	}
	public void delete() throws IOException {
		try {
			for (TimesheetDto dto : selecteds) {
				timesheetService.delete(dto.getSeq());
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

	public List<TimesheetDto> getBaseList() throws IOException {
		if (baseList == null)
		{
			baseList = timesheetService.getAllList(condition.genQuery());
			listModel = new TimesheetListModel(baseList);
		}
		return baseList;
	}

	public void setBaseList(List<TimesheetDto> baseList) {
		this.baseList = baseList;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public List<TimesheetDto> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<TimesheetDto> selecteds) {
		this.selecteds = selecteds;
	}

	public boolean isDeleteMode() {
		return deleteMode;
	}

	public void setDeleteMode(boolean deleteMode) {
		this.deleteMode = deleteMode;
	}
	public void search() throws IOException
	{
		clear();

		WorkInfoCondition wc = new WorkInfoCondition();
		wc.setName(condition.getEmployeeName());
		List<WorkInfoDto> employees = workInfoService.getAllList(wc.genQuery());
		condition.getEmployees().clear();
		for(WorkInfoDto dto : employees)
		{
			condition.getEmployees().add(dto);
		}
	}

	public TimesheetCondition getCondition() {
		return condition;
	}

	public void setCondition(TimesheetCondition condition) {
		this.condition = condition;
	}

	public TimesheetDto getEditDto() {
		return editDto;
	}

	public void setEditDto(TimesheetDto editDto) {
		this.editDto = editDto;
	}

	public List<TimesheetDto> getAllList() throws IOException {
		if(allList == null)
		{
			TimesheetCondition sc = new TimesheetCondition();
			allList = timesheetService.getAllList(sc.genQuery());
		}
		return allList;
	}

	public void setAllList(List<TimesheetDto> allList) {
		this.allList = allList;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
