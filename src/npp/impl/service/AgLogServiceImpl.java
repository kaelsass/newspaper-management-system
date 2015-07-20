package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;

import javax.inject.Inject;

import npp.dto.AgDto;
import npp.dto.AgLog;
import npp.spec.dao.AgLogDao;
import npp.spec.dao.Transaction;
import npp.spec.service.AgLogService;


public class AgLogServiceImpl implements AgLogService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1528029305275870761L;
	private Transaction transaction;
	private AgLogDao dao;

	@Inject
	public AgLogServiceImpl(Transaction transaction, AgLogDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public AgLog find(AgDto ac, String employeeID) throws IOException{
		try{
			transaction.begin();
			AgLog dto = dao.find(transaction, ac, employeeID);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}


	@Override
	public void add(AgLog dto) throws IOException {
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
	public void update(AgLog dto) throws IOException {
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
	public void delete(AgLog dto) throws IOException {
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
