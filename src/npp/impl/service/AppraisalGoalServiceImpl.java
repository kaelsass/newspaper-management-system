package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.dto.AgDto;
import npp.spec.dao.AppraisalGoalDao;
import npp.spec.dao.Transaction;
import npp.spec.service.AppraisalGoalService;


public class AppraisalGoalServiceImpl implements AppraisalGoalService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1528029305275870761L;
	private Transaction transaction;
	private AppraisalGoalDao dao;

	@Inject
	public AppraisalGoalServiceImpl(Transaction transaction, AppraisalGoalDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public List<AgDto> getAllList(int appraisalSeq) throws IOException{
		try{
			transaction.begin();
			List<AgDto> list = dao.getAllList(transaction, appraisalSeq);
			transaction.commit();
			return list;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}


	@Override
	public void add(AgDto dto) throws IOException {
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
