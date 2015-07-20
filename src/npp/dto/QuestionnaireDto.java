package npp.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import npp.faces.QuestionListModel;
import npp.utils.DateUtil;


public class QuestionnaireDto {

	private int seq;
	private String name;
	private String description;
	private PromotionDto promotion;
	private Date addDate;
	private Date modDate;
	private boolean published;

	private List<QuestionDto> questions;
	private QuestionListModel questionListModel;

	private String url;


	//You must declared default constructor for Framework.
	public QuestionnaireDto(){
		init(0, "", "", new PromotionDto(), new Date(), new Date(), false, new ArrayList<QuestionDto>());
	}


	public QuestionnaireDto(int seq, String name, String description, PromotionDto promotion, Date addDate, Date modDate,
			boolean published, List<QuestionDto> questions){
		init(seq, name, description, promotion, addDate, modDate, published, questions);
	}

	public QuestionnaireDto(QuestionnaireDto dto) {
		init(dto.getSeq(), dto.getName(), dto.getDescription(), dto.getPromotion(), dto.getAddDate(), dto.getModDate(), dto.isPublished(),
				dto.getQuestions());
	}
	private void init(int seq, String name, String description, PromotionDto promotion, Date addDate, Date modDate,
			boolean published, List<QuestionDto> questions){
		this.seq = seq;
		this.name = name;
		this.description = description;
		this.promotion = promotion;
		this.addDate = addDate;
		this.modDate = modDate;
		this.published = published;
		this.questions = questions;
		this.questionListModel = null;
	}


	public int getSeq() {
		return seq;
	}


	public void setSeq(int seq) {
		this.seq = seq;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public PromotionDto getPromotion() {
		return promotion;
	}


	public void setPromotion(PromotionDto promotion) {
		this.promotion = promotion;
	}


	public Date getAddDate() {
		return addDate;
	}


	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}


	public Date getModDate() {
		return modDate;
	}


	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}


	public boolean isPublished() {
		return published;
	}


	public void setPublished(boolean published) {
		this.published = published;
	}


	public List<QuestionDto> getQuestions() {
		return questions;
	}


	public void setQuestions(List<QuestionDto> questions) {
		System.out.println("setQuestions");
		System.out.println("questions: " + questions);

		this.questions = questions;
	}


	public String getFormatAddDate() {
		return DateUtil.getDateFormatInstance().format(addDate);
	}
	public String getFormatModDate() {
		return DateUtil.getDateFormatInstance().format(modDate);
	}


	public QuestionListModel getQuestionListModel() {
		//System.out.println("here!!!");
		if(questionListModel == null)
		{
			questionListModel = new QuestionListModel(questions);
		}
		//System.out.println("questionlistmodel : " + questionListModel);
		return questionListModel;
	}


	public void setQuestionListModel(QuestionListModel questionListModel) {
		this.questionListModel = questionListModel;
	}


	public String getUrl() {
		return "http://172.26.142.74:9090/PrimeSecond/faces/addQRecord.jsf?seq=" + this.seq;
	}


	public void setUrl(String url) {
		this.url = url;
	}
}
