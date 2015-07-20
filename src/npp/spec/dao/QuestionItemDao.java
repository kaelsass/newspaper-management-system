package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.dto.QuestionItemDto;

public interface QuestionItemDao {

	void delete(Transaction transaction, int questionSeq) throws IOException;

	void add(Transaction transaction, QuestionItemDto dto) throws IOException;

	List<QuestionItemDto> getAllList(Transaction transaction, int questionSeq)
			throws IOException;

}
