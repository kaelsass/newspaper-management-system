package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.dto.JobTitleDto;


public interface JobTitleService {

	public List<JobTitleDto> getAllList() throws IOException;
	public JobTitleDto findBySeq(int seq) throws IOException;
	public void update(JobTitleDto dto) throws IOException;
	public void delete(int seq) throws IOException;
	public void add(JobTitleDto dto) throws IOException;
	public JobTitleDto findByName(String name) throws IOException;
}
