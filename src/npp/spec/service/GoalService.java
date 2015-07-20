package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.GoalDto;

public interface GoalService {

	GoalDto findBySeq(int seq) throws IOException;

	List<GoalDto> getAllList(DynamicQuery query) throws IOException;

	void add(GoalDto dto) throws IOException;

	void update(GoalDto dto) throws IOException;

	void delete(int seq) throws IOException;

	GoalDto findByName(String name) throws IOException;

}
