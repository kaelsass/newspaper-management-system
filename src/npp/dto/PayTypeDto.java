package npp.dto;

public class PayTypeDto {
	private int seq;
	private String name;
	public PayTypeDto()
	{
		init(0, "");
	}
	public PayTypeDto(int seq, String name) {
		init(seq, name);
	}
	public PayTypeDto(PayTypeDto dto) {
		init(dto.getSeq(), dto.getName());
	}
	private void init(int seq, String name)
	{
		this.seq = seq;
		this.name = name;
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
