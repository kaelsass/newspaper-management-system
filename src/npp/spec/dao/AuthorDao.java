package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.AuthorDto;


public interface AuthorDao {

	public AuthorDto findBySeq(Transaction transaction, int seq) throws IOException;

	public List<AuthorDto> getAllList(Transaction transaction, DynamicQuery query)
			throws IOException;

	public void add(Transaction transaction, AuthorDto dto) throws IOException;

	public void delete(Transaction transaction, int seq) throws IOException;

	public void update(Transaction transaction, AuthorDto dto) throws IOException;

	public AuthorDto findByName(Transaction transaction, String name)throws IOException;

}
