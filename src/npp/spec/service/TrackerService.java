package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.dto.TrackerDto;

public interface TrackerService {

	public void delete(int seq) throws IOException;

	public void update(TrackerDto dto) throws IOException;

	public void add(TrackerDto dto) throws IOException;

	public List<TrackerDto> getAllList() throws IOException;

	public TrackerDto findBySeq(int seq) throws IOException;

}
