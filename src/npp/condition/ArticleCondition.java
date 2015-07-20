package npp.condition;

import java.util.ArrayList;

import npp.dto.AuthorDto;


public class ArticleCondition {
	private String title;
	private int subjectSeq;
	private int sectionSeq;
	private String authorName;
	private ArrayList<AuthorDto> authors;
	private String html;
	private Integer[] statusSeqs;

	public ArticleCondition() {
		clear();
	}

	public DynamicQuery genQuery() {
		DynamicQuery query = new DynamicQuery();
		if (title != null && !title.equals("")) {
			query.addParameter(new Parameter("lower(title)", "like", "%" + title.toLowerCase() + "%"));
		}
		if (subjectSeq > 0) {
			query.addParameter(new Parameter("subject_seq", "=", subjectSeq));
		}
		if (sectionSeq > 0) {
			query.addParameter(new Parameter("section_seq", "=", sectionSeq));
		}

		if (html != null && !html.equals("")) {
			query.addParameter(new Parameter("lower(html)", "like", "%" + html.toLowerCase() + "%"));
		}
		for(AuthorDto dto : authors)
		{
			Parameter para = new Parameter("author_seq", "=", dto.getSeq());
			para.setType(Parameter.OR);
			query.addParameter(para);
		}
		for(Integer seq : statusSeqs)
		{
			Parameter para = new Parameter("status_seq", "=", seq);
			para.setType(Parameter.OR);
			query.addParameter(para);
		}
		return query;
	}
	public void clear()
	{
		this.title = "";
		this.subjectSeq = 0;
		this.sectionSeq = 0;
		this.authorName = "";
		this.html = "";
		authors = new ArrayList<AuthorDto>();
		statusSeqs = new Integer[]{1, 2, 3};
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return html;
	}


	public void setContent(String content) {
		this.html = content;
	}

	public int getSubjectSeq() {
		return subjectSeq;
	}

	public void setSubjectSeq(int subjectSeq) {
		this.subjectSeq = subjectSeq;
	}

	public int getSectionSeq() {
		return sectionSeq;
	}

	public void setSectionSeq(int sectionSeq) {
		this.sectionSeq = sectionSeq;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public ArrayList<AuthorDto> getAuthors() {
		return authors;
	}

	public void setAuthors(ArrayList<AuthorDto> authors) {
		this.authors = authors;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public Integer[] getStatusSeqs() {
		return statusSeqs;
	}

	public void setStatusSeqs(Integer[] statusSeqs) {
		this.statusSeqs = statusSeqs;
	}


}
