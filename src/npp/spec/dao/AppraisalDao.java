package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.dto.AppraisalDto;

public interface AppraisalDao {

	void delete(Transaction transaction, int seq) throws IOException;

	void update(Transaction transaction, AppraisalDto dto) throws IOException;

	void add(Transaction transaction, AppraisalDto dto) throws IOException;

	List<AppraisalDto> getAllList(Transaction transaction) throws IOException;

	AppraisalDto findBySeq(Transaction transaction, int seq) throws IOException;

	int getNewInsertedSeq(Transaction transaction) throws IOException;

}
