package npp.dto;

import java.util.ArrayList;
import java.util.List;


public class AcEmployeeDto {
	private CompetencyDto competency;
	private List<AcLog> logs;

	//You must declared default constructor for Framework.
	public AcEmployeeDto(){
		init(new CompetencyDto(), new ArrayList<AcLog>());
	}


	public AcEmployeeDto(CompetencyDto competency, List<AcLog> logs){
		init(competency, logs);
	}

	public AcEmployeeDto(AcEmployeeDto dto) {
		init(dto.getCompetency(), dto.getLogs());
	}
	private void init(CompetencyDto competency, List<AcLog> logs){
		this.competency = competency;
		this.logs = logs;
	}

	public CompetencyDto getCompetency() {
		return competency;
	}


	public void setCompetency(CompetencyDto competency) {
		this.competency = competency;
	}


	public List<AcLog> getLogs() {
		return logs;
	}


	public void setLogs(List<AcLog> logs) {
		this.logs = logs;
	}

}
