package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.condition.DynamicQuery;
import npp.dto.TaskDto;
import npp.spec.dao.TaskDao;
import npp.spec.dao.Transaction;
import npp.spec.service.TaskService;


public class TaskServiceImpl implements TaskService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1069655030868538783L;
	private Transaction transaction;
	private TaskDao dao;

	@Inject
	public TaskServiceImpl(Transaction transaction, TaskDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public TaskDto findBySeq(int seq) throws IOException{
		try{
			transaction.begin();
			TaskDto dto = dao.findBySeq(transaction, seq);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}
	@Override
	public int getNewInsertedSeq() throws IOException{
		try{
			transaction.begin();
			int seq = dao.getNewInsertedSeq(transaction);
			transaction.commit();
			return seq;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public List<TaskDto> getAllList(DynamicQuery query) throws IOException{
		try{
			transaction.begin();
			List<TaskDto> list = dao.getAllList(transaction, query);
			transaction.commit();
			return list;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(TaskDto dto) throws IOException {
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
	public void update(TaskDto dto) throws IOException {
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
