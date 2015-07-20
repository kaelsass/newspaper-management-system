package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.dto.TrackerReviewerDto;

public interface TrackerReviewerService {

	public void delete(int trackerSeq) throws IOException;

	public void add(TrackerReviewerDto dto) throws IOException;

	public List<TrackerReviewerDto> findByTrackerSeq(int trackerSeq) throws IOException;

}
