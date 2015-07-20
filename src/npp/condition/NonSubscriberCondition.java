package npp.condition;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import npp.dto.IssueDto;


public class NonSubscriberCondition {
	private String name;
	private String address;
	private String workunit;
	private int[] newspaperSeqs;
	private Date dateFrom;
	private Date dateTo;
	private Date orderDateFrom;
	private Date orderDateTo;
	private int quantity;
	private String[] employeeIDs;
	private int purposeSeq;
	private ArrayList<IssueDto> issues;

	public NonSubscriberCondition() {
		clear();
	}

	public DynamicQuery genQuery() {
		SimpleDateFormat begin = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat end = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		DynamicQuery query = new DynamicQuery();
		if (name != null && !name.equals("")) {
			query.addParameter(new Parameter("lower(name)", "like", "%"
					+ name.toLowerCase() + "%"));
		}
		if (address != null && !address.equals("")) {
			query.addParameter(new Parameter("lower(address)", "like", "%"
					+ address.toLowerCase() + "%"));
		}
		if (workunit != null && !workunit.equals("")) {
			query.addParameter(new Parameter("lower(workunit)", "like", "%"
					+ workunit.toLowerCase() + "%"));
		}
		if (orderDateFrom != null) {
			query.addParameter(new Parameter("order_date", ">=", begin
					.format(orderDateFrom)));
		}
		if (orderDateTo != null) {
			query.addParameter(new Parameter("order_date", "<=", end
					.format(orderDateTo)));
		}
		if (quantity > 0) {
			query.addParameter(new Parameter("quantity", ">=", quantity));
		}
		if (purposeSeq > 0) {
			query.addParameter(new Parameter("purpose_seq", "=", purposeSeq));
		}
		if (employeeIDs != null && employeeIDs.length > 0) {
			for (String id : employeeIDs) {
				Parameter para = new Parameter("employee_id", "=", id);
				para.setType(Parameter.OR);
				query.addParameter(para);
			}
		}
		if (issues != null && issues.size() > 0) {
			for (IssueDto dto : issues)
			{
				Parameter para = new Parameter("issue_seq", "=", dto.getSeq());
				para.setType(Parameter.OR);
				query.addParameter(para);
			}
		}
		return query;
	}

	public void clear() {
		this.name = "";
		this.address = "";
		this.workunit = "";
		this.newspaperSeqs = null;
		this.dateFrom = null;
		this.dateTo = null;
		this.orderDateFrom = null;
		this.orderDateTo = null;
		this.quantity = 0;
		this.employeeIDs = null;
		this.purposeSeq = 0;
		this.issues = new ArrayList<IssueDto>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Date getOrderDateTo() {
		return orderDateTo;
	}

	public void setOrderDateTo(Date orderDateTo) {
		this.orderDateTo = orderDateTo;
	}

	public Date getOrderDateFrom() {
		return orderDateFrom;
	}

	public void setOrderDateFrom(Date orderDateFrom) {
		this.orderDateFrom = orderDateFrom;
	}

	public String getWorkunit() {
		return workunit;
	}

	public void setWorkunit(String workunit) {
		this.workunit = workunit;
	}

	public int getPurposeSeq() {
		return purposeSeq;
	}

	public void setPurposeSeq(int purposeSeq) {
		this.purposeSeq = purposeSeq;
	}

	public ArrayList<IssueDto> getIssues() {
		return issues;
	}

	public void setIssues(ArrayList<IssueDto> issues) {
		this.issues = issues;
	}

	public String[] getEmployeeIDs() {
		return employeeIDs;
	}

	public void setEmployeeIDs(String[] employeeIDs) {
		this.employeeIDs = employeeIDs;
	}

	public int[] getNewspaperSeqs() {
		return newspaperSeqs;
	}

	public void setNewspaperSeqs(int[] newspaperSeqs) {
		this.newspaperSeqs = newspaperSeqs;
	}
}
