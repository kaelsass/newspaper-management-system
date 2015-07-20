package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.dto.AgDto;

public interface AppraisalGoalService {

	List<AgDto> getAllList(int appraisalSeq) throws IOException;

	void add(AgDto dto) throws IOException;

	void delete(int seq) throws IOException;

}
