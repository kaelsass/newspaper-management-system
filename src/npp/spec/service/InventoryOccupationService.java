package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.condition.InventoryOccupationCondition;
import npp.dto.InventoryDto;

public interface InventoryOccupationService {

	List<InventoryDto> getAllList(InventoryOccupationCondition condition)
			throws IOException;

}
