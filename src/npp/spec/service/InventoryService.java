package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.condition.InventoryCondition;
import npp.dto.InventoryDto;


public interface InventoryService {

	public List<InventoryDto> getAllList(InventoryCondition condition)
			throws IOException;

}
