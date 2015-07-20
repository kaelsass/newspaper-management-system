package npp.dto;

import java.util.ArrayList;
import java.util.List;

public class NewspaperIssueDto {
	private NewspaperDto newspaper;
	private List<IssueSectionDto> issueSectionList;

	public NewspaperIssueDto(){
		super();
		init(new NewspaperDto(), new ArrayList<IssueSectionDto>());
	}

	private void init(NewspaperDto newspaper,
			ArrayList<IssueSectionDto> list) {
		this.newspaper = newspaper;
		this.issueSectionList = list;

	}
	public NewspaperDto getNewspaper() {
		return newspaper;
	}

	public void setNewspaper(NewspaperDto newspaper) {
		this.newspaper = newspaper;
	}

	public List<IssueSectionDto> getIssueSectionList() {
		return issueSectionList;
	}

	public void setIssueSectionList(List<IssueSectionDto> issueSectionList) {
		this.issueSectionList = issueSectionList;
	}
}
