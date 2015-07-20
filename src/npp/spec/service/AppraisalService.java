package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.dto.AppraisalDto;

public interface AppraisalService {

	List<AppraisalDto> getAllList() throws IOException;

	AppraisalDto findBySeq(int seq) throws IOException;

	void add(AppraisalDto dto) throws IOException;

	void update(AppraisalDto dto) throws IOException;

	void delete(int seq) throws IOException;

	int getNewInsertedSeq() throws IOException;

}
