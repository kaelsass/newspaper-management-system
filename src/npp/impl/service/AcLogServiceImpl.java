package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;

import javax.inject.Inject;

import npp.dto.AcDto;
import npp.dto.AcLog;
import npp.spec.dao.AcLogDao;
import npp.spec.dao.Transaction;
import npp.spec.service.AcLogService;


public class AcLogServiceImpl implements AcLogService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1528029305275870761L;
	private Transaction transaction;
	private AcLogDao dao;

	@Inject
	public AcLogServiceImpl(Transaction transaction, AcLogDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public AcLog find(AcDto ac, String employeeID) throws IOException{
		try{
			transaction.begin();
			AcLog dto = dao.find(transaction, ac, employeeID);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}


	@Override
	public void add(AcLog dto) throws IOException {
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
	public void update(AcLog dto) throws IOException {
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
	public void delete(AcLog dto) throws IOException {
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
