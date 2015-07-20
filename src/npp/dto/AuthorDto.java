package npp.dto;

public class AuthorDto {
	private int seq;
	private String name;
	private String address;
	private String workUnit;
	private String zipcode;
	private String phone;
	private String email;
	private String account;
	public AuthorDto(int seq, String name, String address, String workunit,
			String zipcode, String phone, String email, String account) {
		this.seq = seq;
		this.name = name;
		this.address = address;
		this.workUnit = workunit;
		this.zipcode = zipcode;
		this.phone = phone;
		this.email = email;
		this.account = account;
	}

	public AuthorDto() {
		this.seq = 0;
		this.name = "";
		this.address = "";
		this.workUnit = "";
		this.zipcode = "";
		this.phone = "";
		this.email = "";
		this.account = "";
	}

	public AuthorDto(AuthorDto dto) {
		this.seq = dto.getSeq();
		this.name = dto.getName();
		this.address = dto.getAddress();
		this.workUnit = dto.getWorkUnit();
		this.zipcode = dto.getZipcode();
		this.phone = dto.getPhone();
		this.email = dto.getEmail();
		this.account = dto.getAccount();
	}

	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getWorkUnit() {
		return workUnit;
	}
	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
}
