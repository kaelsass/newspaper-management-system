package npp.dto;

import java.text.SimpleDateFormat;
import java.util.Date;



public class ArticleDto {
	private int seq;
	private String title;
	private String html;
	private SectionDto section;
	private SubjectDto subject;
	private AuthorDto author;
	private Date time;
	private StatusDto status;

	public ArticleDto(){
		init(0, "", "", new SectionDto(), new SubjectDto(), new AuthorDto(), null, new StatusDto(2, "Pending Approval"));
	}

	public ArticleDto(int seq, String title, String html, SectionDto section, SubjectDto subject, AuthorDto author, Date time, StatusDto status){
		init(seq, title, html, section, subject, author, time, status);
	}

	public ArticleDto(ArticleDto dto) {
		init(dto.getSeq(), dto.getTitle(), dto.getHtml(), dto.getSection(), dto.getSubject(), dto.getAuthor(), dto.getTime(), dto.getStatus());
	}
	public void init(int seq, String title, String html, SectionDto section, SubjectDto subject, AuthorDto author, Date time, StatusDto status){
		this.seq = seq;
		this.title = title;
		this.html = html;
		this.section = (section == null ? new SectionDto() : section);
		this.subject = (subject == null ? new SubjectDto() : subject);
		this.author = (author == null ? new AuthorDto() : author);
		this.time = time;
		this.status = status;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public SectionDto getSection() {
		return section;
	}

	public void setSection(SectionDto section) {
		this.section = section;
	}

	public SubjectDto getSubject() {
		return subject;
	}

	public void setSubject(SubjectDto subject) {
		this.subject = subject;
	}

	public AuthorDto getAuthor() {
		return author;
	}

	public void setAuthor(AuthorDto author) {
		this.author = author;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getFormatTime()
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(time);
	}

	public StatusDto getStatus() {
		return status;
	}

	public void setStatus(StatusDto status) {
		this.status = status;
	}

}
