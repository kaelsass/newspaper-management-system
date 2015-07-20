package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.dto.PayTypeDto;
import npp.spec.dao.PayTypeDao;
import npp.spec.dao.Transaction;
import npp.spec.service.PayTypeService;


public class PayTypeServiceImpl implements PayTypeService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 4489515223928182709L;
	private Transaction transaction;
	private PayTypeDao dao;

	@Inject
	public PayTypeServiceImpl(Transaction transaction, PayTypeDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public PayTypeDto findBySeq(int seq) throws IOException{
		try{
			transaction.begin();
			PayTypeDto dto = dao.findBySeq(transaction, seq);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public List<PayTypeDto> getAllList() throws IOException{
		try{
			transaction.begin();
			List<PayTypeDto> list = dao.getAllList(transaction);
			transaction.commit();
			return list;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(PayTypeDto dto) throws IOException {
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
	public void update(PayTypeDto dto) throws IOException {
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
