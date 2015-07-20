package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.SubscriberDto;


public interface SubscriberDao {

	public SubscriberDto findBySeq(Transaction transaction, int seq)
			throws IOException;

	public List<SubscriberDto> getAllList(Transaction transaction, DynamicQuery query)
			throws IOException;

	public void add(Transaction transaction, SubscriberDto dto) throws IOException;

	public void update(Transaction transaction, SubscriberDto dto) throws IOException;

	public void delete(Transaction transaction, int seq) throws IOException;

}
