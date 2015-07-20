package npp.condition;

import java.util.Date;



public class InventoryCondition {
	private int[] newspaperSeqs;
	private int issueSeq;
	private Date from;
	private Date to;
	private int promotionSeq;

	public InventoryCondition() {
		clear();
	}

	public void clear()
	{
		this.newspaperSeqs = new int[0];
		this.issueSeq = 0;
		this.promotionSeq = 0;
		this.from = null;
		this.to = null;

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
	public int getIssueSeq() {
		return issueSeq;
	}

	public void setIssueSeq(int issueSeq) {
		this.issueSeq = issueSeq;
	}

	public int[] getNewspaperSeqs() {
		return newspaperSeqs;
	}

	public void setNewspaperSeqs(int[] newspaperSeqs) {
		this.newspaperSeqs = newspaperSeqs;
	}

	public void setPromotionSeq(int seq) {
		this.promotionSeq = seq;
	}
	public int getPromotionSeq()
	{
		return promotionSeq;
	}
}
