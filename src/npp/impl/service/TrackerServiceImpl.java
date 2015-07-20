package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.dto.TrackerDto;
import npp.spec.dao.TrackerDao;
import npp.spec.dao.Transaction;
import npp.spec.service.TrackerService;


public class TrackerServiceImpl implements TrackerService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1528029305275870761L;
	private Transaction transaction;
	private TrackerDao dao;

	@Inject
	public TrackerServiceImpl(Transaction transaction, TrackerDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public TrackerDto findBySeq(int seq) throws IOException{
		try{
			transaction.begin();
			TrackerDto dto = dao.findBySeq(transaction, seq);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public List<TrackerDto> getAllList() throws IOException{
		try{
			transaction.begin();
			List<TrackerDto> list = dao.getAllList(transaction);
			transaction.commit();
			return list;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(TrackerDto dto) throws IOException {
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
	public void update(TrackerDto dto) throws IOException {
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
