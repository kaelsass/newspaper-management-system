package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.dto.ActivityDto;

public interface ActivityDao {

	public List<ActivityDto> findByTaskSeq(Transaction transaction, int taskSeq)
			throws IOException;

	public void add(Transaction transaction, ActivityDto dto) throws IOException;

	public void delete(Transaction transaction, int seq) throws IOException;

	public void update(Transaction transaction, ActivityDto dto) throws IOException;

	public ActivityDto findBySeq(Transaction transaction, int activitySeq)throws IOException;

}
