package npp.dto;

import java.util.Calendar;
import java.util.Date;

import npp.utils.DateUtil;


public class TimesheetDto {
	private int seq;
	private WorkInfoDto employee;
	private Date startDate;
	private Date endDate;

	//You must declared default constructor for Framework.
	public TimesheetDto(){
		init(0, new WorkInfoDto(), null, null);
	}


	public TimesheetDto(int seq, WorkInfoDto employee, Date startDate, Date endDate){
		init(seq, employee, startDate, endDate);
	}

	public TimesheetDto(TimesheetDto dto) {
		init(dto.getSeq(), dto.getEmployee(), dto.getStartDate(), dto.getEndDate());
	}
	private void init(int seq, WorkInfoDto employee, Date startDate, Date endDate){
		this.seq = seq;
		this.employee = employee;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}


	public WorkInfoDto getEmployee() {
		return employee;
	}


	public void setEmployee(WorkInfoDto employee) {
		this.employee = employee;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getFormatWeek()
	{
		return DateUtil.getDateFormatInstance().format(startDate) + " to " + DateUtil.getDateFormatInstance().format(endDate);
	}
	public int getDate1()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	public int getDate2()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		cal.set(Calendar.DAY_OF_WEEK,Calendar.TUESDAY);
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	public int getDate3()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		cal.set(Calendar.DAY_OF_WEEK,Calendar.WEDNESDAY);
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	public int getDate4()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		cal.set(Calendar.DAY_OF_WEEK,Calendar.THURSDAY);

		return cal.get(Calendar.DAY_OF_MONTH);
	}
	public int getDate5()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		cal.set(Calendar.DAY_OF_WEEK,Calendar.FRIDAY);

		return cal.get(Calendar.DAY_OF_MONTH);
	}
	public int getDate6()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		cal.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);

		return cal.get(Calendar.DAY_OF_MONTH);
	}
	public int getDate7()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(endDate);

		return cal.get(Calendar.DAY_OF_MONTH);
	}

}
