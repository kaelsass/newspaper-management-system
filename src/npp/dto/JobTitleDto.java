package npp.dto;

public class JobTitleDto {
	private int seq;
	private String name;
	private String description;
	private String note;

	//You must declared default constructor for Framework.
	public JobTitleDto(){
		this.seq = 0;
		this.name="";
		this.description="";
		this.note="";
	}

	public JobTitleDto(int seq, String name, String description, String note){
		this.seq = seq;
		this.name = name;
		this.description = description;
		this.note = note;
	}


	public JobTitleDto(JobTitleDto dto) {
		this.seq = dto.seq;
		this.name = dto.name;
		this.description = dto.description;
		this.note = dto.note;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}
}
