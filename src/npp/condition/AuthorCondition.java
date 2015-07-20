package npp.condition;



public class AuthorCondition {
	private String name;
	private String workUnit;
	private String address;

	// You must declared default constructor for Framework.
	public AuthorCondition() {
		clear();
	}

	public DynamicQuery genQuery() {
		DynamicQuery query = new DynamicQuery();
		if (name != null && !name.equals("")) {
			query.addParameter(new Parameter("lower(name)", "like", "%" + name.toLowerCase() + "%"));
		}
		if (workUnit != null && !workUnit.equals("")) {
			query.addParameter(new Parameter("lower(workunit)", "like", "%" + workUnit.toLowerCase() + "%"));
		}
		if (address != null && !address.equals("")) {
			query.addParameter(new Parameter("lower(address)", "like", "%" + address.toLowerCase() + "%"));
		}
		return query;
	}
	public void clear()
	{
		this.name = "";
		this.address = "";
		this.workUnit = "";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWorkUnit() {
		return workUnit;
	}

	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
