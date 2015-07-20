package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.dto.AqDto;

public interface AppraisalQuestionDao {

	void delete(Transaction transaction, int appraisalSeq) throws IOException;

	void add(Transaction transaction, AqDto dto) throws IOException;

	List<AqDto> getAllList(Transaction transaction, int appraisalSeq)
			throws IOException;

}
