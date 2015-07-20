package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.QuestionnaireDto;

public interface QuestionnaireDao {

	void delete(Transaction transaction, int seq) throws IOException;

	void update(Transaction transaction, QuestionnaireDto dto)
			throws IOException;

	void add(Transaction transaction, QuestionnaireDto dto) throws IOException;

	QuestionnaireDto findBySeq(Transaction transaction, int seq)
			throws IOException;

	List<QuestionnaireDto> getAllList(Transaction transaction,
			DynamicQuery query) throws IOException;

}
