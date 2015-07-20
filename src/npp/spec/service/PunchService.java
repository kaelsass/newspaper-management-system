package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.PunchDto;


public interface PunchService {

	public PunchDto findBySeq(int seq) throws IOException;

	public List<PunchDto> getAllList(DynamicQuery query) throws IOException;

	public void add(PunchDto dto) throws IOException;

	public void update(PunchDto dto) throws IOException;

	public void delete(int seq) throws IOException;

	public List<PunchDto> getUnfinishedList()throws IOException;

}
