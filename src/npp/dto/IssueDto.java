package npp.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IssueDto {
	private int seq;
	private int number;
	private Date time;
	private int quantity;
	private NewspaperDto newspaper;
	private StatusDto status;

	public IssueDto(IssueDto dto) {
		init(dto.getSeq(), dto.getNumber(), dto.getTime(), dto.getQuantity(), dto.getNewspaper(), dto.getStatus());

	}
	public IssueDto()
	{
		init(0, 0, null, 0, new NewspaperDto(), new StatusDto());
	}
	public IssueDto(int seq, int number, Date time, int quantity, NewspaperDto newspaper,
			StatusDto status) {
		init(seq, number, time, quantity, newspaper, status);
	}
	public void init(int seq, int number, Date time, int quantity, NewspaperDto newspaper, StatusDto status)
	{
		this.seq = seq;
		this.number = number;
		this.time = time;
		this.quantity = quantity;
		this.newspaper = newspaper;
		this.status = status;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public NewspaperDto getNewspaper() {
		return newspaper;
	}
	public void setNewspaper(NewspaperDto newspaper) {
		this.newspaper = newspaper;
	}
	public StatusDto getStatus() {
		return status;
	}
	public void setStatus(StatusDto status) {
		this.status = status;
	}
	public String getFormatIssue()
	{
		if(number <= 0 || time == null)
			return "";
		return "Issue " + number + " : " + getFormatTime();
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getFormatTime()
	{
		if(time == null)
			return "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(time);
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof IssueDto)
		{
			IssueDto i2 = (IssueDto) obj;
			if(this.seq == i2.getSeq())
				return true;
		}
		return false;
	}

}
