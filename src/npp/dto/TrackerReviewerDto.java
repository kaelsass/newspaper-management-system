package npp.dto;


public class TrackerReviewerDto {
	private int trackerSeq;
	private String employeeID;

	//You must declared default constructor for Framework.
	public TrackerReviewerDto(){
		init(0, "");
	}


	public TrackerReviewerDto(int trackerSeq, String employeeID){
		init(trackerSeq, employeeID);
	}

	private void init(int trackerSeq, String employeeID){
		this.trackerSeq = trackerSeq;
		this.employeeID = employeeID;
	}


	public int getTrackerSeq() {
		return trackerSeq;
	}


	public void setTrackerSeq(int trackerSeq) {
		this.trackerSeq = trackerSeq;
	}


	public String getEmployeeID() {
		return employeeID;
	}


	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

}
