package npp.dto;


public class ActivityDto {
	private int seq;
	private int taskSeq;
	private String name;

	//You must declared default constructor for Framework.
	public ActivityDto(){
		init(0, 0, "");
	}


	public ActivityDto(int seq, int taskSeq, String name){
		init(seq, taskSeq, name);
	}

	public ActivityDto(ActivityDto dto) {
		init(dto.getSeq(), dto.getTaskSeq(), dto.getName());
	}
	private void init(int seq, int taskSeq, String name){
		this.seq = seq;
		this.taskSeq = taskSeq;
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

	public int getTaskSeq() {
		return taskSeq;
	}


	public void setTaskSeq(int taskSeq) {
		this.taskSeq = taskSeq;
	}
}
