package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.SectionDto;


public interface SectionService {

	public SectionDto findBySeq(int seq) throws IOException;

	public List<SectionDto> getAllList(DynamicQuery query) throws IOException;

	public void add(SectionDto dto) throws IOException;

	public void update(SectionDto dto) throws IOException;

	public void delete(int seq) throws IOException;

}
