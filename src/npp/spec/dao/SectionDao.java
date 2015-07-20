package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.SectionDto;


public interface SectionDao {

	public SectionDto findBySeq(Transaction transaction, int seq) throws IOException;

	public List<SectionDto> getAllList(Transaction transaction, DynamicQuery query)
			throws IOException;

	public void add(Transaction transaction, SectionDto dto) throws IOException;

	public void update(Transaction transaction, SectionDto dto) throws IOException;

	public void delete(Transaction transaction, int seq) throws IOException;

}
