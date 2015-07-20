package npp.dto;


public class AqDto {
	private int appraisalSeq;
	private StatusDto question;
	private int ratio;
	private boolean selected;

	//You must declared default constructor for Framework.
	public AqDto(){
		init(0, new StatusDto(), 0, false);
	}


	public AqDto( int appraisalSeq, StatusDto question, int ratio, boolean selected){
		init(appraisalSeq, question, ratio, selected);
	}

	public AqDto(AqDto dto) {
		init( dto.getAppraisalSeq(), dto.getQuestion(), dto.getRatio(), dto.isSelected());
	}
	private void init(int appraisalSeq, StatusDto question, int ratio, boolean selected){
		this.appraisalSeq = appraisalSeq;
		this.question = question;
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


	public StatusDto getQuestion() {
		return question;
	}


	public void setQuestion(StatusDto question) {
		this.question = question;
	}


	public boolean isSelected() {
		return selected;
	}


	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
