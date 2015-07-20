package npp.dto;



public class UserDto {
	private String password;
	private String userName;
	private RoleDto role;
	private WorkInfoDto employee;

	//You must declared default constructor for Framework.
	public UserDto(){
		this.password="";
		this.userName="";
		this.role = new RoleDto();
		this.employee = new WorkInfoDto();
	}

	public UserDto(String password, String userName, RoleDto role, WorkInfoDto employee){
		this.password = password;
		this.userName = userName;
		this.role = (role == null ? new RoleDto() : role);
		this.employee = (employee == null ? new WorkInfoDto() : employee);
	}

	public UserDto(UserDto user) {
		this.password = user.getPassword();
		this.userName = user.getUserName();
		this.role = user.getRole();
		this.employee = user.getEmployee();
	}

	public String getPassword() {
		return password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public RoleDto getRole() {
		return role;
	}

	public void setRole(RoleDto role) {
		this.role = role;
	}

	public WorkInfoDto getEmployee() {
		return employee;
	}

	public void setEmployee(WorkInfoDto employee) {
		this.employee = employee;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
