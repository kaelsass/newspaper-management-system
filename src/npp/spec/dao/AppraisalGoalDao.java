package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.dto.AgDto;

public interface AppraisalGoalDao {

	void delete(Transaction transaction, int appraisalSeq) throws IOException;

	void add(Transaction transaction, AgDto dto) throws IOException;

	List<AgDto> getAllList(Transaction transaction, int appraisalSeq)
			throws IOException;

}
