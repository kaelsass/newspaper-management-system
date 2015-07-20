package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.PromotionDto;

public interface PromotionService {

	PromotionDto findBySeq(int seq) throws IOException;

	int getNewInsertedSeq() throws IOException;

	List<PromotionDto> getAllEditList(DynamicQuery query) throws IOException;

	void add(PromotionDto dto) throws IOException;

	void update(PromotionDto dto) throws IOException;

	void delete(int seq) throws IOException;

	List<PromotionDto> getAllList(DynamicQuery query) throws IOException;

}
