package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.condition.InventoryAgeCondition;
import npp.dto.InventoryDto;

public interface InventoryAgeService {

	List<InventoryDto> getAllList(InventoryAgeCondition condition)
			throws IOException;

}
