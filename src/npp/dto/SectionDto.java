package npp.dto;

public class SectionDto {
	private int seq;
	private int number;
	private SubjectDto subject;
	private IssueDto issue;
	private StatusDto status;
	private String photoName;
	private String html;
	public SectionDto()
	{
		init(0, 0, new SubjectDto(), new IssueDto(), new StatusDto(), "", "");
	}
	public SectionDto(int seq, int number, SubjectDto subject, IssueDto issue,
			StatusDto status, String photoName, String html) {
		init(seq, number, subject, issue, status, photoName, html);
	}
	public SectionDto(SectionDto dto) {
		init(dto.getSeq(), dto.getNumber(),dto.getSubject(), dto.getIssue(), dto.getStatus(), dto.getPhotoName(), dto.getHtml());
	}
	public void init(int seq, int number, SubjectDto subject, IssueDto issue,
			StatusDto status, String photoName, String html)
	{
		this.seq = seq;
		this.number = number;
		this.subject = (subject == null ? new SubjectDto() : subject);
		this.issue = (issue == null ? new IssueDto() : issue);
		this.status = (status == null ? new StatusDto() : status);
		this.photoName = photoName;
		this.html = html;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}

	public IssueDto getIssue() {
		return issue;
	}
	public void setIssue(IssueDto issue) {
		this.issue = issue;
	}
	public StatusDto getStatus() {
		return status;
	}
	public void setStatus(StatusDto status) {
		this.status = status;
	}
	public String getFormatSection()
	{
		StringBuffer sb = new StringBuffer();
		if(number > 0)
			sb.append("Page " + number);
		if(subject.getSeq() > 0)
			sb.append(" (" + subject.getName() + ")");
		return sb.toString();
	}
	public String getPhotoName() {
		if(photoName == null || photoName.equals(""))
			return "default.jpg";
		return photoName;
	}
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}
	public SubjectDto getSubject() {
		return subject;
	}
	public void setSubject(SubjectDto subject) {
		this.subject = subject;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}

}
