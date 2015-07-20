package npp.condition;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import npp.utils.DateUtil;


public class AppraisalCondition {
	private String employeeName;
	private String evaluatorName;
	private Date from;
	private Date to;
	private String employeeID;
	private List<Integer> seqs;


	public AppraisalCondition() {
		clear();
	}

	public DynamicQuery genQuery() {
		DynamicQuery query = new DynamicQuery();
		if (employeeID != null && !employeeID.equals("")) {
			query.addParameter(new Parameter("employee_id", "=", employeeID));
		}
		if (from != null) {
			query.addParameter(new Parameter("due", ">=", DateUtil.getDateFormatInstance().format(from)));
		}
		if (to != null) {
			query.addParameter(new Parameter("due", "<=", DateUtil.getDateFormatInstance().format(to)));
		}

		for(int seq : seqs)
		{
			Parameter para = new Parameter("seq", "=", seq);
			para.setType(Parameter.OR);
			query.addParameter(para);
		}
		return query;
	}
	public void clear()
	{
		this.employeeName = "";
		this.evaluatorName = "";
		this.from = null;
		this.to = null;
		this.employeeID = "";
		this.seqs = new ArrayList<Integer>();
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEvaluatorName() {
		return evaluatorName;
	}

	public void setEvaluatorName(String evaluatorName) {
		this.evaluatorName = evaluatorName;
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

	public String getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

	public List<Integer> getSeqs() {
		return seqs;
	}

	public void setSeqs(List<Integer> seqs) {
		this.seqs = seqs;
	}

}
