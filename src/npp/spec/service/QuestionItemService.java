package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.dto.QuestionItemDto;

public interface QuestionItemService {

	List<QuestionItemDto> getAllList(int questionSeq) throws IOException;

	void add(QuestionItemDto dto) throws IOException;

	void delete(int questionSeq) throws IOException;

}
