package npp.spec.service;

import java.io.IOException;

import npp.dto.AqDto;
import npp.dto.AqLog;

public interface AqLogService {

	AqLog find(AqDto ac, String employeeID) throws IOException;

	void add(AqLog dto) throws IOException;

	void update(AqLog dto) throws IOException;

	void delete(AqLog dto) throws IOException;

}
