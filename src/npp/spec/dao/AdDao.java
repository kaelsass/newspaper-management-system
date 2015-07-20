package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.AdDto;


public interface AdDao {

	public AdDto findByID(Transaction transaction, String ID) throws IOException;

	public List<AdDto> getAllList(Transaction transaction, DynamicQuery query)
			throws IOException;

	public void add(Transaction transaction, AdDto dto) throws IOException;

	public void update(Transaction transaction, AdDto dto) throws IOException;

	public void delete(Transaction transaction, String id) throws IOException;

}
