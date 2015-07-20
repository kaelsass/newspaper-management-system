package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.QuestionDto;

public interface NquestionDao {

	List<QuestionDto> getAllList(Transaction transaction, DynamicQuery query) throws IOException;

	QuestionDto findBySeq(Transaction transaction, int seq) throws IOException;

	void add(Transaction transaction, QuestionDto dto) throws IOException;

	void update(Transaction transaction, QuestionDto dto) throws IOException;

	void delete(Transaction transaction, int seq) throws IOException;

}
