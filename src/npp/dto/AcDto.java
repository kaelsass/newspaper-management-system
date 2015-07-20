package npp.dto;


public class AcDto {
	private int appraisalSeq;
	private CompetencyDto competency;
	private int ratio;
	private boolean selected;

	//You must declared default constructor for Framework.
	public AcDto(){
		init(0, new CompetencyDto(), 0, false);
	}


	public AcDto(int appraisalSeq, CompetencyDto competency, int ratio, boolean selected){
		init(appraisalSeq, competency, ratio, selected);
	}

	public AcDto(AcDto dto) {
		init(dto.getAppraisalSeq(), dto.getCompetency(), dto.getRatio(), dto.isSelected());
	}
	private void init(int appraisalSeq, CompetencyDto competency, int ratio, boolean selected){
		this.appraisalSeq = appraisalSeq;
		this.competency = competency;
		this.ratio = ratio;
		this.selected = selected;
	}

	public CompetencyDto getCompetency() {
		return competency;
	}


	public void setCompetency(CompetencyDto competency) {
		this.competency = competency;
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


	public boolean isSelected() {
		return selected;
	}


	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
