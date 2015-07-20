package npp.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DatePair {
	private Date from;
	private Date to;
	public DatePair()
	{
		from = null;
		to = null;
	}
	public Date getFrom() {
		return from;
	}
	public void setFrom(Date from) {
		this.from = from;
	}
	public Date getTo() {
		return to;
	}
	public void setTo(Date to) {
		this.to = to;
	}
	public String getFormatDate()
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
		return df.format(getFrom());
	}
}
