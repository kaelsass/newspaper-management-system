package npp.dto;

import java.util.ArrayList;
import java.util.List;


public class AqEmployeeDto {
	private StatusDto question;
	private List<AqLog> logs;

	//You must declared default constructor for Framework.
	public AqEmployeeDto(){
		init(new StatusDto(), new ArrayList<AqLog>());
	}


	public AqEmployeeDto(StatusDto dto, List<AqLog> logs){
		init(dto, logs);
	}

	public AqEmployeeDto(AqEmployeeDto dto) {
		init(dto.getQuestion(), dto.getLogs());
	}
	private void init(StatusDto dto, List<AqLog> logs){
		this.question = dto;
		this.logs = logs;
	}


	public List<AqLog> getLogs() {
		return logs;
	}


	public void setLogs(List<AqLog> logs) {
		this.logs = logs;
	}


	public StatusDto getQuestion() {
		return question;
	}


	public void setQuestion(StatusDto question) {
		this.question = question;
	}

}
