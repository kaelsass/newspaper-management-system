package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;

import javax.inject.Inject;

import npp.dto.AqDto;
import npp.dto.AqLog;
import npp.spec.dao.AqLogDao;
import npp.spec.dao.Transaction;
import npp.spec.service.AqLogService;


public class AqLogServiceImpl implements AqLogService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1528029305275870761L;
	private Transaction transaction;
	private AqLogDao dao;

	@Inject
	public AqLogServiceImpl(Transaction transaction, AqLogDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public AqLog find(AqDto ac, String employeeID) throws IOException{
		try{
			transaction.begin();
			AqLog dto = dao.find(transaction, ac, employeeID);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}


	@Override
	public void add(AqLog dto) throws IOException {
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
	public void update(AqLog dto) throws IOException {
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
	public void delete(AqLog dto) throws IOException {
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
