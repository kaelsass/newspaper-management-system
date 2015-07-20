package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.dto.PromotionAdminDto;

public interface PromotionAdminDao {

	void delete(Transaction transaction, int promotionSeq) throws IOException;

	void add(Transaction transaction, PromotionAdminDto dto) throws IOException;

	List<PromotionAdminDto> findByPromotionSeq(Transaction transaction,
			int promotionSeq) throws IOException;

	List<PromotionAdminDto> findByEmployeeID(Transaction transaction, String id) throws IOException;
}
