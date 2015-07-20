package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.RetailDto;


public interface RetailService {

	public RetailDto findBySeq(int seq) throws IOException;

	public List<RetailDto> getAllList(DynamicQuery query) throws IOException;

	public void add(RetailDto dto) throws IOException;

	public void update(RetailDto dto) throws IOException;

	public void delete(int seq) throws IOException;

}
