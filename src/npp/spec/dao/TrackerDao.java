package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.dto.TrackerDto;

public interface TrackerDao {

	public void delete(Transaction transaction, int seq) throws IOException;

	public void update(Transaction transaction, TrackerDto dto) throws IOException;

	public void add(Transaction transaction, TrackerDto dto) throws IOException;

	public TrackerDto findBySeq(Transaction transaction, int seq) throws IOException;

	public List<TrackerDto> getAllList(Transaction transaction) throws IOException;

}
