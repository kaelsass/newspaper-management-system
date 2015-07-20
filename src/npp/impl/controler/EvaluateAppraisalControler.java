package npp.impl.controler;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import npp.dto.AppraisalDto;
import npp.dto.AppraisalRecord;
import npp.dto.WorkInfoDto;
import npp.spec.service.AppraisalRecordService;

@Named
@SessionScoped
public class EvaluateAppraisalControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 4439364197284485516L;

	private AppraisalDto appraisal;
	private WorkInfoDto employee;
	private List<AppraisalRecord> records;

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



	public int getRecordsSize()
	{
		return getRecords().size();
	}


	public WorkInfoDto getEmployee() {
		if(appraisal != null)
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
}
