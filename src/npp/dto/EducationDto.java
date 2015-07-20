package npp.dto;

public class EducationDto {
	private int seq;
	private String name;

	//You must declared default constructor for Framework.
	public EducationDto(){
		this.seq=0;
		this.name="";
	}

	public EducationDto(int seq, String name){
		this.seq = seq;
		this.setName(name);
	}

	public EducationDto(EducationDto dto) {
		this.seq = dto.getSeq();
		this.name = dto.getName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

}
