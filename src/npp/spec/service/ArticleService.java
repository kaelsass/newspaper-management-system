package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.ArticleDto;


public interface ArticleService {

	public ArticleDto findBySeq(int seq) throws IOException;

	public List<ArticleDto> getAllList(DynamicQuery query) throws IOException;

	public void add(ArticleDto dto) throws IOException;

	public void update(ArticleDto dto) throws IOException;

	public void delete(int seq) throws IOException;

}
