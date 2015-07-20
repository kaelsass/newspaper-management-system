package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.dto.EventDto;


public interface ScheduleService {

	public void delete(String id) throws IOException;

	public List<EventDto> getAllList()throws IOException;

	void add(EventDto dto) throws IOException;

	void update(EventDto dto) throws IOException;

	List<EventDto> getNotificationEvents() throws IOException;

}
