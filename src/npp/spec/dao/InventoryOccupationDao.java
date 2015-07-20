package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.condition.InventoryOccupationCondition;
import npp.dto.InventoryDto;

public interface InventoryOccupationDao {

	List<InventoryDto> getAllList(Transaction transaction,
			InventoryOccupationCondition condition) throws IOException;

}
