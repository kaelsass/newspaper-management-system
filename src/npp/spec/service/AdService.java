package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.AdDto;


public interface AdService {

	public AdDto findByID(String ID) throws IOException;

	public List<AdDto> getAllList(DynamicQuery query) throws IOException;

	public void add(AdDto dto) throws IOException;

	public void update(AdDto dto) throws IOException;

	public void delete(String id) throws IOException;

}
