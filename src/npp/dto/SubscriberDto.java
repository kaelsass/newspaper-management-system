package npp.dto;

import java.util.Date;

import npp.utils.DateUtil;

public class SubscriberDto {
	private int seq;
	private String name;
	private String address;
	private String zipcode;
	private NewspaperDto newspaper;
	private Date dateFrom;
	private int monthNumber;
	private int quantity;
	private double discount;
	private Date orderDate;
	private double moneyPay;
	private WorkInfoDto employee;
	private PayTypeDto payType;
	private PromotionDto promotion;
	private int age;
	private String sex;
	private int occupationSeq;
	private int educationSeq;

	public SubscriberDto() {
		init(0, "", "", "", new NewspaperDto(), null, 0, 0, 1, null, 0,
				new WorkInfoDto(), new PayTypeDto(), new PromotionDto(), 0, "", 0, 0);
	}

	public SubscriberDto(int seq, String name, String address, String zipcode,
			NewspaperDto newspaper, Date dateFrom, int monthNumber,
			Date orderDate, int quantity, double discount, double moneyPay,
			WorkInfoDto employee, PayTypeDto paytype, PromotionDto promotion, int age, String sex, int occupationSeq, int educationSeq) {
		init(seq, name, address, zipcode, newspaper, dateFrom, monthNumber,
				quantity, discount, orderDate, moneyPay, employee, paytype,
				promotion, age, sex, occupationSeq, educationSeq);
	}

	public SubscriberDto(SubscriberDto dto) {
		init(dto.getSeq(), dto.getName(), dto.getAddress(), dto.getZipcode(),
				dto.getNewspaper(), dto.getDateFrom(), dto.getMonthNumber(),
				dto.getQuantity(), dto.getDiscount(), dto.getOrderDate(),
				dto.getMoneyPay(), dto.getEmployee(), dto.getPayType(),
				dto.getPromotion(), dto.getAge(), dto.getSex(),
				dto.getOccupationSeq(), dto.getEducationSeq());
	}

	private void init(int seq, String name, String address, String zipcode,
			NewspaperDto newspaper, Date dateFrom, int monthNumber,
			int quantity, double discount, Date orderDate, double moneyPay,
			WorkInfoDto employee, PayTypeDto paytype, PromotionDto promotion,
			int age, String sex, int occupationSeq, int educationSeq) {
		this.seq = seq;
		this.name = name;
		this.address = address;
		this.zipcode = zipcode;
		this.newspaper = (newspaper == null ? new NewspaperDto() : newspaper);
		this.dateFrom = dateFrom;
		this.monthNumber = monthNumber;
		this.quantity = quantity;
		this.discount = discount;
		this.orderDate = orderDate;
		this.moneyPay = moneyPay;
		this.employee = employee;
		this.payType = (paytype == null ? new PayTypeDto() : paytype);
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

	public WorkInfoDto getEmployee() {
		return employee;
	}

	public void setEmployee(WorkInfoDto employee) {
		this.employee = employee;
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

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getMonthNumber() {
		return monthNumber;
	}

	public void setMonthNumber(int monthNumber) {
		this.monthNumber = monthNumber;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public NewspaperDto getNewspaper() {
		return newspaper;
	}

	public void setNewspaper(NewspaperDto newspaper) {
		this.newspaper = newspaper;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFormatDateFrom() {
		if(dateFrom == null)
			return "";
		return DateUtil.getDateFormatInstance().format(dateFrom);
	}

	public double getMoney() {
		double money = 0;
		if (newspaper.getPdate().getType() == PublicationDateDto.MONTH_TYPE)
			money = newspaper.getIssuePrice() * monthNumber * quantity;
		else if (newspaper.getPdate().getType() == PublicationDateDto.WEEK_TYPE)
			money = newspaper.getIssuePrice() * getIssueCountByWeeks()
					* quantity;
		else if (newspaper.getPdate().getType() == PublicationDateDto.DAY_TYPE)
			money = newspaper.getIssuePrice() * getIssueCountByDays() * quantity;
		return money;
	}
	public String getFormatPayable()
	{
		return String.format("%.2f", getPayables());
	}

	private int getIssueCountByDays() {
		return DateUtil.getDaysBetweenDates(dateFrom,
				DateUtil.getDateAfterMonths(dateFrom, monthNumber));
	}

	private int getIssueCountByWeeks() {
		if(dateFrom == null)
			return 0;
		return DateUtil.getWeeksBetweenDates(dateFrom,
				DateUtil.getDateAfterMonths(dateFrom, monthNumber));
	}

	public String getFormatOrderDate() {
		return DateUtil.getDateFormatInstance().format(orderDate);
	}

	public PayTypeDto getPayType() {
		return payType;
	}

	public void setPayType(PayTypeDto payType) {
		this.payType = payType;
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
	public void clearOrderInfo()
	{
		this.newspaper = new NewspaperDto();
		this.dateFrom = null;
		this.monthNumber = 0;
		this.quantity = 0;
		this.discount = 0;
		this.orderDate = null;
		this.moneyPay = 0;
		this.employee = new WorkInfoDto();
		this.payType = new PayTypeDto();
		this.promotion = new PromotionDto();

	}
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof SubscriberDto))
			return false;
		SubscriberDto dto = (SubscriberDto) obj;
		return (this.getNewspaper().getSeq() == dto.getNewspaper().getSeq()) && (this.getFormatDateFrom().equals(dto.getFormatDateFrom()))
				&& (this.getMonthNumber() == dto.getMonthNumber()) && (this.getQuantity() == dto.getQuantity());
	}
}
