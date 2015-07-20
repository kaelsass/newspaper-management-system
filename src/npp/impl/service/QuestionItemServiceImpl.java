package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.dto.QuestionItemDto;
import npp.spec.dao.QuestionItemDao;
import npp.spec.dao.Transaction;
import npp.spec.service.QuestionItemService;


public class QuestionItemServiceImpl implements QuestionItemService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1528029305275870761L;
	private Transaction transaction;
	private QuestionItemDao dao;

	@Inject
	public QuestionItemServiceImpl(Transaction transaction, QuestionItemDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public List<QuestionItemDto> getAllList(int questionSeq) throws IOException{
		try{
			transaction.begin();
			List<QuestionItemDto> List = dao.getAllList(transaction, questionSeq);
			transaction.commit();
			return List;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(QuestionItemDto dto) throws IOException {
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
	public void delete(int questionSeq) throws IOException {
		try{
			transaction.begin();
			dao.delete(transaction, questionSeq);
			transaction.commit();
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

}
