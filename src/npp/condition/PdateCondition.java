package npp.condition;



public class PdateCondition {
	private int type;

	// You must declared default constructor for Framework.
	public PdateCondition() {
		clear();
	}

	public DynamicQuery genQuery() {
		DynamicQuery query = new DynamicQuery();
		if(type > 0)
		{
			query.addParameter(new Parameter("ptype", "=", type));
		}
		return query;
	}
	public void clear()
	{
		this.type = 0;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
