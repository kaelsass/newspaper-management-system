package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.condition.InventorySexCondition;
import npp.dto.InventoryDto;

public interface InventorySexService {

	List<InventoryDto> getAllList(InventorySexCondition condition)
			throws IOException;

}
