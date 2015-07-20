package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.RecordDto;

public interface RecordService {

	List<RecordDto> getAllList(DynamicQuery query) throws IOException;

	void add(RecordDto dto) throws IOException;

	void delete(int seq) throws IOException;

}
