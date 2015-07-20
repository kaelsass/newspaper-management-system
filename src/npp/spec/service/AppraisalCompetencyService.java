package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.dto.AcDto;

public interface AppraisalCompetencyService {

	void delete(int appraisalSeq) throws IOException;

	void add(AcDto dto) throws IOException;

	List<AcDto> getAllList(int appraisalSeq) throws IOException;

}
