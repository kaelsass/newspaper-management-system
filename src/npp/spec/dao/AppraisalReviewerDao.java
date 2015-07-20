package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.dto.ArDto;

public interface AppraisalReviewerDao {

	void delete(Transaction transaction, int appraisalSeq) throws IOException;

	void add(Transaction transaction, ArDto dto) throws IOException;

	List<ArDto> getAllList(Transaction transaction, int appraisalSeq)
			throws IOException;

	List<ArDto> findByEmployeeID(Transaction transaction, String id)throws IOException;

}
