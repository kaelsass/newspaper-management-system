package npp.impl.controler;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import npp.dto.EventDto;
import npp.dto.ReminderDto;
import npp.faces.EventListModel;
import npp.faces.MyScheduleModel;
import npp.spec.manager.SessionManager;
import npp.spec.service.ScheduleService;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.ScheduleEvent;

@Named
@SessionScoped
public class ScheduleControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 6411341285680182936L;

	private MyScheduleModel eventModel;

	private List<EventDto> baseList = null;
	private EventListModel listModel = null;
	private List<EventDto> notifyList = null;


	private boolean addMode;
	private boolean editMode;
	private boolean deleteMode;

	private List<EventDto> selecteds;

	private EventDto editDto;

	@Inject
	private ScheduleService scheduleService;
	@Inject
	private SessionManager sessionManager;

	@PostConstruct
	public void init()
	{
		addMode = false;
		editMode = false;
		deleteMode = false;
		editDto = getNewEventDto();
	}
	public EventListModel getListModel() {
		if (baseList == null) {
			try {
				baseList = scheduleService.getAllList();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			listModel = new EventListModel(baseList);
		}
		return baseList == null ? new EventListModel(
				new ArrayList<EventDto>()) : listModel;
	}
	public List<EventDto> getBaseList() {
		if (baseList == null) {
			try {
				baseList = scheduleService.getAllList();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			listModel = new EventListModel(baseList);
		}
		return baseList == null ? new ArrayList<EventDto>() : baseList;
	}

	public void startAdd() {
		editDto = getNewEventDto();
		addMode = true;
		editMode = false;
	}

	public void startEdit(ActionEvent e) {
		EventDto selected = (EventDto) e.getComponent().getAttributes()
				.get("event");
		editDto = new EventDto(selected);
		addMode = false;
		editMode = true;
	}

	public void startDelete() {
		deleteMode = true;
	}

	public void endDelete() {
		deleteMode = false;
	}

	public void clear() {
		baseList = null;
		editDto = getNewEventDto();
		eventModel = null;
		selecteds = null;
		addMode = false;
		editMode = false;
		deleteMode = false;
	}
	private EventDto getNewEventDto()
	{
		EventDto dto = new EventDto();
		dto.setId(UUID.randomUUID().toString());
		String email = "";
		try {
			email = sessionManager.getLoginUser().getEmployee().getEmail();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dto.setEmail(email);
		return dto;
	}

	public void delete() throws IOException {
		try {
			for (EventDto status : selecteds) {
				scheduleService.delete(status.getId());
			}
			clear();
		} catch (IOException e) {
			sessionManager.addGlobalMessageFatal("Employee Data delete error.",
					null);
			throw e;
		}
	}

	public MyScheduleModel getEventModel() throws IOException {
		if (eventModel == null) {
			eventModel = makeModel();
		}
		return eventModel;
	}


	private MyScheduleModel makeModel() {
		MyScheduleModel model = new MyScheduleModel();
		for(EventDto dto : getBaseList())
		{
			model.addEvent(makeEvent(dto));
		}
		return model;
	}
	public void addEvent() throws IOException {
		if (eventModel.getEvent(editDto.getId()) == null) {
			eventModel.addEvent(makeEvent(editDto));
			scheduleService.add(editDto);
		} else {
			System.out.println("here");
			eventModel.updateEvent(makeEvent(editDto));
			scheduleService.update(editDto);
		}
		clear();
	}

	private ScheduleEvent makeEvent(EventDto dto) {
		DefaultScheduleEvent event = new DefaultScheduleEvent(dto.getTitle(), dto.getDateFrom(), dto.getDateTo(), (Object)dto.getNote());
		event.setId(dto.getId());
		return event;
	}

	public void onEventSelect(SelectEvent selectEvent) {
		DefaultScheduleEvent event = (DefaultScheduleEvent) selectEvent.getObject();
		editDto = findByID(event.getId());
		editMode = true;
		addMode = false;
	}

	private EventDto findByID(String id) {
		for(EventDto dto : getBaseList())
		{
			if(dto.getId().equals(id))
				return dto;
		}
		return null;
	}

	public void onDateSelect(SelectEvent selectEvent) {
		editDto = getNewEventDto();
		editDto.setDateFrom((Date) selectEvent.getObject());
		editDto.setDateTo((Date) selectEvent.getObject());
		addMode = true;
		editMode = false;
	}

	public void onEventMove(ScheduleEntryMoveEvent e) throws IOException {
		DefaultScheduleEvent event = (DefaultScheduleEvent) e
				.getScheduleEvent();
		updateEditDto(event);
		scheduleService.update(editDto);
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"The event has been updated.", "The event has been updated.");

		addMessage(message);
	}

	private void updateEditDto(DefaultScheduleEvent event) {
		editDto.setDateFrom(event.getStartDate());
		editDto.setDateTo(event.getEndDate());

	}

	public void onEventResize(ScheduleEntryResizeEvent e) throws IOException {
		DefaultScheduleEvent event = (DefaultScheduleEvent) e
				.getScheduleEvent();
		updateEditDto(event);
		scheduleService.update(editDto);
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"The event has been updated.", "The event has been updated.");

		addMessage(message);
	}

	private void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
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

	public boolean isDeleteMode() {
		return deleteMode;
	}

	public void setDeleteMode(boolean deleteMode) {
		this.deleteMode = deleteMode;
	}

	public List<EventDto> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<EventDto> selecteds) {
		this.selecteds = selecteds;
	}

	public EventDto getEditDto() {
		return editDto;
	}

	public void setEditDto(EventDto editDto) {
		this.editDto = editDto;
	}
	public void clearDto()
	{
		editDto.setTitle("");
		editDto.setReminder(new ReminderDto());
		editDto.setNote("");
	}
	public void test()
	{
		sessionManager.addGlobalMessageFatal("test", "test");
	}
	public void processNotificationEvents()
	{
		List<EventDto> list = getNotificationEvents();
		List<EventDto> newEvents = new ArrayList<EventDto>();
		if(notifyList == null)
			notifyList = new ArrayList<EventDto>();
		for(EventDto dto : list)
		{
			if(!notifyList.contains(dto))
			{
				newEvents.add(dto);
			}
		}
		for(EventDto dto : newEvents)
		{
			sessionManager.addGlobalMessageInfo("Notification Event", dto.getFormatDate() + " " + dto.getTitle());
		}
		notifyList = list;
	}
	private List<EventDto> getNotificationEvents() {
		List<EventDto> list = new ArrayList<EventDto>();

		for(EventDto dto : getBaseList())
		{
			if(dto.isNeedNotify())
				list.add(dto);
		}
		return list;
	}
	public String getFormatNotification()
	{
		System.out.println("notification size:" + getNotificationEvents().size());
		int todayCount = getTodayNotification().size();
		if(todayCount == 0)
			return "";
		return "("+getNotificationEvents().size() + "/" + todayCount + ")";
	}
	private List<EventDto> getTodayNotification() {
		List<EventDto> list = new ArrayList<EventDto>();

		for(EventDto dto : getBaseList())
		{
			if(dto.isTodayEvent())
				list.add(dto);
		}
		return list;
	}
	public void markComplete(int complete)
	{
		editDto.setComplete(complete);
	}
	public void applyStatus(EventDto dto) throws IOException {
		scheduleService.update(dto);
		updateBaseList(dto);
	}
	private void updateBaseList(EventDto dto) {
		List<EventDto> list = getBaseList();
		for(int i = 0 ; i < list.size(); i++)
		{
			if(list.get(i).getId().equals(dto.getId()))
			{
				baseList.set(i, dto);
				break;
			}
		}
	}
}