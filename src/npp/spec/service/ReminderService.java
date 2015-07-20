package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.dto.ReminderDto;

public interface ReminderService {

	ReminderDto findBySeq(int seq) throws IOException;

	List<ReminderDto> getAllList() throws IOException;

	void add(ReminderDto dto) throws IOException;

	void update(ReminderDto dto) throws IOException;

	void delete(int seq) throws IOException;

	ReminderDto findByName(String name) throws IOException;

}
