package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.dto.TrackerLogDto;

public interface TrackerLogService {

	public void add(TrackerLogDto dto) throws IOException;

	public void update(TrackerLogDto dto) throws IOException;

	public void delete(int seq) throws IOException;

	public List<TrackerLogDto> findByTrackerSeq(int trackerSeq)
			throws IOException;

}
