package npp.dto;

public class AgLog {
	private AgDto ag;
	private String employeeID;
	private int rate;
	private String comment;

	// You must declared default constructor for Framework.
	public AgLog() {
		init(new AgDto(), "", 0, "");
	}

	public AgLog(AgDto ag, String employeeID, int rate, String comment) {
		init(ag, employeeID, rate, comment);
	}

	public AgLog(AgLog dto) {
		init(dto.getAg(), dto.getEmployeeID(), dto.getRate(), dto.getComment());
	}

	private void init(AgDto ag, String employeeID, int rate, String comment) {
		this.ag = ag;
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

	public AgDto getAg() {
		return ag;
	}

	public void setAg(AgDto ag) {
		this.ag = ag;
	}

	public String getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

	public double getScore() {
		return ((double)ag.getRatio())/100 * rate;
	}
}
