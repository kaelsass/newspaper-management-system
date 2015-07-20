package npp.condition;

import java.util.ArrayList;
import java.util.Date;

import npp.dto.IssueDto;




public class SectionCondition {
	private int newspaperSeq;
	private int issueSeq;
	private Date from;
	private Date to;
	private int number;
	private String name;
	private int passed;
	private ArrayList<IssueDto> issues;

	// You must declared default constructor for Framework.
	public SectionCondition() {
		clear();
	}

	public DynamicQuery genQuery() {
		DynamicQuery query = new DynamicQuery();
		if (issueSeq > 0) {
			query.addParameter(new Parameter("issue_seq", "=", issueSeq));
		}
		if (number > 0) {
			query.addParameter(new Parameter("number", "=", number));
		}
		if (name != null && !name.equals("")) {
			query.addParameter(new Parameter("lower(name)", "like", "%" + name.toLowerCase() + "%"));
		}
		if(passed > 0)
		{
			query.addParameter(new Parameter("passed", "=", passed));
		}
		for(IssueDto dto : issues)
		{
			Parameter para = new Parameter("issue_seq", "=", dto.getSeq());
			para.setType(Parameter.OR);
			query.addParameter(para);
		}
		return query;
	}
	public void clear()
	{
		this.newspaperSeq = 0;
		this.issueSeq = 0;
		this.from = null;
		this.to = null;
		this.number = 0;
		this.name = "";
		this.passed = 0;
		this.issues = new ArrayList<IssueDto>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNewspaperSeq() {
		return newspaperSeq;
	}

	public void setNewspaperSeq(int newspaperSeq) {
		this.newspaperSeq = newspaperSeq;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getPassed() {
		return passed;
	}

	public void setPassed(int passed) {
		this.passed = passed;
	}

	public ArrayList<IssueDto> getIssues() {
		return issues;
	}

	public void setIssues(ArrayList<IssueDto> issues) {
		this.issues = issues;
	}

	public int getIssueSeq() {
		return issueSeq;
	}

	public void setIssueSeq(int issueSeq) {
		this.issueSeq = issueSeq;
	}
}
