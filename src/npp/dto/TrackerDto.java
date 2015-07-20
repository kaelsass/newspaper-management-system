package npp.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import npp.utils.DateUtil;

public class TrackerDto {
	private int seq;
	private String name;
	private WorkInfoDto employee;
	private Date addDate;
	private Date modDate;
	private List<WorkInfoDto> reviewers;

	//You must declared default constructor for Framework.
	public TrackerDto(){
		init(0, "", new WorkInfoDto(), new ArrayList<WorkInfoDto>(), null, null );
	}


	public TrackerDto(int seq, String name, WorkInfoDto employee, ArrayList<WorkInfoDto> reviewers, Date addDate, Date modDate){
		init(seq, name, employee, reviewers, addDate, modDate);
	}

	public TrackerDto(TrackerDto dto) {
		init(dto.getSeq(),dto.getName(), dto.getEmployee(), dto.getReviewers(), dto.getAddDate(), dto.getModDate());
	}
	private void init(int seq, String name, WorkInfoDto employee, List<WorkInfoDto> reviewers, Date addDate, Date modDate){
		this.seq = seq;
		this.name = name;
		this.employee = employee;
		this.reviewers = reviewers;
		this.addDate = addDate;
		this.modDate = modDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public Date getModDate() {
		return modDate;
	}

	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}
	public String getFormatAddDate()
	{
		if(addDate == null)
		{
			return "";
		}
		else
		{
			return DateUtil.getDateFormatInstance().format(addDate);
		}
	}
	public String getFormatModDate()
	{
		if(modDate == null)
		{
			return "";
		}
		else
		{
			return DateUtil.getDateFormatInstance().format(modDate);
		}
	}


	public List<WorkInfoDto> getReviewers() {
		return reviewers;
	}


	public void setReviewers(List<WorkInfoDto> reviewers) {
		this.reviewers = reviewers;
	}

}
