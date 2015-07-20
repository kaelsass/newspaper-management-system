package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.dto.PerformanceDto;

public interface PerformanceDao {

	public void delete(Transaction transaction, int seq) throws IOException;

	public void update(Transaction transaction, PerformanceDto dto) throws IOException;

	public void add(Transaction transaction, PerformanceDto dto) throws IOException;

	public PerformanceDto findBySeq(Transaction transaction, int seq)
			throws IOException;

	public List<PerformanceDto> getAllList(Transaction transaction) throws IOException;

}
