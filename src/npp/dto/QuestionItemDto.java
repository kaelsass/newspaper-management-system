package npp.dto;


public class QuestionItemDto {
	private int questionSeq;
	private String item;

	//You must declared default constructor for Framework.
	public QuestionItemDto(){
		init(0, "");
	}


	public QuestionItemDto(int questionSeq, String item){
		init( questionSeq, item);
	}

	public QuestionItemDto(QuestionItemDto dto) {
		init( dto.getQuestionSeq(), dto.getItem());
	}
	private void init(int questionSeq, String item){
		this.questionSeq = questionSeq;
		this.item = item;
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

}
