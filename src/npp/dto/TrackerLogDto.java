package npp.dto;

import java.util.Date;

import npp.utils.DateUtil;

public class TrackerLogDto {
	private int seq;
	private TrackerDto tracker;
	private WorkInfoDto reviewer;
	private String log;
	private String comments;
	private PerformanceDto performance;
	private Date addDate;
	private Date modDate;

	//You must declared default constructor for Framework.
	public TrackerLogDto(){
		init(0, new TrackerDto(), new WorkInfoDto(), "", "", new PerformanceDto(), null, null );
	}


	public TrackerLogDto(int seq, TrackerDto tracker, WorkInfoDto reviewer, String log, String comments, PerformanceDto performance, Date addDate, Date modDate){
		init(seq, tracker, reviewer, log, comments, performance, addDate, modDate);
	}

	public TrackerLogDto(TrackerLogDto dto) {
		init(dto.getSeq(),dto.getTracker(), dto.getReviewer(), dto.getLog(), dto.getComments(), dto.getPerformance(), dto.getAddDate(), dto.getModDate());
	}
	private void init(int seq, TrackerDto tracker, WorkInfoDto reviewer, String log, String comments, PerformanceDto performance, Date addDate, Date modDate){
		this.seq = seq;
		this.tracker = tracker;
		this.reviewer = reviewer;
		this.log = log;
		this.comments = comments;
		this.performance = performance;
		this.addDate = addDate;
		this.modDate = modDate;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
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


	public TrackerDto getTracker() {
		return tracker;
	}


	public void setTracker(TrackerDto tracker) {
		this.tracker = tracker;
	}


	public WorkInfoDto getReviewer() {
		return reviewer;
	}


	public void setReviewer(WorkInfoDto reviewer) {
		this.reviewer = reviewer;
	}


	public String getLog() {
		return log;
	}


	public void setLog(String log) {
		this.log = log;
	}


	public String getComments() {
		return comments;
	}


	public void setComments(String comments) {
		this.comments = comments;
	}


	public PerformanceDto getPerformance() {
		return performance;
	}


	public void setPerformance(PerformanceDto performance) {
		this.performance = performance;
	}


}
