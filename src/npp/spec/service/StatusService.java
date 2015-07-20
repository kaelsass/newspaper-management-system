package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.dto.StatusDto;


public interface StatusService {

	public List<StatusDto> getAllList() throws IOException;
	public StatusDto findBySeq(int seq) throws IOException;
	public void update(StatusDto dto) throws IOException;
	public void delete(int seq) throws IOException;
	public void add(StatusDto dto) throws IOException;
	public StatusDto findByName(String name) throws IOException;
}
