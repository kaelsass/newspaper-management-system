package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.condition.DynamicQuery;
import npp.dto.TimesheetDto;
import npp.spec.dao.TimesheetDao;
import npp.spec.dao.Transaction;
import npp.spec.service.TimesheetService;


public class TimesheetServiceImpl implements TimesheetService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1528029305275870761L;
	private Transaction transaction;
	private TimesheetDao dao;

	@Inject
	public TimesheetServiceImpl(Transaction transaction, TimesheetDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}
	@Override
	public List<TimesheetDto> getAllList(DynamicQuery query) throws IOException{
		try{
			transaction.begin();
			List<TimesheetDto> list = dao.getAllList(transaction, query);
			transaction.commit();
			return list;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public TimesheetDto findBySeq(int seq) throws IOException{
		try{
			transaction.begin();
			TimesheetDto dto = dao.findBySeq(transaction, seq);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(TimesheetDto dto) throws IOException {
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
	public void update(TimesheetDto dto) throws IOException {
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
	@Override
	public int getNewInsertedSeq() throws IOException {
		try{
			transaction.begin();
			int seq = dao.getNewInsertedSeq(transaction);
			transaction.commit();
			return seq;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

}
