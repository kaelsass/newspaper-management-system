package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.WorkInfoDto;


public interface WorkInfoDao {
	public List<WorkInfoDto> getAllList(Transaction transaction, DynamicQuery query) throws IOException;
	public WorkInfoDto findByID(Transaction transaction, String id) throws IOException;
	public void update(Transaction transaction, WorkInfoDto employeeDto) throws IOException;
	public void delete(Transaction transaction, String id) throws IOException;
	public WorkInfoDto findByName(Transaction transaction, String name) throws IOException;
	public void add(Transaction transaction, WorkInfoDto employeeDto)
			throws IOException;
}
