package npp.condition;

import java.util.Date;



public class PromotionSummaryCondition {
	private int[] promotionSeqs;
	private int[] newspaperSeqs;
	private Date from;
	private Date to;

	// You must declared default constructor for Framework.
	public PromotionSummaryCondition() {
		clear();
	}

	public void clear()
	{
		this.promotionSeqs = new int[0];
		this.newspaperSeqs = new int[0];
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

	public int[] getNewspaperSeqs() {
		return newspaperSeqs;
	}

	public void setNewspaperSeqs(int[] newspaperSeqs) {
		this.newspaperSeqs = newspaperSeqs;
	}

	public int[] getPromotionSeqs() {
		return promotionSeqs;
	}

	public void setPromotionSeqs(int[] promotionSeqs) {
		this.promotionSeqs = promotionSeqs;
	}

}
