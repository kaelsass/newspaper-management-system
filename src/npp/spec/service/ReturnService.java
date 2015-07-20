package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.ReturnDto;


public interface ReturnService {

	public ReturnDto findBySeq(int seq) throws IOException;

	public List<ReturnDto> getAllList(DynamicQuery query) throws IOException;

	public void add(ReturnDto dto) throws IOException;

	public void update(ReturnDto dto) throws IOException;

	public void delete(int seq) throws IOException;

}
