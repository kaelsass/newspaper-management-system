package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.condition.DynamicQuery;
import npp.dto.QuestionDto;
import npp.spec.dao.NquestionDao;
import npp.spec.dao.Transaction;
import npp.spec.service.NquestionService;


public class NquestionServiceImpl implements NquestionService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1528029305275870761L;
	private Transaction transaction;
	private NquestionDao dao;

	@Inject
	public NquestionServiceImpl(Transaction transaction, NquestionDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public QuestionDto findBySeq(int seq) throws IOException{
		try{
			transaction.begin();
			QuestionDto dto = dao.findBySeq(transaction, seq);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public List<QuestionDto> getAllList(DynamicQuery query) throws IOException{
		try{
			transaction.begin();
			List<QuestionDto> List = dao.getAllList(transaction, query);
			transaction.commit();
			return List;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(QuestionDto dto) throws IOException {
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
	public void update(QuestionDto dto) throws IOException {
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
