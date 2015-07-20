package npp.condition;

import java.util.ArrayList;
import java.util.List;

import npp.dto.WorkInfoDto;

public class TimesheetCondition {
	private String employeeName;
	private List<WorkInfoDto> employees;

	// You must declared default constructor for Framework.
	public TimesheetCondition() {
		clear();
	}

	public DynamicQuery genQuery() {
		DynamicQuery query = new DynamicQuery();
		if (!employeeName.equals("") && employees.size() == 0) {
			query.addParameter(new Parameter("employee_id", "=", "0"));
		} else {

			for (WorkInfoDto employee : employees) {
				Parameter para = new Parameter("employee_id", "=",
						employee.getId());
				para.setType(Parameter.OR);
				query.addParameter(para);
			}
		}
		return query;
	}

	public void clear() {
		this.employeeName = "";
		this.employees = new ArrayList<WorkInfoDto>();
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
