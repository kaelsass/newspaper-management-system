package npp.dto;

public class SubjectDto {
	private int seq;
	private String name;

	public SubjectDto()
	{
		this.seq = 0;
		this.name = "";
	}

	public SubjectDto(int seq, String name) {
		this.seq = seq;
		this.name = name;
	}

	public SubjectDto(SubjectDto dto) {
		this.seq = dto.getSeq();
		this.name = dto.getName();
	}

	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
