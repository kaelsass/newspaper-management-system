package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.dto.QqDto;

public interface QuestionnaireQuestionService {

	List<QqDto> getAllList(int questionnaireSeq) throws IOException;

	void add(QqDto dto) throws IOException;

	void delete(int questionnaireSeq) throws IOException;

}
