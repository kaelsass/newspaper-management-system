package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.dto.QqDto;

public interface QuestionnaireQuestionDao {

	void delete(Transaction transaction, int questionnaire_seq)
			throws IOException;

	void add(Transaction transaction, QqDto dto) throws IOException;

	List<QqDto> getAllList(Transaction transaction, int qustionnaireSeq)
			throws IOException;

}
