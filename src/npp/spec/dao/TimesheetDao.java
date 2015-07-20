package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.TimesheetDto;

public interface TimesheetDao {

	void delete(Transaction transaction, int seq) throws IOException;

	void update(Transaction transaction, TimesheetDto dto) throws IOException;

	void add(Transaction transaction, TimesheetDto dto) throws IOException;

	List<TimesheetDto> getAllList(Transaction transaction, DynamicQuery query)
			throws IOException;

	TimesheetDto findBySeq(Transaction transaction, int seq) throws IOException;

	int getNewInsertedSeq(Transaction transaction)throws IOException;

}
