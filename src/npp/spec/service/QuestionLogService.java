package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.dto.QuestionDto;
import npp.dto.QuestionLogDto;

public interface QuestionLogService {

	void delete(QuestionLogDto dto) throws IOException;

	void add(QuestionLogDto dto) throws IOException;

	List<QuestionLogDto> getAllList(int questionnaireSeq, QuestionDto question)
			throws IOException;

}
