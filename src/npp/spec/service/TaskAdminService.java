package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.dto.TaskAdminDto;

public interface TaskAdminService {

	List<TaskAdminDto> findByTaskSeq(int taskSeq) throws IOException;

	List<TaskAdminDto> findByEmployeeID(String id) throws IOException;

	void delete(int seq) throws IOException;

	void add(TaskAdminDto dto) throws IOException;

}
