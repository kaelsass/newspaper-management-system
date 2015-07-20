package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.dto.SubjectDto;


public interface SubjectService {

	SubjectDto findBySeq(int seq) throws IOException;

	List<SubjectDto> getAllList() throws IOException;

	void add(SubjectDto dto) throws IOException;

	void update(SubjectDto dto) throws IOException;

	void delete(int seq) throws IOException;

}
