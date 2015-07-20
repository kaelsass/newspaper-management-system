package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.dto.PerformanceDto;

public interface PerformanceService {

	public List<PerformanceDto> getAllList()
			throws IOException;

}
