package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.NewspaperDto;


public interface NewspaperService {

	public NewspaperDto findBySeq(int seq) throws IOException;

	public List<NewspaperDto> getAllList(DynamicQuery query) throws IOException;

	public void add(NewspaperDto dto) throws IOException;

	public void update(NewspaperDto dto) throws IOException;

	public void delete(int seq) throws IOException;

}
