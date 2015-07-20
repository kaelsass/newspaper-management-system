package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.PublicationDateDto;


public interface PdateDao {

	public List<PublicationDateDto> getAllList(Transaction transaction,
			DynamicQuery query) throws IOException;

}
