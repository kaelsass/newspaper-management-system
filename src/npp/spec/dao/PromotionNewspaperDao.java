package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.dto.PromotionNewspaperDto;

public interface PromotionNewspaperDao {

	List<PromotionNewspaperDto> findByPromotionSeq(Transaction transaction,
			int promotionSeq) throws IOException;

	void add(Transaction transaction, PromotionNewspaperDto dto)
			throws IOException;

	void delete(Transaction transaction, int promotionSeq) throws IOException;

	List<PromotionNewspaperDto> findByNewspaperSeq(Transaction transaction,
			int newspaperSeq) throws IOException;

}
