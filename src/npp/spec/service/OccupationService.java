package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.OccupationDto;

public interface OccupationService {

	OccupationDto findByName(String name) throws IOException;

	void delete(int seq) throws IOException;

	void update(OccupationDto dto) throws IOException;

	void add(OccupationDto dto) throws IOException;

	List<OccupationDto> getAllList(DynamicQuery query) throws IOException;

	OccupationDto findBySeq(int seq) throws IOException;

}
