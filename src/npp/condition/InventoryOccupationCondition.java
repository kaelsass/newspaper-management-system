package npp.condition;

import java.util.Date;



public class InventoryOccupationCondition {
	private int[] newspaperSeqs;
	private Date from;
	private Date to;
	private int occupationSeq;

	public InventoryOccupationCondition() {
		clear();
	}

	public void clear()
	{
		this.newspaperSeqs = new int[0];
		this.from = null;
		this.to = null;
		this.occupationSeq = 0;

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

	public int getOccupationSeq() {
		return occupationSeq;
	}

	public void setOccupationSeq(int occupationSeq) {
		this.occupationSeq = occupationSeq;
	}
}
