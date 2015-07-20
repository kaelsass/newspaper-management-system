package npp.dto;

import java.util.ArrayList;
import java.util.List;


public class ManageAppraisalDto {
	private AppraisalDto appraisal;
	private List<AcDto> acDtos;
	private List<AgDto> agDtos;
	private List<AqDto> aqDtos;
	private List<String> employeeIDs;

	//You must declared default constructor for Framework.
	public ManageAppraisalDto(){
		init(new AppraisalDto(), new ArrayList<AcDto>(), new ArrayList<AgDto>(), new ArrayList<AqDto>(), new ArrayList<String>());
	}


	public ManageAppraisalDto(AppraisalDto appraisalDto, List<AcDto> acDtos, List<AgDto> agDtos, List<AqDto> aqDtos, List<String> employeeIDs){
		init(appraisalDto, acDtos, agDtos, aqDtos, employeeIDs);
	}

	public ManageAppraisalDto(ManageAppraisalDto dto) {
		init(dto.getAppraisal(), dto.getAcDtos(), dto.getAgDtos(), dto.getAqDtos(), dto.getEmployeeIDs());
	}
	private void init(AppraisalDto appraisalDto, List<AcDto> acDtos, List<AgDto> agDtos, List<AqDto> aqDtos, List<String> employeeIDs){
		this.appraisal = appraisalDto;
		this.acDtos = acDtos;
		this.agDtos = agDtos;
		this.aqDtos = aqDtos;
		this.employeeIDs = employeeIDs;
	}


	public AppraisalDto getAppraisal() {
		return appraisal;
	}


	public void setAppraisal(AppraisalDto appraisal) {
		this.appraisal = appraisal;
	}


	public List<AcDto> getAcDtos() {
		return acDtos;
	}


	public void setAcDtos(List<AcDto> acDtos) {
		this.acDtos = acDtos;
	}


	public List<AgDto> getAgDtos() {
		return agDtos;
	}


	public void setAgDtos(List<AgDto> agDtos) {
		this.agDtos = agDtos;
	}


	public List<AqDto> getAqDtos() {
		return aqDtos;
	}


	public void setAqDtos(List<AqDto> aqDtos) {
		this.aqDtos = aqDtos;
	}


	public List<String> getEmployeeIDs() {
		return employeeIDs;
	}


	public void setEmployeeIDs(List<String> employeeIDs) {
		this.employeeIDs = employeeIDs;
	}
}
