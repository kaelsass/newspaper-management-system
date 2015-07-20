package npp.condition;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MyRecordCondition {
	private String employeeID;
	private Date date;

	// You must declared default constructor for Framework.
	public MyRecordCondition() {
		super();
		this.employeeID="";
		this.date = null;
	}

	public MyRecordCondition(String employeeName, Date date) {
		this.employeeID = employeeName;
		this.date = date;
	}

	public DynamicQuery genQuery() {
		DynamicQuery query = new DynamicQuery();
		SimpleDateFormat begin = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat end = new SimpleDateFormat("yyyy-MM-dd 23:59:59");

		if (employeeID != null && !employeeID.equals("")) {
			query.addParameter(new Parameter("employeeID", "=", employeeID));
		}
		if (date != null) {
			query.addParameter(new Parameter("intime", ">=", begin.format(date)));
			query.addParameter(new Parameter("intime", "<=", end.format(date)));
		}
		else
		{
			query.addParameter(new Parameter("intime", "=", ""));
		}
		return query;
	}


	public void clear()
	{
		this.date = null;
		this.employeeID = "";
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

}
