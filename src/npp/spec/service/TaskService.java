package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.TaskDto;

public interface TaskService {

	public TaskDto findBySeq(int seq) throws IOException;

	public List<TaskDto> getAllList(DynamicQuery query) throws IOException;

	public void add(TaskDto dto) throws IOException;

	public void update(TaskDto dto) throws IOException;

	public void delete(int seq) throws IOException;

	public int getNewInsertedSeq()throws IOException;

}
