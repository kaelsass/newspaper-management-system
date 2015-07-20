package npp.dto;


public class PromotionAdminDto {
	private int promotionSeq;
	private String employeeID;

	//You must declared default constructor for Framework.
	public PromotionAdminDto(){
		init(0, "");
	}


	public PromotionAdminDto(int promotionSeq, String employeeID){
		init(promotionSeq, employeeID);
	}

	private void init(int promotionSeq, String employeeID){
		this.promotionSeq = promotionSeq;
		this.employeeID = employeeID;
	}
	public String getEmployeeID() {
		return employeeID;
	}


	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}


	public int getPromotionSeq() {
		return promotionSeq;
	}


	public void setPromotionSeq(int promotionSeq) {
		this.promotionSeq = promotionSeq;
	}

}
