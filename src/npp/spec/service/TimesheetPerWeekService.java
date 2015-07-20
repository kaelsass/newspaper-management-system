package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.dto.TimesheetPerWeekDto;

public interface TimesheetPerWeekService {

	List<TimesheetPerWeekDto> findByTimesheetSeq(int seq) throws IOException;

	void add(TimesheetPerWeekDto dto) throws IOException;

	void update(TimesheetPerWeekDto dto) throws IOException;

	void delete(int seq) throws IOException;

}
