package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.EducationDto;

public interface EducationDao {

	List<EducationDto> getAllList(Transaction transaction, DynamicQuery query) throws IOException;

	EducationDto findBySeq(Transaction transaction, int seq) throws IOException;

	void add(Transaction transaction, EducationDto dto) throws IOException;

	void update(Transaction transaction, EducationDto dto) throws IOException;

	void delete(Transaction transaction, int seq) throws IOException;

	EducationDto findByName(Transaction transaction, String name)
			throws IOException;

}
