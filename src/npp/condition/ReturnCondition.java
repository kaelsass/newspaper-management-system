package npp.condition;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import npp.dto.IssueDto;
import npp.dto.WorkInfoDto;


public class ReturnCondition {
	private int[] newspaperSeqs;
	private int quantity;
	private Date dateFrom;
	private Date dateTo;
	private Date orderDateFrom;
	private Date orderDateTo;
	private String employeeName;
	private ArrayList<WorkInfoDto> employees;
	private ArrayList<IssueDto> issues;

	public ReturnCondition() {
		clear();
	}

	public DynamicQuery genQuery() {
		SimpleDateFormat begin = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat end = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		DynamicQuery query = new DynamicQuery();
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
		if (newspaperSeqs != null && newspaperSeqs.length > 0) {
			for (IssueDto dto : issues) {
				Parameter para = new Parameter("issue_seq", "=", dto.getSeq());
				para.setType(Parameter.OR);
				query.addParameter(para);
			}
		}
		if (!(employeeName == null || employeeName.trim().equals(""))) {
			for (WorkInfoDto dto : employees) {
				Parameter para = new Parameter("employee_id", "=", dto.getId());
				para.setType(Parameter.OR);
				query.addParameter(para);
			}
		}
		return query;
	}

	public void clear() {
		this.newspaperSeqs = null;
		this.dateFrom = null;
		this.dateTo = null;
		this.orderDateFrom = null;
		this.orderDateTo = null;
		this.quantity = 0;
		this.employeeName = "";
		this.employees = new ArrayList<WorkInfoDto>();
		this.issues = new ArrayList<IssueDto>();
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public ArrayList<WorkInfoDto> getEmployees() {
		return employees;
	}

	public void setEmployees(ArrayList<WorkInfoDto> employees) {
		this.employees = employees;
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

	public ArrayList<IssueDto> getIssues() {
		return issues;
	}

	public void setIssues(ArrayList<IssueDto> issues) {
		this.issues = issues;
	}

	public int[] getNewspaperSeqs() {
		return newspaperSeqs;
	}

	public void setNewspaperSeqs(int[] newspaperSeqs) {
		this.newspaperSeqs = newspaperSeqs;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
}
