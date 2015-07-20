package npp.dto;



public class SupervisorDto {
	private String id;
	private String name;

	//You must declared default constructor for Framework.
	public SupervisorDto(){
		this.id = "";
		this.name = "";
	}

	public SupervisorDto(String id, String name){
		this.id = id;
		this.name = name;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
