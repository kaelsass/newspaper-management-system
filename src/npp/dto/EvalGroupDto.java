package npp.dto;

public class EvalGroupDto {
	private int seq;
	private String name;
	private boolean choose;

	//You must declared default constructor for Framework.
	public EvalGroupDto(){
		init(0, "", true);
	}

	public EvalGroupDto(int seq, String name, boolean choose){
		init(seq, name, choose);
	}

	public EvalGroupDto(EvalGroupDto dto) {
		init(dto.getSeq(), dto.getName(), dto.isChoose());
	}
	public void init(int seq, String name, boolean choose)
	{
		this.seq = seq;
		this.name = name;
		this.choose = choose;
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

	public boolean isChoose() {
		return choose;
	}

	public void setChoose(boolean choose) {
		this.choose = choose;
	}

}
