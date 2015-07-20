package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.dto.ReminderDto;

public interface ReminderDao {

	ReminderDto findByName(Transaction transaction, String name)
			throws IOException;

	void delete(Transaction transaction, int seq) throws IOException;

	void update(Transaction transaction, ReminderDto dto) throws IOException;

	void add(Transaction transaction, ReminderDto dto) throws IOException;

	ReminderDto findBySeq(Transaction transaction, int seq) throws IOException;

	List<ReminderDto> getAllList(Transaction transaction) throws IOException;

}
