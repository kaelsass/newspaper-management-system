package npp.condition;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import npp.dto.WorkInfoDto;


public class AdCondition {
	private String ID;
	private String workUnit;
	private String address;
	private int newspaperSeq;
	private Date dateFrom;
	private Date dateTo;
	private Date orderDateFrom;
	private Date orderDateTo;
	private String employeeName;
	private ArrayList<WorkInfoDto> employees;

	public AdCondition() {
		clear();
	}

	public DynamicQuery genQuery() {
		SimpleDateFormat begin = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat end = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		DynamicQuery query = new DynamicQuery();
		if (ID != null && !ID.equals("")) {
			query.addParameter(new Parameter("lower(ID)", "like", "%"
					+ ID.toLowerCase() + "%"));
		}
		if (workUnit != null && !workUnit.equals("")) {
			query.addParameter(new Parameter("lower(workUnit)", "like", "%"
					+ workUnit.toLowerCase() + "%"));
		}
		if (address != null && !address.equals("")) {
			query.addParameter(new Parameter("lower(address)", "like", "%"
					+ address.toLowerCase() + "%"));
		}
		if (newspaperSeq > 0) {
			query.addParameter(new Parameter("newspaper_seq", "=", newspaperSeq));
		}
		if (dateFrom != null) {
			query.addParameter(new Parameter("date_from", ">=", begin
					.format(dateFrom)));
		}
		if (dateTo != null) {
			query.addParameter(new Parameter("date_from", "<=", end
					.format(dateTo)));
		}
		if (orderDateFrom != null) {
			query.addParameter(new Parameter("order_date", ">=", begin
					.format(orderDateFrom)));
		}
		if (orderDateTo != null) {
			query.addParameter(new Parameter("order_date", "<=", end
					.format(orderDateTo)));
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
		this.ID = "";
		this.workUnit = "";
		this.address = "";
		this.newspaperSeq = 0;
		this.dateFrom = null;
		this.dateTo = null;
		this.orderDateFrom = null;
		this.orderDateTo = null;
		this.employeeName = "";
		this.employees = new ArrayList<WorkInfoDto>();
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

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getWorkUnit() {
		return workUnit;
	}

	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}

	public int getNewspaperSeq() {
		return newspaperSeq;
	}

	public void setNewspaperSeq(int newspaperSeq) {
		this.newspaperSeq = newspaperSeq;
	}

	public Date getOrderDateFrom() {
		return orderDateFrom;
	}

	public void setOrderDateFrom(Date orderDateFrom) {
		this.orderDateFrom = orderDateFrom;
	}

	public Date getOrderDateTo() {
		return orderDateTo;
	}

	public void setOrderDateTo(Date orderDateTo) {
		this.orderDateTo = orderDateTo;
	}

}
