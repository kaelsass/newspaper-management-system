package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.EducationDto;

public interface EducationService {

	EducationDto findByName(String name) throws IOException;

	void delete(int seq) throws IOException;

	void update(EducationDto dto) throws IOException;

	void add(EducationDto dto) throws IOException;

	List<EducationDto> getAllList(DynamicQuery query) throws IOException;

	EducationDto findBySeq(int seq) throws IOException;

}
