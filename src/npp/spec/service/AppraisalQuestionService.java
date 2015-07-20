package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.dto.AqDto;

public interface AppraisalQuestionService {

	List<AqDto> getAllList(int appraisalSeq) throws IOException;

	void add(AqDto dto) throws IOException;

	void delete(int seq) throws IOException;

}
