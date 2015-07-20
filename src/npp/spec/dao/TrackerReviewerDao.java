package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.dto.TrackerReviewerDto;

public interface TrackerReviewerDao {

	public void delete(Transaction transaction, int trackerSeq) throws IOException;

	public void add(Transaction transaction, TrackerReviewerDto dto)
			throws IOException;

	public List<TrackerReviewerDto> findByTrackerSeq(Transaction transaction, int trackerSeq)
			throws IOException;

}
