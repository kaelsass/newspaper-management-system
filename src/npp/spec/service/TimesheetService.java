package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.TimesheetDto;

public interface TimesheetService {

	TimesheetDto findBySeq(int seq) throws IOException;

	void add(TimesheetDto dto) throws IOException;

	void update(TimesheetDto dto) throws IOException;

	void delete(int seq) throws IOException;

	List<TimesheetDto> getAllList(DynamicQuery query) throws IOException;

	int getNewInsertedSeq() throws IOException;

}
