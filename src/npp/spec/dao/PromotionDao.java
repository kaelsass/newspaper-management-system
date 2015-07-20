package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.PromotionDto;

public interface PromotionDao {

	void delete(Transaction transaction, int seq) throws IOException;

	void update(Transaction transaction, PromotionDto dto) throws IOException;

	void add(Transaction transaction, PromotionDto dto) throws IOException;

	PromotionDto findBySeq(Transaction transaction, int seq) throws IOException;

	List<PromotionDto> getAllEditList(Transaction transaction, DynamicQuery query)
			throws IOException;

	int getNewInsertedSeq(Transaction transaction) throws IOException;

	List<PromotionDto> getAllList(Transaction transaction, DynamicQuery query)
			throws IOException;

}
