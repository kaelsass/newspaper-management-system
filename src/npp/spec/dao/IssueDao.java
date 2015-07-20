package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.IssueDto;


public interface IssueDao {

	public IssueDto findBySeq(Transaction transaction, int seq) throws IOException;

	public List<IssueDto> getAllList(Transaction transaction, DynamicQuery query)
			throws IOException;

	public void add(Transaction transaction, IssueDto dto) throws IOException;

	public void update(Transaction transaction, IssueDto dto) throws IOException;

	public void delete(Transaction transaction, int seq) throws IOException;

	public IssueDto getNextIssueByNewspaper(Transaction transaction, int newspaper_seq)
			throws IOException;

}
