package npp.condition;

import java.util.ArrayList;
import java.util.List;

import npp.dto.WorkInfoDto;


public class UserCondition {
	private String userName;
	private String role;
	private String employeeName;
	private List<WorkInfoDto> employees;

	// You must declared default constructor for Framework.
	public UserCondition() {
		super();
		this.userName="";
		this.role = "";
		this.employeeName = "";
		employees = new ArrayList<WorkInfoDto>();
	}

	public UserCondition(String userName, String userRole, String employeeName) {
		this.userName = userName;
		this.role = userRole;
		this.employeeName = employeeName;
		employees = new ArrayList<WorkInfoDto>();
	}



	public DynamicQuery genQuery() {
		DynamicQuery query = new DynamicQuery();
		if (userName != null && !userName.equals("")) {
			query.addParameter(new Parameter("lower(username)", "like", "%"+userName.toLowerCase()+"%"));
		}
		if (role != null && !role.equals("")) {
			query.addParameter(new Parameter("role", "=", role));
		}
		for(WorkInfoDto dto : employees)
		{
			Parameter para = new Parameter("employeeid", "=", dto.getId());
			para.setType(Parameter.OR);
			query.addParameter(para);
		}
		return query;
	}

	public String getUserRole() {
		return role;
	}

	public void setUserRole(String userRole) {
		this.role = userRole;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public void clear()
	{
		this.userName = "";
		this.role = "";
		this.employeeName = "";
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<WorkInfoDto> getEmployees() {
		return employees;
	}

	public void setEmployees(List<WorkInfoDto> employees) {
		this.employees = employees;
	}
}
