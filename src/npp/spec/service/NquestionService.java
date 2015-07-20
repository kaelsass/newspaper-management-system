package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.QuestionDto;

public interface NquestionService {

	QuestionDto findBySeq(int seq) throws IOException;

	List<QuestionDto> getAllList(DynamicQuery query) throws IOException;

	void add(QuestionDto dto) throws IOException;

	void update(QuestionDto dto) throws IOException;

	void delete(int seq) throws IOException;

}
