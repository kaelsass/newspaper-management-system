package npp.condition;

import java.text.SimpleDateFormat;
import java.util.Date;




public class IssueCondition {
	private int[] newspaperSeqs;
	private int quantity;
	private Date from;
	private Date to;

	// You must declared default constructor for Framework.
	public IssueCondition() {
		clear();
	}

	public DynamicQuery genQuery() {
		DynamicQuery query = new DynamicQuery();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		if(quantity >= 0)
		{
			query.addParameter(new Parameter("quantity", ">=", quantity));
		}
		if(from != null)
		{
			query.addParameter(new Parameter("time", ">=", df.format(from)));
		}
		if(to != null)
		{
			query.addParameter(new Parameter("time", "<=", df.format(to)));
		}
		if(newspaperSeqs != null && newspaperSeqs.length > 0)
		{
			for(int seq : newspaperSeqs)
			{
				Parameter para = new Parameter("newspaper_seq", "=", seq);
				para.setType(Parameter.OR);
				query.addParameter(para);
			}
		}

//		for(NewspaperDto dto : newspapers)
//		{
//			Parameter para = new Parameter("newspaper_seq", "=", dto.getSeq());
//			para.setType(Parameter.OR);
//			query.addParameter(para);
//		}
//		for(Integer seq: statusSeqs)
//		{
//			Parameter para = new Parameter("status_seq", "=", seq);
//			para.setType(Parameter.OR);
//			query.addParameter(para);
//		}
		return query;
	}
	public void clear()
	{
		this.newspaperSeqs = null;
		this.quantity = 0;
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int[] getNewspaperSeqs() {
		return newspaperSeqs;
	}

	public void setNewspaperSeqs(int[] newspaperSeqs) {
		this.newspaperSeqs = newspaperSeqs;
	}
}
