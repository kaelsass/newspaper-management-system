package npp.dto;

public class SexDto {
	private String name;

	//You must declared default constructor for Framework.
	public SexDto(){
		super();
		name = "";
	}

	public SexDto(String name){
		this.name = name;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
