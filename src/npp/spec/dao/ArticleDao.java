package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.ArticleDto;


public interface ArticleDao {

	public List<ArticleDto> getAllList(Transaction transaction, DynamicQuery query)throws IOException;

	public void add(Transaction transaction, ArticleDto dto)throws IOException;

	public void update(Transaction transaction, ArticleDto dto)throws IOException;

	public void delete(Transaction transaction, int seq)throws IOException;

	public ArticleDto findBySeq(Transaction transaction, int seq)throws IOException;

}
