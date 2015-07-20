package npp.condition;


public class WorkInfoCondition {
	private String id;
	private String name;
	private int status;
	private String supervisorName;
	private int jobTitle;
	private int department;
	private int office;

	// You must declared default constructor for Framework.
	public WorkInfoCondition() {
		super();
		this.id = "";
		this.name="";
		this.status=-1;
		this.supervisorName="";
		this.jobTitle=-1;
		this.department=-1;
		this.office=-1;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSupervisorName() {
		return supervisorName;
	}

	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}


	public DynamicQuery genQuery() {
		DynamicQuery query = new DynamicQuery();
		if (id != null && !id.equals("")) {
				query.addParameter(new Parameter("lower(id)", "like", "%"+id.toLowerCase()+"%"));
		}
		if (name != null && !name.equals("")) {
			query.addParameter(new Parameter("lower(name)", "like", "%"+name.toLowerCase()+"%"));

		}
		if (status > 0) {
			query.addParameter(new Parameter("status_seq", "=", status));
		}
		if (jobTitle > 0) {
			query.addParameter(new Parameter("jobTitle_seq", "=", jobTitle));
		}
		if (department > 0) {
			query.addParameter(new Parameter("department_seq", "=", department));
		}
		if (office > 0) {
			query.addParameter(new Parameter("office_seq", "=", office));
		}
		return query;
	}
	public void clear()
	{
		this.id = "";
		this.name = "";
		this.status = -1;
		this.supervisorName = "";
		this.jobTitle = -1;
		this.department = -1;
		this.office = -1;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(int jobTitle) {
		this.jobTitle = jobTitle;
	}

	public int getDepartment() {
		return department;
	}

	public void setDepartment(int department) {
		this.department = department;
	}

	public int getOffice() {
		return office;
	}

	public void setOffice(int office) {
		this.office = office;
	}
}
