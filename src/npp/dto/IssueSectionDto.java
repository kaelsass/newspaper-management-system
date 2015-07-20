package npp.dto;

import java.util.ArrayList;
import java.util.List;

public class IssueSectionDto {
	private IssueDto issue;
	private List<SectionDto> sections;

	public IssueSectionDto(){
		super();
		issue = new IssueDto();
		sections = new ArrayList<SectionDto>();
	}

	public IssueSectionDto(IssueDto dto, List<SectionDto> sections) {
		this.issue = dto;
		this.sections = sections;
	}

	public IssueDto getIssue() {
		return issue;
	}

	public void setIssue(IssueDto issue) {
		this.issue = issue;
	}

	public List<SectionDto> getSections() {
		return sections;
	}

	public void setSections(List<SectionDto> sections) {
		this.sections = sections;
	}
	public String getPhotoName()
	{
		if(sections.size() == 0)
			return "default.jpg";
		return sections.get(0).getPhotoName();
	}
}
