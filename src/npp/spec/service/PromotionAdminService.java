package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.dto.PromotionAdminDto;

public interface PromotionAdminService {

	List<PromotionAdminDto> findByPromotionSeq(int promotionSeq)
			throws IOException;
	List<PromotionAdminDto> findByEmployeeID(String id)
			throws IOException;

	void add(PromotionAdminDto dto) throws IOException;

	void delete(int trackerSeq) throws IOException;
	
}
