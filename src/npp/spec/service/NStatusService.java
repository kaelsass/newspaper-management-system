package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.dto.StatusDto;


public interface NStatusService {

	StatusDto findBySeq(int seq) throws IOException;

	List<StatusDto> getAllList() throws IOException;

	void add(StatusDto dto) throws IOException;

	void update(StatusDto dto) throws IOException;

	void delete(int seq) throws IOException;

	StatusDto findByName(String name) throws IOException;

}
