package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.dto.OfficeDto;


public interface OfficeDao {
	public List<OfficeDto> getAllList(Transaction transaction) throws IOException;
	public OfficeDto findBySeq(Transaction transaction, int seq) throws IOException;
	public void add(Transaction transaction, OfficeDto dto) throws IOException;
	public void update(Transaction transaction, OfficeDto dto) throws IOException;
	public void delete(Transaction transaction, int seq) throws IOException;
	public OfficeDto findByName(Transaction transaction, String name) throws IOException;
}
