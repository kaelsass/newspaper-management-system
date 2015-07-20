package npp.condition;

import java.util.Date;

import npp.utils.DateUtil;


public class QuestionCondition {
	private String name;
	private int[] typeSeqs;
	private Date from;
	private Date to;

	public QuestionCondition() {
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

		for(int seq : typeSeqs)
		{
			Parameter para = new Parameter("type_seq", "=", seq);
			para.setType(Parameter.OR);
			query.addParameter(para);
		}
		return query;
	}
	public void clear()
	{
		this.name = "";
		this.typeSeqs = new int[]{1, 2, 3};
		this.from = null;
		this.to = null;
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

	public int[] getTypeSeqs() {
		return typeSeqs;
	}

	public void setTypeSeqs(int[] typeSeqs) {
		this.typeSeqs = typeSeqs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
