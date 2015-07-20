package npp.dto;


public class ArDto {
	private int appraisalSeq;
	private WorkInfoDto employee;
	private EvalGroupDto evalGroup;

	//You must declared default constructor for Framework.
	public ArDto(){
		init( 0, new WorkInfoDto(), new EvalGroupDto());
	}


	public ArDto(int appraisalSeq, WorkInfoDto employee, EvalGroupDto evalGroupDto){
		init(appraisalSeq, employee, evalGroupDto);
	}

	public ArDto(ArDto dto) {
		init(dto.getAppraisalSeq(), dto.getEmployee(), dto.getEvalGroup());
	}
	private void init(int appraisalSeq, WorkInfoDto employee, EvalGroupDto evalGroupDto){
		this.appraisalSeq = appraisalSeq;
		this.employee = employee;
		this.evalGroup = evalGroupDto;
	}

	public int getAppraisalSeq() {
		return appraisalSeq;
	}


	public void setAppraisalSeq(int appraisalSeq) {
		this.appraisalSeq = appraisalSeq;
	}


	public WorkInfoDto getEmployee() {
		return employee;
	}


	public void setEmployee(WorkInfoDto employee) {
		this.employee = employee;
	}


	public EvalGroupDto getEvalGroup() {
		return evalGroup;
	}


	public void setEvalGroup(EvalGroupDto evalGroup) {
		this.evalGroup = evalGroup;
	}
}
