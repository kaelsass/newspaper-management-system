package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.NonSubscriberDto;


public interface NonSubscriberService {

	public NonSubscriberDto findBySeq(int seq) throws IOException;

	public List<NonSubscriberDto> getAllList(DynamicQuery query) throws IOException;

	public void add(NonSubscriberDto dto) throws IOException;

	public void update(NonSubscriberDto dto) throws IOException;

	public void delete(int seq) throws IOException;

}
