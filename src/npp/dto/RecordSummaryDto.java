package npp.dto;

import java.util.ArrayList;
import java.util.List;


public class RecordSummaryDto {
	private List<String> items;
	private String answer;

	//You must declared default constructor for Framework.
	public RecordSummaryDto(){
		init(new ArrayList<String>(), "");
	}


	public RecordSummaryDto(List<String> items, String answer){
		init(items, answer);
	}

	private void init(List<String> items, String answer){
		this.items = items;
		this.answer = answer;
	}


	public List<String> getItems() {
		return items;
	}


	public void setItems(List<String> items) {
		this.items = items;
	}


	public String getAnswer() {
		return answer;
	}


	public void setAnswer(String answer) {
		this.answer = answer;
	}

}
