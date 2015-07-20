package npp.dto;



public class QuestionLogDto {
	private int recordSeq;
	private int questionSeq;
	private String item;
	private String answer;

	//You must declared default constructor for Framework.
	public QuestionLogDto(){
		init(0, 0, "", "");
	}


	public QuestionLogDto(int recordSeq, int questionSeq, String item, String answer){
		init(recordSeq, questionSeq, item, answer);
	}

	public QuestionLogDto(QuestionLogDto dto) {
		init(dto.getRecordSeq(), dto.getQuestionSeq(), dto.getItem(), dto.getAnswer());
	}
	private void init(int recordSeq, int questionSeq, String item, String answer){
		this.recordSeq = recordSeq;
		this.questionSeq = questionSeq;
		this.item = (item == null ? "" : item);
		this.answer = (answer == null ? "" : answer);
	}

	public int getQuestionSeq() {
		return questionSeq;
	}


	public void setQuestionSeq(int questionSeq) {
		this.questionSeq = questionSeq;
	}


	public String getItem() {
		return item;
	}


	public void setItem(String item) {
		this.item = item;
	}


	public String getAnswer() {
		return answer;
	}


	public void setAnswer(String answer) {
		this.answer = answer;
	}


	public int getRecordSeq() {
		return recordSeq;
	}


	public void setRecordSeq(int recordSeq) {
		this.recordSeq = recordSeq;
	}
}
