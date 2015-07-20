package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.dto.SubjectDto;


public interface SubjectDao {

	public SubjectDto findBySeq(Transaction transaction, int seq) throws IOException;

	public List<SubjectDto> getAllList(Transaction transaction) throws IOException;

	public void add(Transaction transaction, SubjectDto dto) throws IOException;

	public void update(Transaction transaction, SubjectDto dto) throws IOException;

	public void delete(Transaction transaction, int seq) throws IOException;

}
