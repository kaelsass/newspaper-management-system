package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.dto.StatusDto;


public interface StatusDao {
	public List<StatusDto> getAllList(Transaction transaction) throws IOException;
	public StatusDto findBySeq(Transaction transaction, int seq) throws IOException;
	public void add(Transaction transaction, StatusDto dto) throws IOException;
	public void update(Transaction transaction, StatusDto dto) throws IOException;
	public void delete(Transaction transaction, int seq) throws IOException;
	public StatusDto findByName(Transaction transaction, String name) throws IOException;
}
