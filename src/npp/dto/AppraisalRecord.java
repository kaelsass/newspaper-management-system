package npp.dto;

import java.util.ArrayList;
import java.util.List;


public class AppraisalRecord {
	private WorkInfoDto employee;
	private List<AcLog> acLogs;
	private List<AgLog> agLogs;
	private List<AqLog> aqLogs;


	//You must declared default constructor for Framework.
	public AppraisalRecord(){
		init(new WorkInfoDto(), new ArrayList<AcLog>(), new ArrayList<AgLog>(), new ArrayList<AqLog>());
	}


	public AppraisalRecord(WorkInfoDto employee, List<AcLog> acLogs, List<AgLog> agLogs, List<AqLog> aqLogs){
		init(employee, acLogs, agLogs, aqLogs);
	}

	public AppraisalRecord(AppraisalRecord dto) {
		init(dto.getEmployee(), dto.getAcLogs(), dto.getAgLogs(), dto.getAqLogs());
	}
	private void init(WorkInfoDto employee, List<AcLog> acLogs, List<AgLog> agLogs, List<AqLog> aqLogs){
		this.employee = employee;
		this.acLogs = acLogs;
		this.agLogs = agLogs;
		this.aqLogs = aqLogs;
	}


	public WorkInfoDto getEmployee() {
		return employee;
	}


	public void setEmployee(WorkInfoDto employee) {
		this.employee = employee;
	}


	public List<AcLog> getAcLogs() {
		return acLogs;
	}


	public void setAcLogs(List<AcLog> acLogs) {
		this.acLogs = acLogs;
	}


	public List<AgLog> getAgLogs() {
		return agLogs;
	}


	public void setAgLogs(List<AgLog> agLogs) {
		this.agLogs = agLogs;
	}


	public List<AqLog> getAqLogs() {
		return aqLogs;
	}


	public void setAqLogs(List<AqLog> aqLogs) {
		this.aqLogs = aqLogs;
	}

	public double getAcScore() {
		double sum = 0;
		for(AcLog log : acLogs)
		{
			sum += log.getScore();
		}
		return sum;
	}


	public double getAgScore() {
		double sum = 0;
		for(AgLog log : agLogs)
		{
			sum += log.getScore();
		}
		return sum;
	}


	public double getAqScore() {
		double sum = 0;
		for(AqLog log : aqLogs)
		{
			sum += log.getScore();
		}
		return sum;
	}
	public String getFormatAcScore()
	{
		return String.format("%.1f", getAcScore());
	}
	public String getFormatAgScore()
	{
		return String.format("%.1f", getAgScore());
	}

	public String getFormatAqScore()
	{
		return String.format("%.1f", getAqScore());
	}


}
