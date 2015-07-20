package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.dto.TaskAdminDto;

public interface TaskAdminDao {

	public void delete(Transaction transaction, int taskSeq) throws IOException;

	public void add(Transaction transaction, TaskAdminDto dto) throws IOException;

	public List<TaskAdminDto> findByTaskSeq(Transaction transaction, int taskSeq)
			throws IOException;
	public List<TaskAdminDto> findByEmployeeID(Transaction transaction, String id)
			throws IOException;

}
