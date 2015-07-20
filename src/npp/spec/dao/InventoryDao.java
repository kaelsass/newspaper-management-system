package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.condition.InventoryCondition;
import npp.dto.InventoryDto;


public interface InventoryDao {

	List<InventoryDto> getAllList(Transaction transaction,
			InventoryCondition condition) throws IOException;

}
