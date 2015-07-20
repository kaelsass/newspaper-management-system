package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.dto.ActivityDto;
import npp.spec.dao.ActivityDao;
import npp.spec.dao.Transaction;
import npp.spec.service.ActivityService;


public class ActivityServiceImpl implements ActivityService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1528029305275870761L;
	private Transaction transaction;
	private ActivityDao dao;

	@Inject
	public ActivityServiceImpl(Transaction transaction, ActivityDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public List<ActivityDto> findByTaskSeq(int taskSeq) throws IOException{
		try{
			transaction.begin();
			List<ActivityDto> list = dao.findByTaskSeq(transaction, taskSeq);
			transaction.commit();
			return list;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public ActivityDto findBySeq(int seq) throws IOException{
		try{
			transaction.begin();
			ActivityDto dto = dao.findBySeq(transaction, seq);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(ActivityDto dto) throws IOException {
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
	public void update(ActivityDto dto) throws IOException {
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
