package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.dto.QuestionDto;
import npp.dto.QuestionLogDto;

public interface QuestionLogDao {

	void delete(Transaction transaction, QuestionLogDto log) throws IOException;

	void add(Transaction transaction, QuestionLogDto log) throws IOException;

	List<QuestionLogDto> getAllList(Transaction transaction,
			int questionnaireSeq, QuestionDto dto) throws IOException;

}
