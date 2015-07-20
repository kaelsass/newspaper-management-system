package npp.dto;


public class AgDto {
	private int appraisalSeq;
	private GoalDto goal;
	private int ratio;
	private boolean selected;

	//You must declared default constructor for Framework.
	public AgDto(){
		init(0, new GoalDto(), 0, false);
	}


	public AgDto(int appraisalSeq, GoalDto goal, int ratio, boolean selected){
		init(appraisalSeq, goal, ratio, selected);
	}

	public AgDto(AgDto dto) {
		init(dto.getAppraisalSeq(), dto.getGoal(), dto.getRatio(), dto.isSelected());
	}
	private void init(int appraisalSeq, GoalDto goal, int ratio, boolean selected){
		this.appraisalSeq = appraisalSeq;
		this.goal = goal;
		this.ratio = ratio;
		this.selected = selected;
	}

	public int getRatio() {
		return ratio;
	}


	public void setRatio(int ratio) {
		this.ratio = ratio;
	}


	public int getAppraisalSeq() {
		return appraisalSeq;
	}


	public void setAppraisalSeq(int appraisalSeq) {
		this.appraisalSeq = appraisalSeq;
	}


	public GoalDto getGoal() {
		return goal;
	}


	public void setGoal(GoalDto goal) {
		this.goal = goal;
	}


	public boolean isSelected() {
		return selected;
	}


	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
