package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.condition.DynamicQuery;
import npp.dto.ArticleDto;
import npp.spec.dao.ArticleDao;
import npp.spec.dao.Transaction;
import npp.spec.service.ArticleService;


public class ArticleServiceImpl implements ArticleService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1528029305275870761L;
	private Transaction transaction;
	private ArticleDao dao;

	@Inject
	public ArticleServiceImpl(Transaction transaction, ArticleDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public ArticleDto findBySeq(int seq) throws IOException{
		try{
			transaction.begin();
			ArticleDto dto = dao.findBySeq(transaction, seq);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public List<ArticleDto> getAllList(DynamicQuery query) throws IOException{
		try{
			transaction.begin();
			List<ArticleDto> list = dao.getAllList(transaction, query);
			transaction.commit();
			return list;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(ArticleDto dto) throws IOException {
		try{
			transaction.begin();
			dao.add(transaction, dto);
			transaction.commit();
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void update(ArticleDto dto) throws IOException {
		try{
			transaction.begin();
			dao.update(transaction, dto);
			transaction.commit();
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void delete(int seq) throws IOException {
		try{
			transaction.begin();
			dao.delete(transaction, seq);
			transaction.commit();
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

}
