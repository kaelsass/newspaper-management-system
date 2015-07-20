package npp.condition;

import java.util.Date;

import npp.dto.AgePair;



public class InventoryAgeCondition {
	private int[] newspaperSeqs;
	private Date from;
	private Date to;
	private AgePair agePair;

	public InventoryAgeCondition() {
		clear();
	}

	public void clear()
	{
		this.newspaperSeqs = new int[0];
		this.from = null;
		this.to = null;
		this.agePair = new AgePair();

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

	public void setAgePair(AgePair ageRange) {
		this.agePair = ageRange;
	}
	public AgePair getAgePair()
	{
		return agePair;
	}

}
