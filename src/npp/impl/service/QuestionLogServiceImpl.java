package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.dto.QuestionDto;
import npp.dto.QuestionLogDto;
import npp.spec.dao.QuestionLogDao;
import npp.spec.dao.Transaction;
import npp.spec.service.QuestionLogService;


public class QuestionLogServiceImpl implements QuestionLogService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1528029305275870761L;
	private Transaction transaction;
	private QuestionLogDao dao;

	@Inject
	public QuestionLogServiceImpl(Transaction transaction, QuestionLogDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public List<QuestionLogDto> getAllList(int questionnaireSeq, QuestionDto question) throws IOException{
		try{
			transaction.begin();
			List<QuestionLogDto> list = dao.getAllList(transaction, questionnaireSeq, question);
			transaction.commit();
			return list;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}


	@Override
	public void add(QuestionLogDto dto) throws IOException {
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
	public void delete(QuestionLogDto dto) throws IOException {
		try{
			transaction.begin();
			dao.delete(transaction, dto);
			transaction.commit();
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

}
