package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.dto.EventDto;


public interface ScheduleDao {

	public void update(Transaction transaction, EventDto dto)throws IOException;

	public void add(Transaction transaction, EventDto dto)throws IOException;

	public void delete(Transaction transaction, String id)throws IOException;

	public List<EventDto> getAllList(Transaction transaction)throws IOException;

	List<EventDto> getNotificationEvents(Transaction transaction)
			throws IOException;

}
