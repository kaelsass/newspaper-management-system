package npp.dto;

import java.util.Date;

import npp.utils.DateUtil;

public class GoalDto {
	private int seq;
	private String name;
	private int completion;
	private WorkInfoDto employee;
	private Date due;
	private StatusDto status;
	private StatusDto type;
	private String description;
	public GoalDto()
	{
		init(0, "", 0, new WorkInfoDto(), new Date(), new StatusDto(), new StatusDto(), "");
	}
	public GoalDto(int seq, String name, int completion, WorkInfoDto employee, Date due, StatusDto status, StatusDto type, String description)
	{
		init(seq, name, completion, employee, due, status, type, description);
	}
	public GoalDto(GoalDto dto)
	{
		init(dto.getSeq(), dto.getName(), dto.getCompletion(), dto.getEmployee(), dto.getDue(), dto.getStatus(), dto.getType(), dto.getDescription());
	}
	public void init(int seq, String name, int completion, WorkInfoDto employee, Date due, StatusDto status, StatusDto type, String description)
	{
		this.seq = seq;
		this.name = name;
		this.completion = completion;
		this.employee = employee;
		this.due = due;
		this.status = status;
		this.type = type;
		this.description = description;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCompletion() {
		return completion;
	}
	public void setCompletion(int completion) {
		this.completion = completion;
	}
	public WorkInfoDto getEmployee() {
		return employee;
	}
	public void setEmployee(WorkInfoDto employee) {
		this.employee = employee;
	}

	public StatusDto getStatus() {
		return status;
	}
	public void setStatus(StatusDto status) {
		this.status = status;
	}
	public StatusDto getType() {
		return type;
	}
	public void setType(StatusDto type) {
		this.type = type;
	}
	public Date getDue() {
		return due;
	}
	public void setDue(Date due) {
		this.due = due;
	}
	public String getFormatDue()
	{
		if(due == null)
			return "";
		return DateUtil.getDateFormatInstance().format(due);
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
