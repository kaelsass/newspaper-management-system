package npp.condition;

import java.util.Date;

import npp.utils.DateUtil;


public class RecordCondition {
	private int questionnaireSeq;
	private Date from;
	private Date to;

	public RecordCondition() {
		clear();
	}

	public DynamicQuery genQuery() {
		DynamicQuery query = new DynamicQuery();
		if (questionnaireSeq > 0) {
			query.addParameter(new Parameter("questionnaire_seq", "=", questionnaireSeq ));
		}
		if (from != null) {
			query.addParameter(new Parameter("add_date", ">=", DateUtil.getDateFormatInstance().format(from)));
		}
		if (to != null) {
			query.addParameter(new Parameter("add_date", "<=", DateUtil.getDateFormatInstance().format(to)));
		}
		return query;
	}
	public void clear()
	{
		this.questionnaireSeq = 0;
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

	public int getQuestionnaireSeq() {
		return questionnaireSeq;
	}

	public void setQuestionnaireSeq(int questionnaireSeq) {
		this.questionnaireSeq = questionnaireSeq;
	}
}
