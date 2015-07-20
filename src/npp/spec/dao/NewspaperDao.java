package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.NewspaperDto;


public interface NewspaperDao {

	public NewspaperDto findBySeq(Transaction transaction, int seq) throws IOException;

	public List<NewspaperDto> getAllList(Transaction transaction, DynamicQuery query)
			throws IOException;

	public void add(Transaction transaction, NewspaperDto dto) throws IOException;

	public void update(Transaction transaction, NewspaperDto dto) throws IOException;

	public void delete(Transaction transaction, int seq) throws IOException;

}
