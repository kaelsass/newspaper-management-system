package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.dto.EventDto;
import npp.spec.dao.ScheduleDao;
import npp.spec.dao.Transaction;
import npp.spec.service.ScheduleService;



public class ScheduleServiceImpl implements ScheduleService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1528029305275870761L;
	private Transaction transaction;
	private ScheduleDao dao;

	@Inject
	public ScheduleServiceImpl(Transaction transaction, ScheduleDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public List<EventDto> getAllList() throws IOException {
		try{
			transaction.begin();
			List<EventDto> list = dao.getAllList(transaction);
			transaction.commit();
			return list;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}
	
	@Override
	public List<EventDto> getNotificationEvents() throws IOException {
		try{
			transaction.begin();
			List<EventDto> list = dao.getNotificationEvents(transaction);
			transaction.commit();
			return list;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}


	@Override
	public void add(EventDto dto) throws IOException {
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
	public void update(EventDto dto) throws IOException {
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
	public void delete(String id) throws IOException {
		try{
			transaction.begin();
			dao.delete(transaction, id);
			transaction.commit();
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

}
