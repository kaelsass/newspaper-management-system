package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.dto.AqDto;
import npp.spec.dao.AppraisalQuestionDao;
import npp.spec.dao.Transaction;
import npp.spec.service.AppraisalQuestionService;


public class AppraisalQuestionServiceImpl implements AppraisalQuestionService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1528029305275870761L;
	private Transaction transaction;
	private AppraisalQuestionDao dao;

	@Inject
	public AppraisalQuestionServiceImpl(Transaction transaction, AppraisalQuestionDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public List<AqDto> getAllList(int appraisalSeq) throws IOException{
		try{
			transaction.begin();
			List<AqDto> list = dao.getAllList(transaction, appraisalSeq);
			transaction.commit();
			return list;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}


	@Override
	public void add(AqDto dto) throws IOException {
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
