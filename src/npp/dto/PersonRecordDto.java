package npp.dto;

import java.util.ArrayList;

public class PersonRecordDto {
	private WorkInfoDto employee;
	private String total;
	private ArrayList<DayRecord> list;
	public PersonRecordDto()
	{
		list = new ArrayList<DayRecord>();
	}
	public String getTotal() {
		double sum = 0;
		for(DayRecord record : list)
		{
			sum += Double.parseDouble(record.getTotal());
		}
		total = String.format("%.2f", sum);
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public void add(DayRecord dto)
	{
		list.add(dto);
	}
	public WorkInfoDto getEmployee() {
		return employee;
	}
	public void setEmployee(WorkInfoDto employee) {
		this.employee = employee;
	}
	public ArrayList<DayRecord> getList() {
		return list;
	}
	public void setList(ArrayList<DayRecord> list) {
		this.list = list;
	}

}
