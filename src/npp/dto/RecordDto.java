package npp.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import npp.utils.DateUtil;


public class RecordDto {
	private int seq;
	private int questionnaireSeq;
	private Date date;
	private int time;
	private String ip;
	private List<QuestionDto> questions;

	//You must declared default constructor for Framework.
	public RecordDto(){
		init(0, 0, new Date(), 0, "", new ArrayList<QuestionDto>());
	}


	public RecordDto(int seq, int questionnaireSeq, Date date, int time, String ip, List<QuestionDto> questions){
		init(seq, questionnaireSeq, date, time, ip, questions);
	}

	public RecordDto(RecordDto dto) {
		init(dto.getSeq(), dto.getQuestionnaireSeq(), dto.getDate(), dto.getTime(), dto.getIp(), dto.getQuestions());
	}
	private void init(int seq, int questionnaireSeq, Date date, int time, String ip, List<QuestionDto> questions){
		this.seq = seq;
		this.questionnaireSeq = questionnaireSeq;
		this.date = date;
		this.time = time;
		this.ip = ip;
		this.questions = questions;
	}


	public int getQuestionnaireSeq() {
		return questionnaireSeq;
	}


	public void setQuestionnaireSeq(int questionnaireSeq) {
		this.questionnaireSeq = questionnaireSeq;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public int getTime() {
		return time;
	}


	public void setTime(int time) {
		this.time = time;
	}


	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}


	public List<QuestionDto> getQuestions() {
		return questions;
	}


	public void setQuestions(List<QuestionDto> questions) {
		this.questions = questions;
	}


	public String getFormatDate() {
		return DateUtil.getDateWithTimeFormatInstance().format(date);
	}


	public int getSeq() {
		return seq;
	}


	public void setSeq(int seq) {
		this.seq = seq;
	}

}
