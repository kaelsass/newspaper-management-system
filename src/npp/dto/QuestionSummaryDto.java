package npp.dto;

import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.chart.PieChartModel;


public class QuestionSummaryDto {
	private QuestionDto question;
	private List<RecordSummaryDto> records;
	private PieChartModel proportionModel;

	//You must declared default constructor for Framework.
	public QuestionSummaryDto(){
		init(new QuestionDto(), new ArrayList<RecordSummaryDto>());
	}


	public QuestionSummaryDto(QuestionDto question, List<RecordSummaryDto> records){
		init(question, records);
	}

	private void init(QuestionDto question, List<RecordSummaryDto> records){
		this.question = question;
		this.records = records;
		proportionModel = null;
	}


	public QuestionDto getQuestion() {
		return question;
	}


	public void setQuestion(QuestionDto question) {
		this.question = question;
	}


	public List<RecordSummaryDto> getRecords() {
		return records;
	}


	public void setRecords(List<RecordSummaryDto> records) {
		this.records = records;
	}
	public int getCount(String item)
	{
		int total = 0;
		for(RecordSummaryDto record : records)
		{
			if(record.getItems().contains(item))
				total ++;
		}
		return total;
	}
	public PieChartModel getProportionModel() {
		initProportionModel();
		return proportionModel;
	}

	private void initProportionModel() {
		proportionModel = new PieChartModel();
		for (String item : question.getItems()) {
			proportionModel.set(item,
					getCount(item));
		}
	}
	public int getProportion(String item)
	{
		return (int)((double)getCount(item)/getTotalCount() * 100);
	}
	public int getTotalCount()
	{
		int total = 0;
		for (String item : question.getItems()) {
			total += getCount(item);
		}
		return total;
	}
}
