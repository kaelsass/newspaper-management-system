package npp.dto;


public class TaskAdminDto {
	private int taskSeq;
	private String employeeID;

	//You must declared default constructor for Framework.
	public TaskAdminDto(){
		init(0, "");
	}


	public TaskAdminDto(int taskSeq, String employeeID){
		init(taskSeq, employeeID);
	}

	public TaskAdminDto(TaskAdminDto dto) {
		init(dto.getTaskSeq(), dto.getEmployeeID());
	}
	private void init(int taskSeq, String employeeID){
		this.taskSeq = taskSeq;
		this.employeeID = employeeID;
	}


	public int getTaskSeq() {
		return taskSeq;
	}


	public void setTaskSeq(int taskSeq) {
		this.taskSeq = taskSeq;
	}


	public String getEmployeeID() {
		return employeeID;
	}


	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

}
