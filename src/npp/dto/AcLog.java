package npp.dto;


public class AcLog {
	private AcDto ac;
	private String employeeID;
	private int rate;
	private String comment;
	//You must declared default constructor for Framework.
	public AcLog(){
		init(new AcDto(), "", 0, "");
	}


	public AcLog(AcDto ac, String employeeID, int rate, String comment){
		init(ac, employeeID, rate, comment);
	}

	public AcLog(AcLog dto) {
		init(dto.getAc(), dto.getEmployeeID(), dto.getRate(), dto.getComment());
	}
	private void init(AcDto ac, String employeeID, int rate, String comment){
		this.ac = ac;
		this.employeeID = employeeID;
		this.rate = rate;
		this.comment = comment;
	}

	public int getRate() {
		return rate;
	}


	public void setRate(int rate) {
		this.rate = rate;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	public AcDto getAc() {
		return ac;
	}


	public void setAc(AcDto ac) {
		this.ac = ac;
	}


	public String getEmployeeID() {
		return employeeID;
	}


	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

	public double getScore()
	{
		return ((double)ac.getRatio())/100 * rate;
	}
}
