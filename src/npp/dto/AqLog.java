package npp.dto;


public class AqLog {
	private AqDto aq;
	private String employeeID;
	private int rate;
	private String comment;
	//You must declared default constructor for Framework.
	public AqLog(){
		init(new AqDto(), "", 0, "");
	}


	public AqLog(AqDto aq, String employeeID, int rate, String comment){
		init(aq, employeeID, rate, comment);
	}

	public AqLog(AqLog dto) {
		init(dto.getAq(), dto.getEmployeeID(), dto.getRate(), dto.getComment());
	}
	private void init(AqDto aq, String employeeID, int rate, String comment){
		this.aq = aq;
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
	public AqDto getAq() {
		return aq;
	}


	public void setAq(AqDto aq) {
		this.aq = aq;
	}


	public String getEmployeeID() {
		return employeeID;
	}


	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}


	public double getScore() {
		return ((double)aq.getRatio())/100 * rate;
	}

	public String getFormatComment()
	{
		if(comment == null)
			return null;
		int base = 35;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		while((count + base) < comment.length())
		{
			sb.append(comment.substring(count, count+base));
			sb.append("\n");
			count += base;
		}
		sb.append(comment.substring(count));
		return sb.toString();
	}

}
