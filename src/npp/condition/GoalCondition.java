package npp.condition;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import npp.utils.DateUtil;


public class GoalCondition {
	private int typeSeq;
	private String employeeName;
	private String name;
	private Date from;
	private Date to;
	private List<String> employeeIDs;
	private int[] statusSeqs;


	public GoalCondition() {
		clear();
	}

	public DynamicQuery genQuery() {
		DynamicQuery query = new DynamicQuery();
		if (name != null && !name.equals("")) {
			query.addParameter(new Parameter("lower(name)", "like", "%" + name.toLowerCase() + "%"));
		}
		if (typeSeq > 0) {
			query.addParameter(new Parameter("type_seq", "=", typeSeq));
		}
		if (from != null) {
			query.addParameter(new Parameter("due", ">=", DateUtil.getDateFormatInstance().format(from)));
		}
		if (to != null) {
			query.addParameter(new Parameter("due", "<=", DateUtil.getDateFormatInstance().format(to)));
		}
		for(int seq : statusSeqs)
		{
			Parameter para = new Parameter("status_seq", "=", seq);
			para.setType(Parameter.OR);
			query.addParameter(para);
		}
		for(String id : employeeIDs)
		{
			Parameter para = new Parameter("employee_id", "=", id);
			para.setType(Parameter.OR);
			query.addParameter(para);
		}
		return query;
	}
	public void clear()
	{
		this.typeSeq = 0;
		this.employeeName = "";
		this.name = "";
		this.from = null;
		this.to = null;
		this.employeeIDs = new ArrayList<String>();
		this.statusSeqs = new int[0];
	}

	public int getTypeSeq() {
		return typeSeq;
	}

	public void setTypeSeq(int typeSeq) {
		this.typeSeq = typeSeq;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public List<String> getEmployeeIDs() {
		return employeeIDs;
	}

	public void setEmployeeIDs(List<String> employeeIDs) {
		this.employeeIDs = employeeIDs;
	}

	public int[] getStatusSeqs() {
		return statusSeqs;
	}

	public void setStatusSeqs(int[] statusSeqs) {
		this.statusSeqs = statusSeqs;
	}

}
