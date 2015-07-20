package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.AuthorDto;


public interface AuthorService {
	public AuthorDto findBySeq(int seq) throws IOException;

	public List<AuthorDto> getAllList(DynamicQuery query) throws IOException;

	public void add(AuthorDto dto) throws IOException;

	public void update(AuthorDto dto) throws IOException;

	public void delete(int seq) throws IOException;

	public AuthorDto findByName(String name)throws IOException;
}
