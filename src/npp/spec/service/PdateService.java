package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.PublicationDateDto;


public interface PdateService {

	public List<PublicationDateDto> getAllList(DynamicQuery query) throws IOException;

}
