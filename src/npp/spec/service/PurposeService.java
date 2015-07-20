package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.dto.PurposeDto;


public interface PurposeService {

	public PurposeDto findBySeq(int seq) throws IOException;

	public List<PurposeDto> getAllList() throws IOException;

	public void add(PurposeDto dto) throws IOException;

	public void update(PurposeDto dto) throws IOException;

	public void delete(int seq) throws IOException;

}
