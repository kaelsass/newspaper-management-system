package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.QuestionnaireDto;

public interface QuestionnaireService {

	QuestionnaireDto findBySeq(int seq) throws IOException;

	List<QuestionnaireDto> getAllList(DynamicQuery query) throws IOException;

	void add(QuestionnaireDto dto) throws IOException;

	void update(QuestionnaireDto dto) throws IOException;

	void delete(int seq) throws IOException;

}
