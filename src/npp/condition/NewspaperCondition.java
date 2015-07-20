package npp.condition;

public class NewspaperCondition {
	private int[] newspaperSeqs;
	private String issn;
	private int[] ptypes;

	// You must declared default constructor for Framework.
	public NewspaperCondition() {
		clear();
	}

	public DynamicQuery genQuery() {
		DynamicQuery query = new DynamicQuery();
		if(issn != null && !issn.equals(""))
		{
			query.addParameter(new Parameter("lower(issn)", "like", "%" + issn.toLowerCase() + "%"));
		}
		if(newspaperSeqs != null && newspaperSeqs.length > 0)
		{
			for(int seq : newspaperSeqs)
			{
				Parameter para = new Parameter("seq", "=", seq);
				para.setType(Parameter.OR);
				query.addParameter(para);
			}
		}
		if(ptypes != null && ptypes.length > 0)
		{
			for(int type : ptypes)
			{
				Parameter para = new Parameter("ptype", "=", type);
				para.setType(Parameter.OR);
				query.addParameter(para);
			}
		}
		return query;
	}

	public void clear() {
		this.newspaperSeqs = null;
		this.issn = "";
		this.ptypes = null;
	}

	public String getIssn() {
		return issn;
	}

	public void setIssn(String issn) {
		this.issn = issn;
	}

	public int[] getNewspaperSeqs() {
		return newspaperSeqs;
	}

	public void setNewspaperSeqs(int[] newspaperSeqs) {
		this.newspaperSeqs = newspaperSeqs;
	}

	public int[] getPtypes() {
		return ptypes;
	}

	public void setPtypes(int[] ptypes) {
		this.ptypes = ptypes;
	}
}
