package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.dto.AppraisalDto;
import npp.spec.dao.AppraisalDao;
import npp.spec.dao.Transaction;
import npp.spec.service.AppraisalService;


public class AppraisalServiceImpl implements AppraisalService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1528029305275870761L;
	private Transaction transaction;
	private AppraisalDao dao;

	@Inject
	public AppraisalServiceImpl(Transaction transaction, AppraisalDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}
	@Override
	public List<AppraisalDto> getAllList() throws IOException{
		try{
			transaction.begin();
			List<AppraisalDto> list = dao.getAllList(transaction);
			transaction.commit();
			return list;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public int getNewInsertedSeq() throws IOException{
		try{
			transaction.begin();
			int dto = dao.getNewInsertedSeq(transaction);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public AppraisalDto findBySeq(int seq) throws IOException{
		try{
			transaction.begin();
			AppraisalDto dto = dao.findBySeq(transaction, seq);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(AppraisalDto dto) throws IOException {
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
	public void update(AppraisalDto dto) throws IOException {
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
