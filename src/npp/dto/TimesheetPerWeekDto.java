package npp.dto;

import java.util.Calendar;
import java.util.Date;


public class TimesheetPerWeekDto {
	private int seq;
	private TimesheetDto timesheet;
	private TaskDto task;
	private ActivityDto activity;
	private Date time1;
	private Date time2;
	private Date time3;
	private Date time4;
	private Date time5;
	private Date time6;
	private Date time7;

	//You must declared default constructor for Framework.
	public TimesheetPerWeekDto(){
		Calendar cal = Calendar.getInstance();
		cal.set(0, 0, 0, 0, 0);
		init(0, new TimesheetDto(), new TaskDto(), new ActivityDto(), cal.getTime(),cal.getTime(),cal.getTime(),
				cal.getTime(),cal.getTime(),cal.getTime(),cal.getTime());
	}

	public TimesheetPerWeekDto(int seq, TimesheetDto timesheet, TaskDto task, ActivityDto activity, Date time1, Date time2, Date time3, Date time4,
			Date time5, Date time6, Date time7){
		init(seq, timesheet, task, activity, time1, time2, time3, time4, time5, time6, time7);
	}

	public TimesheetPerWeekDto(TimesheetPerWeekDto dto) {
		init(dto.getSeq(), dto.getTimesheet(), dto.getTask(), dto.getActivity(), dto.getTime1(), dto.getTime2(), dto.getTime3(), dto.getTime4(),
				dto.getTime5(), dto.getTime6(), dto.getTime7());
	}
	private void init(int seq, TimesheetDto timesheet, TaskDto task, ActivityDto activity, Date time1, Date time2, Date time3, Date time4,
			Date time5, Date time6, Date time7){
		this.seq = seq;
		this.timesheet = timesheet;
		this.task = task;
		this.activity = activity;
		this.time1 = time1;
		this.time2 = time2;
		this.time3 = time3;
		this.time4 = time4;
		this.time5 = time5;
		this.time6 = time6;
		this.time7 = time7;
	}


	public int getSeq() {
		return seq;
	}


	public void setSeq(int seq) {
		this.seq = seq;
	}

	public TaskDto getTask() {
		return task;
	}


	public void setTask(TaskDto task) {
		this.task = task;
	}


	public ActivityDto getActivity() {
		return activity;
	}


	public void setActivity(ActivityDto activity) {
		this.activity = activity;
	}


	public Date getTime1() {
		return time1;
	}


	public void setTime1(Date time1) {
		this.time1 = time1;
	}


	public Date getTime2() {
		return time2;
	}


	public void setTime2(Date time2) {
		this.time2 = time2;
	}


	public Date getTime3() {
		return time3;
	}


	public void setTime3(Date time3) {
		this.time3 = time3;
	}


	public Date getTime4() {
		return time4;
	}


	public void setTime4(Date time4) {
		this.time4 = time4;
	}


	public Date getTime5() {
		return time5;
	}


	public void setTime5(Date time5) {
		this.time5 = time5;
	}


	public Date getTime6() {
		return time6;
	}


	public void setTime6(Date time6) {
		this.time6 = time6;
	}


	public Date getTime7() {
		return time7;
	}


	public void setTime7(Date time7) {
		this.time7 = time7;
	}


	public TimesheetDto getTimesheet() {
		return timesheet;
	}


	public void setTimesheet(TimesheetDto timesheet) {
		this.timesheet = timesheet;
	}
	public String getFormatTime1()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(time1);
		return String.format("%02d:%02d",cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
	}
	public String getFormatTime2()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(time2);
		return String.format("%02d:%02d",cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
	}
	public String getFormatTime3()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(time3);
		return String.format("%02d:%02d",cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
	}
	public String getFormatTime4()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(time4);
		return String.format("%02d:%02d",cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
	}
	public String getFormatTime5()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(time5);
		return String.format("%02d:%02d",cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
	}
	public String getFormatTime6()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(time6);
		return String.format("%02d:%02d",cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
	}
	public String getFormatTime7()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(time7);
		return String.format("%02d:%02d",cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
	}
	public String getFormatTotalTime()
	{
		int hour = getTotalHour();
		int minutes = getTotalMinutes();
		hour += minutes/60;
		minutes = minutes % 60;
		return String.format("%02d:%02d", hour, minutes);

	}

	private int getTotalMinutes() {
		int total = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(time1);
		total += cal.get(Calendar.MINUTE);
		cal.setTime(time2);
		total += cal.get(Calendar.MINUTE);
		cal.setTime(time3);
		total += cal.get(Calendar.MINUTE);
		cal.setTime(time4);
		total += cal.get(Calendar.MINUTE);
		cal.setTime(time5);
		total += cal.get(Calendar.MINUTE);
		cal.setTime(time6);
		total += cal.get(Calendar.MINUTE);
		cal.setTime(time7);
		total += cal.get(Calendar.MINUTE);
		return total;
	}

	private int getTotalHour() {
		int total = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(time1);
		total += cal.get(Calendar.HOUR_OF_DAY);
		cal.setTime(time2);
		total += cal.get(Calendar.HOUR_OF_DAY);
		cal.setTime(time3);
		total += cal.get(Calendar.HOUR_OF_DAY);
		cal.setTime(time4);
		total += cal.get(Calendar.HOUR_OF_DAY);
		cal.setTime(time5);
		total += cal.get(Calendar.HOUR_OF_DAY);
		cal.setTime(time6);
		total += cal.get(Calendar.HOUR_OF_DAY);
		cal.setTime(time7);
		total += cal.get(Calendar.HOUR_OF_DAY);
		return total;
	}

}
