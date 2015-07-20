package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.dto.EvalGroupDto;

public interface EvalGroupDao {

	EvalGroupDto findByName(Transaction transaction, String name)
			throws IOException;

	void delete(Transaction transaction, int seq) throws IOException;

	void update(Transaction transaction, EvalGroupDto dto) throws IOException;

	void add(Transaction transaction, EvalGroupDto dto) throws IOException;

	EvalGroupDto findBySeq(Transaction transaction, int seq) throws IOException;

	List<EvalGroupDto> getAllList(Transaction transaction) throws IOException;

}
