package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.condition.InventoryAgeCondition;
import npp.dto.InventoryDto;

public interface InventoryAgeDao {

	List<InventoryDto> getAllList(Transaction transaction,
			InventoryAgeCondition condition) throws IOException;

}
