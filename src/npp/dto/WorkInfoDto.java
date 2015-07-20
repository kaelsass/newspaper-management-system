package npp.dto;




public class WorkInfoDto {
	private String id;
	private String name;
	private JobTitleDto jobTitle;
	private StatusDto status;
	private JobCategoryDto jobCategory;
	private DepartmentDto department;
	private SupervisorDto supervisor;
	private String phone;
	private String email;
	private String photoName;

	//You must declared default constructor for Framework.
	public WorkInfoDto(){
		this.id = "";
		this.name="";
		this.jobTitle = new JobTitleDto();
		this.status = new StatusDto();
		this.jobCategory = new JobCategoryDto();
		this.department = new DepartmentDto();
		this.supervisor = new SupervisorDto();
		this.phone = "";
		this.email = "";
		this.photoName = "";
	}

	public WorkInfoDto(String id, String name, JobTitleDto jobTitle, StatusDto status, JobCategoryDto jobCategory, DepartmentDto department, SupervisorDto supervisor, String phone, String email, String photoName){
		this.id = id;
		this.name = name;
		this.jobTitle = (jobTitle == null ? new JobTitleDto() : jobTitle);
		this.status = (status == null ? new StatusDto() : status);
		this.jobCategory = (jobCategory == null ? new JobCategoryDto() : jobCategory);
		this.department = (department == null ? new DepartmentDto() : department);
		this.supervisor = (supervisor == null ? new SupervisorDto() : supervisor);
		this.phone = phone;
		this.email = email;
		this.photoName = photoName;
	}

	public WorkInfoDto(WorkInfoDto dto) {
		this.id = dto.getId();
		this.name = dto.getName();
		this.jobTitle = (dto.getJobTitle() == null ? new JobTitleDto() : dto.getJobTitle());
		this.status = (dto.getStatus() == null ? new StatusDto() : dto.getStatus());
		this.jobCategory = (dto.getJobCategory() == null ? new JobCategoryDto() : dto.getJobCategory());
		this.department = (dto.getDepartment() == null ? new DepartmentDto() : dto.getDepartment());
		this.supervisor = (dto.getSupervisor() == null ? new SupervisorDto() : dto.getSupervisor());
		this.phone = dto.getPhone();
		this.email = dto.getEmail();
		this.photoName = dto.getPhotoName();
	}

	public String getName()
	{
		return name;
	}

	public String getFirstName() {
		String[] strs = name.split(" ");
		return (strs.length >= 1 ? strs[0]:"");
	}

	public String getLastName() {
		String[] strs = name.split(" ");
		return (strs.length == 2 ? strs[1]:"");
	}

	public DepartmentDto getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentDto departmentDto) {
		this.department = departmentDto;
	}

	public JobTitleDto getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(JobTitleDto jobTitle) {
		this.jobTitle = jobTitle;
	}

	public StatusDto getStatus() {
		return status;
	}

	public void setStatus(StatusDto status) {
		this.status = status;
	}

	public SupervisorDto getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(SupervisorDto supervisor) {
		this.supervisor = supervisor;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}
	public String getPhotoName()
	{
		if(photoName == null || photoName.equals(""))
			return "default.gif";
		return photoName;
	}

	public JobCategoryDto getJobCategory() {
		return jobCategory;
	}

	public void setJobCategory(JobCategoryDto jobCategory) {
		this.jobCategory = jobCategory;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof WorkInfoDto))
			return false;
		WorkInfoDto other = (WorkInfoDto) obj;
		return other.getId().equals(this.getId());
	}

}
