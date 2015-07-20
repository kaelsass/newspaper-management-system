package npp.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import npp.utils.DateUtil;


public class PromotionDto {
	private int seq;
	private String name;
	private String place;
	private List<NewspaperDto> newspapers;
	private List<WorkInfoDto> admins;
	private Date dateFrom;
	private Date dateTo;

	//You must declared default constructor for Framework.
	public PromotionDto(){
		init(0, "", "", new ArrayList<NewspaperDto>(), new ArrayList<WorkInfoDto>(), null, null);
	}


	public PromotionDto(int seq, String name, String place, List<NewspaperDto> newspapers, List<WorkInfoDto> admins, Date dateFrom, Date dateTo){
		init(seq, name, place, newspapers, admins, dateFrom, dateTo);
	}

	public PromotionDto(PromotionDto dto) {
		init(dto.getSeq(), dto.getName(), dto.getPlace(), dto.getNewspapers(), dto.getAdmins(), dto.getDateFrom(), dto.getDateTo());
	}
	private void init(int seq, String name, String place, List<NewspaperDto> newspapers, List<WorkInfoDto> admins, Date dateFrom, Date dateTo){
		this.seq = seq;
		this.name = name;
		this.place = place;
		this.newspapers = newspapers;
		this.admins = admins;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
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


	public String getPlace() {
		return place;
	}


	public void setPlace(String place) {
		this.place = place;
	}


	public List<NewspaperDto> getNewspapers() {
		return newspapers;
	}


	public void setNewspapers(List<NewspaperDto> newspapers) {
		this.newspapers = newspapers;
	}


	public List<WorkInfoDto> getAdmins() {
		return admins;
	}


	public void setAdmins(List<WorkInfoDto> admins) {
		this.admins = admins;
	}


	public Date getDateFrom() {
		return dateFrom;
	}


	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}


	public Date getDateTo() {
		return dateTo;
	}


	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	public String getFormatNewspaperNames()
	{
		StringBuffer sb = new StringBuffer();
		for(NewspaperDto dto : newspapers)
		{
			sb.append(dto.getName() + ", ");
		}
		if(sb.length() > 0)
		{
			return sb.substring(0, sb.length()-2);
		}
		else
			return sb.toString();
	}
	public String getFormatAdmins()
	{
		StringBuffer sb = new StringBuffer();
		for(WorkInfoDto dto : admins)
		{
			sb.append(dto.getName() + ", ");
		}
		if(sb.length() > 0)
		{
			return sb.substring(0, sb.length()-2);
		}
		else
			return sb.toString();
	}
	public String getFormatDateFrom()
	{
		if(dateFrom == null)
			return "";
		return DateUtil.getDateFormatInstance().format(dateFrom);
	}
	public String getFormatDateTo()
	{
		if(dateTo == null)
			return "";
		return DateUtil.getDateFormatInstance().format(dateTo);
	}

}
