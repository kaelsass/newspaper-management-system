package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.GoalDto;

public interface GoalDao {

	GoalDto findByName(Transaction transaction, String name) throws IOException;

	void delete(Transaction transaction, int seq) throws IOException;

	void update(Transaction transaction, GoalDto dto) throws IOException;

	void add(Transaction transaction, GoalDto dto) throws IOException;

	GoalDto findBySeq(Transaction transaction, int seq) throws IOException;

	List<GoalDto> getAllList(Transaction transaction, DynamicQuery query) throws IOException;

}
