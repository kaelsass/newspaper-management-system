package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.condition.DynamicQuery;
import npp.dto.RetailDto;
import npp.spec.dao.RetailDao;
import npp.spec.dao.Transaction;
import npp.spec.service.RetailService;


public class RetailServiceImpl implements RetailService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1528029305275870761L;
	private Transaction transaction;
	private RetailDao dao;

	@Inject
	public RetailServiceImpl(Transaction transaction, RetailDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public RetailDto findBySeq(int seq) throws IOException{
		try{
			transaction.begin();
			RetailDto dto = dao.findBySeq(transaction, seq);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public List<RetailDto> getAllList(DynamicQuery query) throws IOException{
		try{
			transaction.begin();
			List<RetailDto> list = dao.getAllList(transaction, query);
			transaction.commit();
			return list;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(RetailDto dto) throws IOException {
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
	public void update(RetailDto dto) throws IOException {
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
