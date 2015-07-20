package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.dto.PromotionNewspaperDto;

public interface PromotionNewspaperService {

	List<PromotionNewspaperDto> findByPromotionSeq(int promotionSeq)
			throws IOException;
	List<PromotionNewspaperDto> findByNewspaperSeq(int newspaperSeq)
			throws IOException;

	void add(PromotionNewspaperDto dto) throws IOException;

	void delete(int promotionSeq) throws IOException;

}
