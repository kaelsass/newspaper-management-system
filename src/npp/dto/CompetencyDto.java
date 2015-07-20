package npp.dto;

public class CompetencyDto {
	private int seq;
	private String name;
	private String description;

	//You must declared default constructor for Framework.
	public CompetencyDto(){
		init(0, "", "");
	}

	public CompetencyDto(int seq, String name, String description){
		init(seq, name, description);
	}


	public CompetencyDto(CompetencyDto dto) {
		init(dto.getSeq(), dto.getName(), dto.getDescription());
	}
	public void init(int seq,String name, String descpt)
	{
		this.seq = seq;
		this.name = name;
		this.description = descpt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}
}
