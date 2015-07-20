package npp.condition;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import npp.dto.DepartmentDto;
import npp.dto.JobTitleDto;
import npp.dto.StatusDto;
import npp.dto.WorkInfoDto;


public class AttendanceSummaryCondition {
	private List<WorkInfoDto> employees;
	private String employeeName;
	private JobTitleDto jobTitle;
	private DepartmentDto department;
	private StatusDto status;
	private Date from;
	private Date to;

	// You must declared default constructor for Framework.
	public AttendanceSummaryCondition() {
		super();
		this.employeeName="";
		employees = new ArrayList<WorkInfoDto>();
		jobTitle = new JobTitleDto();
		department = new DepartmentDto();
		status = new StatusDto();
		from = null;
		to = null;
	}

	public void addEmployee(WorkInfoDto dto)
	{
		employees.add(dto);
	}

	public DynamicQuery genQuery() {
		DynamicQuery query = new DynamicQuery();
		SimpleDateFormat begin = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat end = new SimpleDateFormat("yyyy-MM-dd 23:59:59");


//		if(jobTitle.getSeq() > 0)
//		{
//			query.addParameter(new Parameter("jobtitle_seq", "=", jobTitle.getSeq()));
//		}
//		if(status.getSeq() > 0)
//		{
//			query.addParameter(new Parameter("status_seq", "=", status.getSeq()));
//		}
//		if(department.getSeq() > 0)
//		{
//			query.addParameter(new Parameter("department_seq", "=", department.getSeq()));
//		}
		if (from != null) {
			query.addParameter(new Parameter("intime", ">=", begin.format(from)));
		}
		if (to != null) {
			query.addParameter(new Parameter("intime", "<=", end.format(to)));
		}
		if(from == null && to == null)
		{
			query.addParameter(new Parameter("intime", "=", ""));
		}
		for(WorkInfoDto dto : employees)
		{
			Parameter parm = new Parameter("employeeid", "=", dto.getId());
			parm.setType(Parameter.OR);
			query.addParameter(parm);
		}
		return query;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public JobTitleDto getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(JobTitleDto jobTitle) {
		this.jobTitle = jobTitle;
	}

	public DepartmentDto getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentDto department) {
		this.department = department;
	}

	public StatusDto getStatus() {
		return status;
	}

	public void setStatus(StatusDto status) {
		this.status = status;
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

	public List<WorkInfoDto> getEmployees() {
		return employees;
	}

	public void setEmployees(List<WorkInfoDto> employees) {
		this.employees = employees;
	}
}
