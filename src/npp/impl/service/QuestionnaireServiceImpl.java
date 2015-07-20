package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.condition.DynamicQuery;
import npp.dto.QuestionnaireDto;
import npp.spec.dao.QuestionnaireDao;
import npp.spec.dao.Transaction;
import npp.spec.service.QuestionnaireService;


public class QuestionnaireServiceImpl implements QuestionnaireService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1528029305275870761L;
	private Transaction transaction;
	private QuestionnaireDao dao;

	@Inject
	public QuestionnaireServiceImpl(Transaction transaction, QuestionnaireDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public QuestionnaireDto findBySeq(int seq) throws IOException{
		try{
			transaction.begin();
			QuestionnaireDto dto = dao.findBySeq(transaction, seq);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public List<QuestionnaireDto> getAllList(DynamicQuery query) throws IOException{
		try{
			transaction.begin();
			List<QuestionnaireDto> List = dao.getAllList(transaction, query);
			transaction.commit();
			return List;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(QuestionnaireDto dto) throws IOException {
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
	public void update(QuestionnaireDto dto) throws IOException {
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
