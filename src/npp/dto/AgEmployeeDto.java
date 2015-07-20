package npp.dto;

import java.util.ArrayList;
import java.util.List;


public class AgEmployeeDto {
	private GoalDto goal;
	private List<AgLog> logs;

	//You must declared default constructor for Framework.
	public AgEmployeeDto(){
		init(new GoalDto(), new ArrayList<AgLog>());
	}


	public AgEmployeeDto(GoalDto dto, List<AgLog> logs){
		init(dto, logs);
	}

	public AgEmployeeDto(AgEmployeeDto dto) {
		init(dto.getGoal(), dto.getLogs());
	}
	private void init(GoalDto dto, List<AgLog> logs){
		this.goal = dto;
		this.logs = logs;
	}


	public List<AgLog> getLogs() {
		return logs;
	}


	public void setLogs(List<AgLog> logs) {
		this.logs = logs;
	}


	public GoalDto getGoal() {
		return goal;
	}


	public void setGoal(GoalDto goal) {
		this.goal = goal;
	}


}
