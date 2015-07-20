package npp.dto;


public class MessageDto {
	private String summary;
	private String detail;
	private String severity;

	//You must declared default constructor for Framework.
	public MessageDto(){
		init("", "", "");
	}

	private void init(String summary, String detail, String severity){
		this.summary = summary;
		this.detail = detail;
		this.severity = severity;
	}


	public String getSummary() {
		return summary;
	}


	public void setSummary(String summary) {
		this.summary = summary;
	}


	public String getDetail() {
		return detail;
	}


	public void setDetail(String detail) {
		this.detail = detail;
	}


	public String getSeverity() {
		return severity;
	}


	public void setSeverity(String severity) {
		this.severity = severity;
	}

}
