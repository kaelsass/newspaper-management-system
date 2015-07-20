package npp.condition;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import npp.utils.DateUtil;



public class PromotionCondition {
	private String name;
	private String place;
	private String[] employeeIDs;
	private int[] newspaperSeqs;
	private List<Integer> promotionSeqs;
	private Date dateFrom;
	private Date dateTo;

	// You must declared default constructor for Framework.
	public PromotionCondition() {
		clear();
	}

	public DynamicQuery genQuery() {
		DynamicQuery query = new DynamicQuery();
		if(name != null && !name.equals(""))
		{
			query.addParameter(new Parameter("name", "like", "%" + name + "%"));
		}
//		for(Integer seq : newspaperSeqs)
//		{
//			Parameter para = new Parameter("_seq", "=", seq);
//			para.setType(Parameter.OR);
//			query.addParameter(para);
//		}
		if(dateFrom != null)
		{
			query.addParameter(new Parameter("start_date", ">=", DateUtil.getDateWithTimeFormatInstance().format(dateFrom)));
		}
		if(dateTo != null)
		{
			query.addParameter(new Parameter("end_date", "<=", DateUtil.getDateWithTimeFormatInstance().format(dateTo)));
		}
		for(int seq : promotionSeqs)
		{
			Parameter para = new Parameter("seq", "=", seq);
			para.setType(Parameter.OR);
			query.addParameter(para);
		}
		return query;
	}
	public void clear()
	{
		this.name = "";
		this.place = "";
		this.employeeIDs = new String[0];
		this.newspaperSeqs = new int[0];
		this.promotionSeqs = new ArrayList<Integer>();
		dateFrom = null;
		dateTo = null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}



	public List<Integer> getPromotionSeqs() {
		return promotionSeqs;
	}

	public void setPromotionSeqs(List<Integer> promotionSeqs) {
		this.promotionSeqs = promotionSeqs;
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

	public String[] getEmployeeIDs() {
		return employeeIDs;
	}

	public void setEmployeeIDs(String[] employeeIDs) {
		this.employeeIDs = employeeIDs;
	}

	public int[] getNewspaperSeqs() {
		return newspaperSeqs;
	}

	public void setNewspaperSeqs(int[] newspaperSeqs) {
		this.newspaperSeqs = newspaperSeqs;
	}

}
