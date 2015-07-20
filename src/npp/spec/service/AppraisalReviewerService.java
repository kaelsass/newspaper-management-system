package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.dto.ArDto;

public interface AppraisalReviewerService {

	List<ArDto> getAllList(int appraisalSeq) throws IOException;

	List<ArDto> findByEmployeeID(String id) throws IOException;

	void add(ArDto dto) throws IOException;

	void delete(int appraisalSeq) throws IOException;

}
