package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.IssueDto;


public interface IssueService {

	public IssueDto findBySeq(int seq) throws IOException;

	public List<IssueDto> getAllList(DynamicQuery query) throws IOException;

	public void add(IssueDto dto) throws IOException;

	public void update(IssueDto dto) throws IOException;

	public void delete(int seq) throws IOException;

	public IssueDto getNextIssue(int seq) throws IOException;

}
