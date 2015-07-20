package npp.dto;

import npp.utils.FileUtil;

public class PublicationDateDto {
	public static final int MONTH_TYPE = 1;
	public static final int WEEK_TYPE = 2;
	public static final int DAY_TYPE = 3;

	private int day;
	private int type;
	public PublicationDateDto(int day, int type) {
		this.day = day;
		this.type = type;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getPtypeName()
	{
		if(type == 1)
			return "Every Month";
		else if(type == 2)
			return "Every Week";
		else if(type == 3)
			return "Every Day";
		return "";
	}
	public String getFormatPDay()
	{
		if(day == 0)
			return "";
		String ord = FileUtil.getOrdinal(day);
		return ord + " Day " + getPtypeName();
	}
}
