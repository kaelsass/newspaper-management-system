package npp.dto;

import java.util.Date;

import npp.utils.DateUtil;

public class RetailDto {
	private int seq;
	private IssueDto issue;
	private double unitPrice;
	private int quantity;
	private double discount;
	private double moneyPay;
	private WorkInfoDto employee;
	private Date orderDate;
	private PromotionDto promotion;
	private int age;
	private String sex;
	private int occupationSeq;
	private int educationSeq;

	// You must declared default constructor for Framework.
	public RetailDto() {
		init(0, new IssueDto(), 0, 0, 1, 0, new WorkInfoDto(), null, new PromotionDto(), 0, "", 0, 0);
	}

	public RetailDto(int seq, IssueDto issue, double unitPrice, int quantity,
			double discount, double moneyPay, WorkInfoDto employee,
			Date orderDate, PromotionDto promotion, int age, String sex, int occupationSeq,
			int educationSeq) {
		init(seq, issue, unitPrice, quantity, discount, moneyPay, employee,
				orderDate, promotion, age, sex, occupationSeq, educationSeq);
	}

	public RetailDto(RetailDto dto) {
		init(dto.getSeq(), dto.getIssue(), dto.getUnitPrice(),
				dto.getQuantity(), dto.getDiscount(), dto.getMoneyPay(),
				dto.getEmployee(), dto.getOrderDate(), dto.getPromotion(), dto.getAge(), dto.getSex(),dto.getOccupationSeq(), dto.getEducationSeq());
	}

	private void init(int seq, IssueDto issue, double unitPrice, int quantity,
			double discount, double moneyPay, WorkInfoDto employee,
			Date orderDate, PromotionDto promotion, int age, String sex, int occupationSeq,
			int educationSeq) {
		this.seq = seq;
		this.issue = issue;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.discount = discount;
		this.moneyPay = moneyPay;
		this.employee = employee;
		this.orderDate = orderDate;
		this.promotion = promotion;
		this.age = age;
		this.sex = sex;
		this.occupationSeq = occupationSeq;
		this.educationSeq = educationSeq;
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

	public double getMoneyPay() {
		return moneyPay;
	}

	public void setMoneyPay(double moneyPay) {
		this.moneyPay = moneyPay;
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

	public double getChange() {
		return moneyPay - getPayables();
	}

	public PromotionDto getPromotion() {
		return promotion;
	}

	public void setPromotion(PromotionDto promotion) {
		this.promotion = promotion;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}


	public int getOccupationSeq() {
		return occupationSeq;
	}

	public void setOccupationSeq(int occupationSeq) {
		this.occupationSeq = occupationSeq;
	}

	public int getEducationSeq() {
		return educationSeq;
	}

	public void setEducationSeq(int educationSeq) {
		this.educationSeq = educationSeq;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void clearOrderInfo() {
		this.issue = new IssueDto();
		this.unitPrice = 0;
		this.quantity = 0;
		this.discount = 0;
		this.moneyPay = 0;
		this.employee = new WorkInfoDto();
		this.orderDate = null;
		this.promotion = new PromotionDto();
	}
}
