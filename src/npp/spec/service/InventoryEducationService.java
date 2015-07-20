package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.condition.InventoryEducationCondition;
import npp.dto.InventoryDto;

public interface InventoryEducationService {

	List<InventoryDto> getAllList(InventoryEducationCondition condition)
			throws IOException;

}
