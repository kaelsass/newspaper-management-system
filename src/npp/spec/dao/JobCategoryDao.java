package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.dto.JobCategoryDto;


public interface JobCategoryDao {

	public List<JobCategoryDto> getAllList(Transaction transaction) throws IOException;

	public JobCategoryDto findBySeq(Transaction transaction, int seq)
			throws IOException;

	public void add(Transaction transaction, JobCategoryDto dto) throws IOException;

	public void update(Transaction transaction, JobCategoryDto dto) throws IOException;

	public void delete(Transaction transaction, int seq) throws IOException;

	public JobCategoryDto findByName(Transaction transaction, String name)
			throws IOException;

}
