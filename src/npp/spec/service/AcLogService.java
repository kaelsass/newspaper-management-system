package npp.spec.service;

import java.io.IOException;

import npp.dto.AcDto;
import npp.dto.AcLog;

public interface AcLogService {

	AcLog find(AcDto ac, String employeeID) throws IOException;

	void add(AcLog dto) throws IOException;

	void update(AcLog dto) throws IOException;

	void delete(AcLog dto) throws IOException;

}
