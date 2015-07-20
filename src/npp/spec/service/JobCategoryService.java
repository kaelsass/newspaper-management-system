package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.dto.JobCategoryDto;


public interface JobCategoryService {

	public JobCategoryDto findBySeq(int seq) throws IOException;

	public List<JobCategoryDto> getAllList() throws IOException;

	public void add(JobCategoryDto dto) throws IOException;

	public void update(JobCategoryDto dto) throws IOException;

	public void delete(int seq) throws IOException;

	public JobCategoryDto findByName(String name) throws IOException;

}
