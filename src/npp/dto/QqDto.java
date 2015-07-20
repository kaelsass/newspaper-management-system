package npp.dto;


public class QqDto {
	private int questionnaireSeq;
	private QuestionDto question;

	//You must declared default constructor for Framework.
	public QqDto(){
		init( 0, new QuestionDto());
	}


	public QqDto(int questionnaireSeq, QuestionDto question){
		init(questionnaireSeq, question);
	}

	public QqDto(QqDto dto) {
		init(dto.getQuestionnaireSeq(), dto.getQuestion());
	}
	private void init(int questionnaireSeq, QuestionDto question){
		this.questionnaireSeq = questionnaireSeq;
		this.question = question;
	}


	public int getQuestionnaireSeq() {
		return questionnaireSeq;
	}


	public void setQuestionnaireSeq(int questionnaireSeq) {
		this.questionnaireSeq = questionnaireSeq;
	}


	public QuestionDto getQuestion() {
		return question;
	}


	public void setQuestion(QuestionDto question) {
		this.question = question;
	}

}
