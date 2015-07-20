package npp.spec.dao;

import java.io.IOException;

import npp.dto.AcDto;
import npp.dto.AcLog;

public interface AcLogDao {


	void delete(Transaction transaction, AcLog log) throws IOException;

	void update(Transaction transaction, AcLog log) throws IOException;

	void add(Transaction transaction, AcLog log) throws IOException;

	AcLog find(Transaction transaction, AcDto ac, String employeeID)
			throws IOException;

}
