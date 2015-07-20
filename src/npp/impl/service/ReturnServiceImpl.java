package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.condition.DynamicQuery;
import npp.dto.ReturnDto;
import npp.spec.dao.ReturnDao;
import npp.spec.dao.Transaction;
import npp.spec.service.ReturnService;


public class ReturnServiceImpl implements ReturnService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1528029305275870761L;
	private Transaction transaction;
	private ReturnDao dao;

	@Inject
	public ReturnServiceImpl(Transaction transaction, ReturnDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public ReturnDto findBySeq(int seq) throws IOException{
		try{
			transaction.begin();
			ReturnDto dto = dao.findBySeq(transaction, seq);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public List<ReturnDto> getAllList(DynamicQuery query) throws IOException{
		try{
			transaction.begin();
			List<ReturnDto> list = dao.getAllList(transaction, query);
			transaction.commit();
			return list;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(ReturnDto dto) throws IOException {
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
	public void update(ReturnDto dto) throws IOException {
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
