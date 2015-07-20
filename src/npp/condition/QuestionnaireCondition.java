package npp.condition;

import java.util.Date;

import npp.utils.DateUtil;


public class QuestionnaireCondition {
	private String name;
	private int[] promotionSeqs;
	private Date from;
	private Date to;
	private int published;

	public QuestionnaireCondition() {
		clear();
	}

	public DynamicQuery genQuery() {
		DynamicQuery query = new DynamicQuery();
		if (name != null && !name.equals("")) {
			query.addParameter(new Parameter("name", "like", "%" + name + "%" ));
		}
		if (from != null) {
			query.addParameter(new Parameter("mod_date", ">=", DateUtil.getDateFormatInstance().format(from)));
		}
		if (to != null) {
			query.addParameter(new Parameter("mod_date", "<=", DateUtil.getDateFormatInstance().format(to)));
		}
		//0:all, 1:yes, 2:no
		if(published == 1)
		{
			query.addParameter(new Parameter("published", "=", 1));
		}
		else if(published == 2)
		{
			query.addParameter(new Parameter("published", "=", 0));
		}

		for(int seq : promotionSeqs)
		{
			Parameter para = new Parameter("promotion_seq", "=", seq);
			para.setType(Parameter.OR);
			query.addParameter(para);
		}
		return query;
	}
	public void clear()
	{
		this.name = "";
		this.promotionSeqs = new int[0];
		this.from = null;
		this.to = null;
		this.published = 0;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int[] getPromotionSeqs() {
		return promotionSeqs;
	}

	public void setPromotionSeqs(int[] promotionSeqs) {
		this.promotionSeqs = promotionSeqs;
	}

	public int getPublished() {
		return published;
	}

	public void setPublished(int published) {
		this.published = published;
	}
}
