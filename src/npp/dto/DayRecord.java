package npp.dto;

import java.util.ArrayList;

public class DayRecord {
	private String date;
	private String total;
	private ArrayList<PunchDto> list;
	public DayRecord()
	{
		list = new ArrayList<PunchDto>();
	}
	public String getTotal() {
		double sum = 0;
		for(PunchDto record : list)
		{
			sum += Double.parseDouble(record.getDuration());
		}
		total = String.format("%.2f", sum);
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public void add(PunchDto dto)
	{
		list.add(dto);
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public ArrayList<PunchDto> getList() {
		return list;
	}
	public void setList(ArrayList<PunchDto> list) {
		this.list = list;
	}
}
