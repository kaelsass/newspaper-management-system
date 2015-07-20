package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.dto.TrackerLogDto;

public interface TrackerLogDao {

	public void delete(Transaction transaction, int seq) throws IOException;

	public void add(Transaction transaction, TrackerLogDto dto) throws IOException;

	public void update(Transaction transaction, TrackerLogDto dto) throws IOException;

	public List<TrackerLogDto> findByTrackerSeq(Transaction transaction, int trackerSeq)
			throws IOException;

}
