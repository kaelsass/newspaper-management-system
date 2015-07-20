package npp.dto;

import java.util.Date;

import npp.utils.DateUtil;

public class ReturnDto {
	private int seq;
	private IssueDto issue;
	private double unitPrice;
	private int quantity;
	private double discount;
	private WorkInfoDto employee;
	private Date orderDate;
	private PromotionDto promotion;

	// You must declared default constructor for Framework.
	public ReturnDto() {
		init(0, new IssueDto(), 0, 0, 1, new WorkInfoDto(), null, new PromotionDto());
	}

	public ReturnDto(int seq, IssueDto issue, double unitPrice, int quantity,
			double discount, WorkInfoDto employee, Date orderDate, PromotionDto promotion) {
		init(seq, issue, unitPrice, quantity, discount, employee, orderDate, promotion);
	}

	public ReturnDto(ReturnDto dto) {
		init(dto.getSeq(), dto.getIssue(), dto.getUnitPrice(),
				dto.getQuantity(), dto.getDiscount(), dto.getEmployee(),
				dto.getOrderDate(),dto.getPromotion());
	}

	private void init(int seq, IssueDto issue, double unitPrice, int quantity,
			double discount, WorkInfoDto employee, Date orderDate, PromotionDto promotion) {
		this.seq = seq;
		this.issue = issue;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.discount = discount;
		this.employee = employee;
		this.orderDate = orderDate;
		this.promotion = promotion;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public IssueDto getIssue() {
		return issue;
	}

	public void setIssue(IssueDto issue) {
		this.issue = issue;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public WorkInfoDto getEmployee() {
		return employee;
	}

	public void setEmployee(WorkInfoDto employee) {
		this.employee = employee;
	}

	public String getFormatOrderDate() {
		return DateUtil.getDateFormatInstance().format(orderDate);
	}

	public double getMoney() {
		return unitPrice * quantity;
	}

	public double getPayables() {
		return getMoney() * discount;
	}

	public PromotionDto getPromotion() {
		return promotion;
	}

	public void setPromotion(PromotionDto promotion) {
		this.promotion = promotion;
	}
}
