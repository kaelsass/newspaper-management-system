package npp.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import npp.utils.DateUtil;


public class EventDto {
	private String id;
	private String title;
	private Date dateFrom;
	private Date dateTo;
	private ReminderDto reminder;
	private String note;
	private int complete;
	private boolean emailNotification;
	private String email;

	//You must declared default constructor for Framework.
	public EventDto(){
		init("", "", new Date(), new Date(), new ReminderDto(), "", 0, false, "");
	}


	public EventDto(String id, String title, Date dateFrom, Date dateTo, ReminderDto reminder, String note, int complete, boolean emailNotification, String email){
		init(id, title, dateFrom, dateTo, reminder, note, complete, emailNotification, email);
	}

	public EventDto(EventDto dto) {
		init(dto.getId(), dto.getTitle(),dto.getDateFrom(), dto.getDateTo(), dto.getReminder(), dto.getNote(), dto.getComplete(), dto.isEmailNotification(), dto.getEmail());
	}
	private void init(String id, String title, Date dateFrom, Date dateTo, ReminderDto reminder, String note, int complete, boolean emailNotification, String email){
		this.id = id;
		this.title = title;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.reminder = reminder;
		this.note = (note == null ? "" : note);
		this.complete = complete;
		this.emailNotification = emailNotification;
		this.email = (email == null ? "" : email);
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Date getDateFrom() {
		return dateFrom;
	}


	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}


	public Date getDateTo() {
		return dateTo;
	}


	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public ReminderDto getReminder() {
		return reminder;
	}


	public void setReminder(ReminderDto reminder) {
		this.reminder = reminder;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}
	public String getFormatDate()
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return df.format(dateFrom) + " to " + df.format(dateTo);
	}

	public int getComplete() {
		return complete;
	}


	public void setComplete(int complete) {
		this.complete = complete;
	}

	public boolean isTodayEvent() {
		Date today = new Date();
		SimpleDateFormat df = DateUtil.getDateFormatInstance();
		return (df.format(dateFrom).compareTo(df.format(today)) <= 0 && df.format(dateTo).compareTo(df.format(today)) >= 0);
	}


	public boolean isNeedNotify() {
		if(getComplete() == 1)
			return false;
		Date now = new Date();
		long time1 = now.getTime();
        long time2 = dateFrom.getTime();

        // Calculate difference in milliseconds
        long diff = time2 - time1;

        // Difference in minutes
        long diffMin = diff / (60 * 1000);

        if(diffMin  >= 0 && diffMin <= reminder.getMinute())
        	return true;
        return false;
	}
	public boolean isExpire() {
		if(getComplete() == 1)
			return false;
		Date now = new Date();
		long time1 = now.getTime();
        long time2 = dateTo.getTime();

        if(time1 > time2)
        	return true;
        return false;
	}


	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof EventDto))
			return false;
		return ((EventDto)obj).getId().equals(this.getId());
	}


	public boolean isEmailNotification() {
		return emailNotification;
	}


	public void setEmailNotification(boolean emailNotification) {
		this.emailNotification = emailNotification;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


}
