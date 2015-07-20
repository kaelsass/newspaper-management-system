package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.ReturnDto;


public interface ReturnDao {

	public ReturnDto findBySeq(Transaction transaction, int seq) throws IOException;

	public List<ReturnDto> getAllList(Transaction transaction, DynamicQuery query)
			throws IOException;

	public void add(Transaction transaction, ReturnDto dto) throws IOException;

	public void update(Transaction transaction, ReturnDto dto) throws IOException;

	public void delete(Transaction transaction, int seq) throws IOException;

}
