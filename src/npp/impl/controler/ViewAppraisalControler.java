package npp.impl.controler;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import npp.dto.AcEmployeeDto;
import npp.dto.AcLog;
import npp.dto.AgEmployeeDto;
import npp.dto.AgLog;
import npp.dto.AppraisalDto;
import npp.dto.AppraisalRecord;
import npp.dto.AqEmployeeDto;
import npp.dto.AqLog;
import npp.dto.WorkInfoDto;
import npp.spec.service.AppraisalRecordService;

@Named
@SessionScoped
public class ViewAppraisalControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 4439364197284485516L;

	private AppraisalDto appraisal;
	private WorkInfoDto employee;
	private AppraisalRecord record;

	private List<AppraisalRecord> records;

	private List<AcEmployeeDto> acEmployees;
	private List<AgEmployeeDto> agEmployees;
	private List<AqEmployeeDto> aqEmployees;

	@Inject
	private AppraisalRecordService appraisalRecordService;


	public void startEdit(ActionEvent e) {
		appraisal = (AppraisalDto) e.getComponent().getAttributes()
				.get("appraisal");
	}


	public AppraisalDto getAppraisal() {
		return appraisal;
	}


	public void setAppraisal(AppraisalDto appraisal) {
		this.appraisal = appraisal;
	}
	public double getRate()
	{
		return 0;
	}


	public List<AppraisalRecord> getRecords() {
		if(records == null)
		{
			try {
				records = appraisalRecordService.getAllList(appraisal.getSeq());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return records;
	}


	public void setRecords(List<AppraisalRecord> records) {
		this.records = records;
	}


	public List<AcEmployeeDto> getAcEmployees() {
		if(acEmployees == null)
		{
			acEmployees = new ArrayList<AcEmployeeDto>();
			getRecords();
			if(records.size() == 0)
				return acEmployees;
			List<AcLog> list = records.get(0).getAcLogs();
			for(int i = 0; i < list.size(); i++)
			{
				AcEmployeeDto ace = new AcEmployeeDto();
				ace.setCompetency(list.get(i).getAc().getCompetency());
				for(int j = 0; j < records.size(); j++)
				{
					ace.getLogs().add(records.get(j).getAcLogs().get(i));
				}
				acEmployees.add(ace);
			}
		}
		return acEmployees;
	}

	public List<AgEmployeeDto> getAgEmployees() {
		if(agEmployees == null)
		{
			agEmployees = new ArrayList<AgEmployeeDto>();
			getRecords();
			if(records.size() == 0)
				return agEmployees;
			List<AgLog> list = records.get(0).getAgLogs();
			for(int i = 0; i < list.size(); i++)
			{
				AgEmployeeDto ace = new AgEmployeeDto();
				ace.setGoal(list.get(i).getAg().getGoal());
				for(int j = 0; j < records.size(); j++)
				{
					ace.getLogs().add(records.get(j).getAgLogs().get(i));
				}
				agEmployees.add(ace);
			}
		}
		return agEmployees;
	}

	public List<AqEmployeeDto> getAqEmployees() {
		if(aqEmployees == null)
		{
			aqEmployees = new ArrayList<AqEmployeeDto>();
			getRecords();
			if(records.size() == 0)
				return aqEmployees;
			List<AqLog> list = records.get(0).getAqLogs();
			for(int i = 0; i < list.size(); i++)
			{
				AqEmployeeDto ace = new AqEmployeeDto();
				ace.setQuestion(list.get(i).getAq().getQuestion());
				for(int j = 0; j < records.size(); j++)
				{
					ace.getLogs().add(records.get(j).getAqLogs().get(i));
				}
				aqEmployees.add(ace);
			}
		}
		return aqEmployees;
	}

	public int getRecordsSize()
	{
		return getRecords().size();
	}


	public WorkInfoDto getEmployee() {
		if(employee == null)
		{
			List<WorkInfoDto> list = appraisal.getEvaluators();
			if(list.size() > 0)
				employee = list.get(0);
		}
		return employee == null ? new WorkInfoDto() : employee;
	}


	public void setEmployee(WorkInfoDto employee) {
		this.employee = employee;
	}


	public AppraisalRecord getRecord() {
		if(record == null)
		{
			for(AppraisalRecord cur : getRecords())
			{
				if(cur.getEmployee().getId().equals(getEmployee().getId()))
				{
					record = cur;
					break;
				}
			}
		}
		return record;
	}


	public void setRecord(AppraisalRecord record) {
		this.record = record;
	}
}
