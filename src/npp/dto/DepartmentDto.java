package npp.dto;

public class DepartmentDto {
	private int seq;
	private String name;
	private String description;
	private int parentSeq;

	//You must declared default constructor for Framework.
	public DepartmentDto(){
		this.seq = 0;
		this.name = "";
		this.description = "";
		this.parentSeq=0;
	}

	public DepartmentDto(int seq, String name, String description, int parentSeq){
		this.setSeq(seq);
		this.setName(name);
		this.description = description;
		this.parentSeq = parentSeq;
	}

	public int getSeq() {
		return seq;
	}

	public String getName() {
		return name;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getParentSeq() {
		return parentSeq;
	}

	public void setParentSeq(int parentSeq) {
		this.parentSeq = parentSeq;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
