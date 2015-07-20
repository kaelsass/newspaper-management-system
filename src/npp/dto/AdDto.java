package npp.dto;

import java.util.Date;

import npp.utils.DateUtil;

public class AdDto {
	private String ID;
	private String workUnit;
	private String address;
	private NewspaperDto newspaper;
	private int pageCount;
	private int pageArea;
	private Date dateFrom;
	private int count;
	private double unitPrice;
	private double discount;
	private double moneyPay;
	private String contactPerson;
	private String phone;
	private String email;
	private WorkInfoDto employee;
	private Date orderDate;
	private PromotionDto promotion;

	public AdDto() {
		init("", "", "", new NewspaperDto(), 0, 0, null, 0, 0, 1, 0, "", "",
				"", new WorkInfoDto(), null, new PromotionDto());
	}

	// id, workUnit, address, newspaperDto, pageCount, pageArea, dateFrom,
	// dateTo,
	// unitPrice, discount, moneyPay, contactPerson, phone, email, workInfoDto,
	// orderDate
	public AdDto(String ID, String workUnit, String address,
			NewspaperDto newspaper, int pageCount, int pageArea, Date dateFrom,
			int count, double unitPrice, double discount, double moneyPay,
			String contactPerson, String phone, String email,
			WorkInfoDto employee, Date orderDate, PromotionDto promotion) {
		init(ID, workUnit, address, newspaper, pageCount, pageArea, dateFrom,
				count, unitPrice, discount, moneyPay, contactPerson, phone,
				email, employee, orderDate, promotion);
	}

	public AdDto(AdDto dto) {
		init(dto.getID(), dto.getWorkUnit(), dto.getAddress(),
				dto.getNewspaper(), dto.getPageCount(), dto.getPageArea(),
				dto.getDateFrom(), dto.getCount(), dto.getUnitPrice(),
				dto.getDiscount(), dto.getMoneyPay(), dto.getContactPerson(),
				dto.getPhone(), dto.getEmail(), dto.getEmployee(),
				dto.getOrderDate(), dto.getPromotion());
	}

	private void init(String ID, String workUnit, String address,
			NewspaperDto newspaper, int pageCount, int pageArea, Date dateFrom,
			int count, double unitPrice, double discount, double moneyPay,
			String contactPerson, String phone, String email,
			WorkInfoDto employee, Date orderDate, PromotionDto promotion) {
		this.ID = ID;
		this.workUnit = workUnit;
		this.address = address;
		this.newspaper = newspaper;
		this.pageCount = pageCount;
		this.pageArea = pageArea;
		this.dateFrom = dateFrom;
		this.unitPrice = unitPrice;
		this.count = count;
		this.discount = discount;
		this.moneyPay = moneyPay;
		this.contactPerson = contactPerson;
		this.phone = phone;
		this.email = email;
		this.employee = employee;
		this.orderDate = orderDate;
		this.promotion = promotion;
	}

	public String getWorkUnit() {
		return workUnit;
	}

	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public NewspaperDto getNewspaper() {
		return newspaper;
	}

	public void setNewspaper(NewspaperDto newspaper) {
		this.newspaper = newspaper;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
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

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public int getPageArea() {
		return pageArea;
	}

	public void setPageArea(int pageArea) {
		this.pageArea = pageArea;
	}

	public WorkInfoDto getEmployee() {
		return employee;
	}

	public void setEmployee(WorkInfoDto employee) {
		this.employee = employee;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getFormatDateFrom() {
		return DateUtil.getDateWithTimeFormatInstance().format(dateFrom);
	}

	public String getFormatOrderDate() {
		return DateUtil.getDateWithTimeFormatInstance().format(orderDate);
	}

	public double getPayables() {
		return unitPrice * count;
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
}
