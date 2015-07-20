package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.WorkInfoDto;


public interface WorkInfoService {
	public List<WorkInfoDto> getAllList(DynamicQuery query)
			throws IOException;
	public WorkInfoDto findByID(String id) throws IOException;
	public void add(WorkInfoDto editWorkInfo) throws IOException;
	public void update(WorkInfoDto employeeDto) throws IOException;
	public void delete(String id) throws IOException;
	public WorkInfoDto findByName(String name)throws IOException;
}
