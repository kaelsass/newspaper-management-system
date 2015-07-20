package npp.dto;

import java.util.ArrayList;
import java.util.List;

public class TaskDto {
	private int seq;
	private String name;
	private String description;
	private List<WorkInfoDto> admins;
	private List<ActivityDto> activities;

	//You must declared default constructor for Framework.
	public TaskDto(){
		init(0, "", "", new ArrayList<WorkInfoDto>(), new ArrayList<ActivityDto>());
	}


	public TaskDto(int seq, String name, String description, List<WorkInfoDto> admins, List<ActivityDto> activities){
		init(seq, name, description, admins, activities);
	}

	public TaskDto(TaskDto dto) {
		init(dto.getSeq(),dto.getName(), dto.getDescription(), dto.getAdmins(), dto.getActivities());
	}
	private void init(int seq, String name, String description, List<WorkInfoDto> admins, List<ActivityDto> activities){
		this.seq = seq;
		this.name = name;
		this.description = description;
		this.admins = admins;
		this.activities = activities;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}


	public List<WorkInfoDto> getAdmins() {
		return admins;
	}


	public void setAdmins(List<WorkInfoDto> admins) {
		this.admins = admins;
	}


	public List<ActivityDto> getActivities() {
		return activities;
	}


	public void setActivities(List<ActivityDto> activities) {
		this.activities = activities;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
	public String getFormatAdmins()
	{
		StringBuffer sb = new StringBuffer();
		for(WorkInfoDto dto : admins)
		{
			sb.append(dto.getName() + ", ");
		}
		if(sb.length() > 0)
		{
			return sb.substring(0, sb.length()-2);
		}
		else
			return sb.toString();
	}
	
}
