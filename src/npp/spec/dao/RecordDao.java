package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.RecordDto;

public interface RecordDao {

	void delete(Transaction transaction, int seq) throws IOException;

	void add(Transaction transaction, RecordDto dto) throws IOException;

	List<RecordDto> getAllList(Transaction transaction, DynamicQuery query)
			throws IOException;

}
