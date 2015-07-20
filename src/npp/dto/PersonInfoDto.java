package npp.dto;

import java.text.SimpleDateFormat;
import java.util.Date;




public class PersonInfoDto {
	private String id;
	private String name;
	private SexDto sex;
	private Date birth;
	private String phone;
	private String email;
	private String photoName;


	//You must declared default constructor for Framework.
	public PersonInfoDto(){
		this.id = "";
		this.name="";
		this.sex = new SexDto();
		this.birth = null;
		this.phone = "";
		this.email = "";
		this.photoName = "";
	}

	public PersonInfoDto(String id, String name, SexDto sexDto, Date birth, String phone, String email, String photoName){
		this.id = id;
		this.name = name;
		this.sex = (sexDto == null ? new SexDto() : sexDto);
		this.birth = birth;
		this.phone = phone;
		this.email = email;
		this.photoName = photoName;
	}

	public PersonInfoDto(PersonInfoDto dto) {
		this.id = dto.getId();
		this.name = dto.getName();
		this.sex = dto.getSex();
		this.birth = dto.getBirth();
		this.phone = dto.getPhone();
		this.email = dto.getEmail();
		this.photoName = dto.getPhotoName();
	}

	public String getName()
	{
		return name;
	}

	public String getFirstName() {
		String[] strs = name.split(" ");
		return (strs.length >= 1 ? strs[0]:"");
	}

	public String getLastName() {
		String[] strs = name.split(" ");
		return (strs.length == 2 ? strs[1]:"");
	}

	public void setFirstName(String str) {
		String[] strs = name.split(" ");
		if(strs.length >= 1)
		{
			strs[0] = str;
			StringBuilder sb = new StringBuilder();
			for(String s : strs)
			{
				sb.append(s + " ");
			}
			name = sb.toString().trim();
		}
		else
			name = str.trim();
	}

	public void setLastName(String str) {
		String[] strs = name.split(" ");
		if(strs.length >= 2)
		{
			strs[1] = str;
			StringBuilder sb = new StringBuilder();
			for(String s : strs)
			{
				sb.append(s + " ");
			}
			name = sb.toString().trim();
		}
		else
			name = name + str.trim();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}
	public String getPhotoName()
	{
		if(photoName == null || photoName.equals(""))
			return "default.gif";
		return photoName;
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

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getFormatBirth()
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if(birth != null)
			return df.format(birth);
		else
			return "yyyy-MM-dd";
	}

	public SexDto getSex() {
		return sex;
	}

	public void setSex(SexDto sex) {
		this.sex = sex;
	}
}
