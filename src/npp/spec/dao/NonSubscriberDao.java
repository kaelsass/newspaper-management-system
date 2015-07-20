package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.NonSubscriberDto;


public interface NonSubscriberDao {

	public NonSubscriberDto findBySeq(Transaction transaction, int seq)
			throws IOException;

	public List<NonSubscriberDto> getAllList(Transaction transaction,
			DynamicQuery query) throws IOException;

	public void add(Transaction transaction, NonSubscriberDto dto) throws IOException;

	public void update(Transaction transaction, NonSubscriberDto dto)
			throws IOException;

	public void delete(Transaction transaction, int seq) throws IOException;

}
