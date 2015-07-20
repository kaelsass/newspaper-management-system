package npp.spec.dao;

import java.io.IOException;

import npp.dto.AqDto;
import npp.dto.AqLog;

public interface AqLogDao {

	void delete(Transaction transaction, AqLog log) throws IOException;

	void update(Transaction transaction, AqLog log) throws IOException;

	void add(Transaction transaction, AqLog log) throws IOException;

	AqLog find(Transaction transaction, AqDto dto, String employeeID)
			throws IOException;

}
