package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.RetailDto;


public interface RetailDao {

	public RetailDto findBySeq(Transaction transaction, int seq) throws IOException;

	public List<RetailDto> getAllList(Transaction transaction, DynamicQuery query)
			throws IOException;

	public void add(Transaction transaction, RetailDto dto) throws IOException;

	public void update(Transaction transaction, RetailDto dto) throws IOException;

	public void delete(Transaction transaction, int seq) throws IOException;

}
