package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.dto.CompetencyDto;

public interface CompetencyDao {

	CompetencyDto findByName(Transaction transaction, String name)
			throws IOException;

	void delete(Transaction transaction, int seq) throws IOException;

	void update(Transaction transaction, CompetencyDto dto) throws IOException;

	void add(Transaction transaction, CompetencyDto dto) throws IOException;

	CompetencyDto findBySeq(Transaction transaction, int seq)
			throws IOException;

	List<CompetencyDto> getAllList(Transaction transaction) throws IOException;

}
