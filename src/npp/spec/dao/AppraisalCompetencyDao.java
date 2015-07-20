package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.dto.AcDto;

public interface AppraisalCompetencyDao {

	void delete(Transaction transaction, int appraisalSeq) throws IOException;

	void add(Transaction transaction, AcDto dto) throws IOException;

	List<AcDto> getAllList(Transaction transaction, int appraisalSeq)
			throws IOException;

}
