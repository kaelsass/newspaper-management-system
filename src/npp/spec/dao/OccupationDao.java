package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.OccupationDto;

public interface OccupationDao {

	List<OccupationDto> getAllList(Transaction transaction, DynamicQuery query) throws IOException;

	OccupationDto findBySeq(Transaction transaction, int seq)
			throws IOException;

	void add(Transaction transaction, OccupationDto dto) throws IOException;

	void update(Transaction transaction, OccupationDto dto) throws IOException;

	void delete(Transaction transaction, int seq) throws IOException;

	OccupationDto findByName(Transaction transaction, String name)
			throws IOException;

}
