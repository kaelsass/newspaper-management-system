package npp.condition;

import java.util.Date;



public class InventorySexCondition {
	private int[] newspaperSeqs;
	private Date from;
	private Date to;
	private String sex;

	public InventorySexCondition() {
		clear();
	}

	public void clear()
	{
		this.newspaperSeqs = new int[0];
		this.from = null;
		this.to = null;
		this.sex = "";

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

	public int[] getNewspaperSeqs() {
		return newspaperSeqs;
	}

	public void setNewspaperSeqs(int[] newspaperSeqs) {
		this.newspaperSeqs = newspaperSeqs;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

}
