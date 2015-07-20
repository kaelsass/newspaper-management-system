package npp.dto;

import java.util.Date;

import npp.utils.DateUtil;

public class NonSubscriberDto {
	private int seq;
	private String name;
	private String address;
	private String zipcode;
	private String workunit;
	private IssueDto issue;
	private int quantity;
	private PurposeDto purpose;
	private String phone;
	private String email;
	private WorkInfoDto employee;
	private Date orderDate;
	private PromotionDto promotion;
	private int age;
	private String sex;
	private int occupationSeq;
	private int educationSeq;

	public NonSubscriberDto() {
		init(0, "", "", "", "", new IssueDto(), 0, new PurposeDto(), "", "",
				new WorkInfoDto(), null, new PromotionDto(), 0, "", 0, 0);
	}

	public NonSubscriberDto(int seq, String name, String address,
			String zipcode, String workUnit, IssueDto issue, int quantity,
			PurposeDto purpose, String phone, String email,
			WorkInfoDto employee, Date orderDate, PromotionDto promotion,
			int age, String sex, int occupationSeq, int educationSeq) {
		init(seq, name, address, zipcode, workUnit, issue, quantity, purpose,
				phone, email, employee, orderDate, promotion, age, sex,
				occupationSeq, educationSeq);
	}

	public NonSubscriberDto(NonSubscriberDto dto) {
		init(dto.getSeq(), dto.getName(), dto.getAddress(), dto.getZipcode(),
				dto.getWorkunit(), dto.getIssue(), dto.getQuantity(),
				dto.getPurpose(), dto.getPhone(), dto.getEmail(),
				dto.getEmployee(), dto.getOrderDate(), dto.getPromotion(),
				dto.getAge(), dto.getSex(), dto.getOccupationSeq(),
				dto.getEducationSeq());
	}

	private void init(int seq, String name, String address, String zipcode,
			String workUnit, IssueDto issue, int quantity, PurposeDto purpose,
			String phone, String email, WorkInfoDto employee, Date orderDate,
			PromotionDto promotion, int age, String sex, int occupationSeq,
			int educationSeq) {
		this.seq = seq;
		this.name = name;
		this.address = address;
		this.zipcode = zipcode;
		this.workunit = workUnit;
		this.issue = (issue == null ? new IssueDto() : issue);
		this.quantity = quantity;
		this.purpose = (purpose == null ? new PurposeDto() : purpose);
		this.phone = phone;
		this.email = email;
		this.employee = (employee == null ? new WorkInfoDto() : employee);
		this.orderDate = orderDate;
		this.promotion = promotion;
		this.age = age;
		this.sex = sex;
		this.occupationSeq = occupationSeq;
		this.educationSeq = educationSeq;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public IssueDto getIssue() {
		return issue;
	}

	public void setIssue(IssueDto issue) {
		this.issue = issue;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public PurposeDto getPurpose() {
		return purpose;
	}

	public void setPurpose(PurposeDto purpose) {
		this.purpose = purpose;
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

	public WorkInfoDto getEmployee() {
		return employee;
	}

	public void setEmployee(WorkInfoDto employee) {
		this.employee = employee;
	}

	public String getWorkunit() {
		return workunit;
	}

	public void setWorkunit(String workunit) {
		this.workunit = workunit;
	}

	public String getFormatOrderDate() {
		return DateUtil.getDateFormatInstance().format(orderDate);
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
		this.quantity = 0;
		this.purpose = new PurposeDto();
		this.phone = "";
		this.email = "";
		this.employee = new WorkInfoDto();
		this.orderDate = null;
		this.promotion = new PromotionDto();
	}
}
