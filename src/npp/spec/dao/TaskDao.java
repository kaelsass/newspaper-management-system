package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.TaskDto;

public interface TaskDao {

	public void delete(Transaction transaction, int seq) throws IOException;

	public void update(Transaction transaction, TaskDto dto) throws IOException;

	public void add(Transaction transaction, TaskDto dto) throws IOException;

	public TaskDto findBySeq(Transaction transaction, int seq) throws IOException;

	List<TaskDto> getAllList(Transaction transaction, DynamicQuery query)
			throws IOException;

	public int getNewInsertedSeq(Transaction transaction)throws IOException;

}
