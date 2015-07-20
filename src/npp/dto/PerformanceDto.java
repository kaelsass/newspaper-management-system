package npp.dto;

public class PerformanceDto {
	private int seq;
	private String name;
	public PerformanceDto()
	{
		init(0, "");
	}
	public PerformanceDto(int seq, String name) {
		this.seq = seq;
		this.name = name;
	}
	private void init(int seq, String name) {
		this.seq = seq;
		this.name = name;
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
