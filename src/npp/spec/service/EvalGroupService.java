package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.dto.EvalGroupDto;

public interface EvalGroupService {

	EvalGroupDto findBySeq(int seq) throws IOException;

	List<EvalGroupDto> getAllList() throws IOException;

	void add(EvalGroupDto dto) throws IOException;

	void update(EvalGroupDto dto) throws IOException;

	void delete(int seq) throws IOException;

	EvalGroupDto findByName(String name) throws IOException;

}
