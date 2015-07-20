package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.condition.InventorySexCondition;
import npp.dto.InventoryDto;

public interface InventorySexDao {

	List<InventoryDto> getAllList(Transaction transaction,
			InventorySexCondition condition) throws IOException;

}
