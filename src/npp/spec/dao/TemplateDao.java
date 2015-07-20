package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.dto.StatusDto;

public interface TemplateDao {

	List<StatusDto> getAllList(Transaction transaction) throws IOException;

	StatusDto findByName(Transaction transaction, String name)
			throws IOException;

	void delete(Transaction transaction, int seq) throws IOException;

	void update(Transaction transaction, StatusDto dto) throws IOException;

	void add(Transaction transaction, StatusDto dto) throws IOException;

	StatusDto findBySeq(Transaction transaction, int seq) throws IOException;

}
