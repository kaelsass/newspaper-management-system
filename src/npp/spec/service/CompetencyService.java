package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.dto.CompetencyDto;

public interface CompetencyService {

	CompetencyDto findBySeq(int seq) throws IOException;

	List<CompetencyDto> getAllList() throws IOException;

	void add(CompetencyDto dto) throws IOException;

	void update(CompetencyDto dto) throws IOException;

	void delete(int seq) throws IOException;

	CompetencyDto findByName(String name) throws IOException;

}
