package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.condition.InventoryEducationCondition;
import npp.dto.InventoryDto;

public interface InventoryEducationDao {

	List<InventoryDto> getAllList(Transaction transaction,
			InventoryEducationCondition condition) throws IOException;

}
