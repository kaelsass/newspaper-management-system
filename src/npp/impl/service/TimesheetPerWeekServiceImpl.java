package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.dto.TimesheetPerWeekDto;
import npp.spec.dao.TimesheetPerWeekDao;
import npp.spec.dao.Transaction;
import npp.spec.service.TimesheetPerWeekService;


public class TimesheetPerWeekServiceImpl implements TimesheetPerWeekService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1528029305275870761L;
	private Transaction transaction;
	private TimesheetPerWeekDao dao;

	@Inject
	public TimesheetPerWeekServiceImpl(Transaction transaction, TimesheetPerWeekDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public List<TimesheetPerWeekDto> findByTimesheetSeq(int seq) throws IOException{
		try{
			transaction.begin();
			List<TimesheetPerWeekDto> list = dao.findByTimesheetSeq(transaction, seq);
			transaction.commit();
			return list;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(TimesheetPerWeekDto dto) throws IOException {
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
	public void update(TimesheetPerWeekDto dto) throws IOException {
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
