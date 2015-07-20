package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.SubscriberDto;


public interface SubscriberService {

	SubscriberDto findBySeq(int seq) throws IOException;

	List<SubscriberDto> getAllList(DynamicQuery query) throws IOException;

	void add(SubscriberDto dto) throws IOException;

	void update(SubscriberDto dto) throws IOException;

	void delete(int seq) throws IOException;

}
