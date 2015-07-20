package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.PunchDto;


public interface PunchDao {

	public PunchDto findBySeq(Transaction transaction, int seq) throws IOException;

	public List<PunchDto> getAllList(Transaction transaction, DynamicQuery query)
			throws IOException;

	public void add(Transaction transaction, PunchDto dto) throws IOException;

	public void update(Transaction transaction, PunchDto dto) throws IOException;

	public void delete(Transaction transaction, int seq) throws IOException;

	public List<PunchDto> getUnfinishedList(Transaction transaction)
			throws IOException;

}
