package npp.dto;

public class ReminderDto {
	public static final String BEFORE_5_MINUTES = "5 Minutes Before";
	public static final String BEFORE_10_MINUTES = "10 Minutes Before";
	public static final String BEFORE_30_MINUTES = "Half an hour Before";
	public static final String BEFORE_60_MINUTES = "1 hour Before";
	public static final String BEFORE_120_MINUTES = "2 hours Before";
	public static final String BEFORE_720_MINUTES = "Half a day Before";
	public static final String BEFORE_1440_MINUTES = "1 day Before";


	private int seq;
	private String name;

	//You must declared default constructor for Framework.
	public ReminderDto(){
		this.seq=1;
		this.name="None";
	}

	public ReminderDto(int seq, String name){
		this.seq = seq;
		this.setName(name);
	}

	public ReminderDto(ReminderDto dto) {
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
	public int getMinute()
	{
		if(name.equals(BEFORE_5_MINUTES))
			return 5;
		else if(name.equals(BEFORE_10_MINUTES))
			return 10;
		else if(name.equals(BEFORE_30_MINUTES))
			return 30;
		else if(name.equals(BEFORE_60_MINUTES))
			return 60;
		else if (name.equals(BEFORE_120_MINUTES))
			return 120;
		else if(name.equals(BEFORE_720_MINUTES))
			return 720;
		else if(name.equals(BEFORE_1440_MINUTES))
			return 1440;
		else
			return 0;

	}

}
