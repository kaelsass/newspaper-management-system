package npp.spec.dao;

import java.io.IOException;

import npp.dto.AgDto;
import npp.dto.AgLog;

public interface AgLogDao {

	void add(Transaction transaction, AgLog log) throws IOException;

	AgLog find(Transaction transaction, AgDto dto, String employeeID)
			throws IOException;

	void update(Transaction transaction, AgLog log) throws IOException;

	void delete(Transaction transaction, AgLog log) throws IOException;

}
