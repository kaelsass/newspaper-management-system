package npp.dto;

public class RoleDto {
	private int id;
	private String role;

	//You must declared default constructor for Framework.
	public RoleDto(){
		this.id=0;
		this.role="";
	}

	public RoleDto(int id, String role){
		this.id = id;
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
