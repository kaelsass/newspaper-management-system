package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.dto.ArDto;
import npp.spec.dao.AppraisalReviewerDao;
import npp.spec.dao.Transaction;
import npp.spec.service.AppraisalReviewerService;


public class AppraisalReviewerServiceImpl implements AppraisalReviewerService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1528029305275870761L;
	private Transaction transaction;
	private AppraisalReviewerDao dao;

	@Inject
	public AppraisalReviewerServiceImpl(Transaction transaction, AppraisalReviewerDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}
	@Override
	public List<ArDto> getAllList(int appraisalSeq) throws IOException{
		try{
			transaction.begin();
			List<ArDto> list = dao.getAllList(transaction, appraisalSeq);
			transaction.commit();
			return list;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public List<ArDto> findByEmployeeID(String id) throws IOException{
		try{
			transaction.begin();
			List<ArDto> list = dao.findByEmployeeID(transaction, id);
			transaction.commit();
			return list;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(ArDto dto) throws IOException {
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
