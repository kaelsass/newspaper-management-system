package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.dto.ActivityDto;

public interface ActivityService {

	public List<ActivityDto> findByTaskSeq(int seq) throws IOException;

	public void add(ActivityDto dto) throws IOException;

	public void update(ActivityDto dto) throws IOException;

	public void delete(int seq) throws IOException;

	public ActivityDto findBySeq(int seq)throws IOException;

}
