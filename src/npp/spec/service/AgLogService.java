package npp.spec.service;

import java.io.IOException;

import npp.dto.AgDto;
import npp.dto.AgLog;

public interface AgLogService {

	void delete(AgLog dto) throws IOException;

	void add(AgLog dto) throws IOException;

	void update(AgLog dto) throws IOException;

	AgLog find(AgDto ac, String employeeID) throws IOException;

}
