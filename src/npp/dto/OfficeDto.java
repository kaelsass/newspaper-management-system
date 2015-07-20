package npp.dto;

public class OfficeDto {
	private int seq;
	private String name;

	//You must declared default constructor for Framework.
	public OfficeDto(){
		super();
	}

	public OfficeDto(int seq, String name){
		this.setSeq(seq);
		this.setName(name);
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

}
