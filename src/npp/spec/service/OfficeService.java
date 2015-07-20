package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.dto.OfficeDto;


public interface OfficeService {

	public List<OfficeDto> getAllList() throws IOException;
	public OfficeDto findBySeq(int seq) throws IOException;
	public void update(OfficeDto dto) throws IOException;
	public void delete(int seq) throws IOException;
	public void add(OfficeDto dto) throws IOException;
	public OfficeDto findByName(String name) throws IOException;
}
