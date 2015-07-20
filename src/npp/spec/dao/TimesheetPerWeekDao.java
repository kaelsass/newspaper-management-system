package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.dto.TimesheetPerWeekDto;

public interface TimesheetPerWeekDao {

	void delete(Transaction transaction, int seq) throws IOException;

	void update(Transaction transaction, TimesheetPerWeekDto dto)
			throws IOException;

	void add(Transaction transaction, TimesheetPerWeekDto dto)
			throws IOException;

	List<TimesheetPerWeekDto> findByTimesheetSeq(Transaction transaction,
			int timesheetSeq) throws IOException;

}
