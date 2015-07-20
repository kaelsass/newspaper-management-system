package npp.condition;

import java.util.ArrayList;
import java.util.List;



public class TaskCondition {
	private String name;
	private List<String> employeeIDs;
	private List<Integer> taskSeqs;

	// You must declared default constructor for Framework.
	public TaskCondition() {
		clear();
	}

	public DynamicQuery genQuery() {
		DynamicQuery query = new DynamicQuery();
		if(name != null && !name.equals(""))
		{
			query.addParameter(new Parameter("name", "like", "%" + name + "%"));
		}
		for(Integer taskSeq : taskSeqs)
		{
			Parameter para = new Parameter("task_seq", "=", taskSeq);
			para.setType(Parameter.OR);
			query.addParameter(para);
		}
		return query;
	}
	public void clear()
	{
		this.name = "";
		this.employeeIDs = new ArrayList<String>();
		this.taskSeqs = new ArrayList<Integer>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Integer> getTaskSeqs() {
		return taskSeqs;
	}

	public void setTaskSeqs(List<Integer> taskSeqs) {
		this.taskSeqs = taskSeqs;
	}

	public List<String> getEmployeeIDs() {
		return employeeIDs;
	}

	public void setEmployeeIDs(List<String> employeeIDs) {
		this.employeeIDs = employeeIDs;
	}

}
