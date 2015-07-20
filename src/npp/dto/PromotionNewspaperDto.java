package npp.dto;


public class PromotionNewspaperDto {
	private int promotionSeq;
	private int newspaperSeq;

	//You must declared default constructor for Framework.
	public PromotionNewspaperDto(){
		init(0, 0);
	}


	public PromotionNewspaperDto(int promotionSeq, int newspaperSeq){
		init(promotionSeq, newspaperSeq);
	}

	private void init(int promotionSeq, int newspaperSeq){
		this.promotionSeq = promotionSeq;
		this.newspaperSeq = newspaperSeq;
	}


	public int getPromotionSeq() {
		return promotionSeq;
	}


	public void setPromotionSeq(int promotionSeq) {
		this.promotionSeq = promotionSeq;
	}


	public int getNewspaperSeq() {
		return newspaperSeq;
	}


	public void setNewspaperSeq(int newspaperSeq) {
		this.newspaperSeq = newspaperSeq;
	}


}
