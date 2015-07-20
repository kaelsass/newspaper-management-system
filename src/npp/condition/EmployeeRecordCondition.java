package npp.condition;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import npp.dto.WorkInfoDto;


public class EmployeeRecordCondition {
	private List<WorkInfoDto> employees;
	private String employeeName;
	private Date date;

	// You must declared default constructor for Framework.
	public EmployeeRecordCondition() {
		super();
		this.employeeName="";
		this.date = null;
		employees = new ArrayList<WorkInfoDto>();
	}

	public EmployeeRecordCondition(String employeeName, Date date) {
		this.employeeName = employeeName;
		this.date = date;
		employees = new ArrayList<WorkInfoDto>();
	}
	public void addEmployee(WorkInfoDto dto)
	{
		employees.add(dto);
	}

	public DynamicQuery genQuery() {
		DynamicQuery query = new DynamicQuery();
		SimpleDateFormat begin = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat end = new SimpleDateFormat("yyyy-MM-dd 23:59:59");


		if (date != null) {
			query.addParameter(new Parameter("intime", ">=", begin.format(date)));
			query.addParameter(new Parameter("intime", "<=", end.format(date)));
		}
		else
		{
			query.addParameter(new Parameter("intime", "=", ""));
		}
		for(WorkInfoDto dto : employees)
		{
			Parameter para = new Parameter("employeeid", "=", dto.getId());
			para.setType(Parameter.OR);
			query.addParameter(para);
		}
		return query;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public List<WorkInfoDto> getEmployees() {
		return employees;
	}

	public void setEmployees(List<WorkInfoDto> employees) {
		this.employees = employees;
	}
}
