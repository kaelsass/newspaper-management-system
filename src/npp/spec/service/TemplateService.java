package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.dto.StatusDto;

public interface TemplateService {

	StatusDto findByName(String name) throws IOException;

	void delete(int seq) throws IOException;

	void update(StatusDto dto) throws IOException;

	void add(StatusDto dto) throws IOException;

	List<StatusDto> getAllList() throws IOException;

	StatusDto findBySeq(int seq) throws IOException;

}
