package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.dto.JobTitleDto;


public interface JobTitleDao {
	public List<JobTitleDto> getAllList(Transaction transaction) throws IOException;
	public JobTitleDto findBySeq(Transaction transaction, int seq) throws IOException;
	public void add(Transaction transaction, JobTitleDto dto) throws IOException;
	public void update(Transaction transaction, JobTitleDto dto) throws IOException;
	public void delete(Transaction transaction, int seq) throws IOException;
	public JobTitleDto findByName(Transaction transaction, String name) throws IOException;
}
