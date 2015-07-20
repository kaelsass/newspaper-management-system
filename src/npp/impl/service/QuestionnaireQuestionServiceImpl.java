package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.dto.QqDto;
import npp.spec.dao.QuestionnaireQuestionDao;
import npp.spec.dao.Transaction;
import npp.spec.service.QuestionnaireQuestionService;


public class QuestionnaireQuestionServiceImpl implements QuestionnaireQuestionService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1528029305275870761L;
	private Transaction transaction;
	private QuestionnaireQuestionDao dao;

	@Inject
	public QuestionnaireQuestionServiceImpl(Transaction transaction, QuestionnaireQuestionDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public List<QqDto> getAllList(int questionnaireSeq) throws IOException {
		try{
			transaction.begin();
			List<QqDto> list = dao.getAllList(transaction, questionnaireSeq);
			transaction.commit();
			return list;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(QqDto dto) throws IOException {
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
	public void delete(int questionnaireSeq) throws IOException {
		try{
			transaction.begin();
			dao.delete(transaction, questionnaireSeq);
			transaction.commit();
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

}
