package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.dto.TaskAdminDto;
import npp.spec.dao.TaskAdminDao;
import npp.spec.dao.Transaction;
import npp.spec.service.TaskAdminService;


public class TaskAdminServiceImpl implements TaskAdminService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1069655030868538783L;
	private Transaction transaction;
	private TaskAdminDao dao;

	@Inject
	public TaskAdminServiceImpl(Transaction transaction, TaskAdminDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public List<TaskAdminDto> findByTaskSeq(int taskSeq) throws IOException{
		try{
			transaction.begin();
			List<TaskAdminDto> list = dao.findByTaskSeq(transaction, taskSeq);
			transaction.commit();
			return list;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public List<TaskAdminDto> findByEmployeeID(String id) throws IOException{
		try{
			transaction.begin();
			List<TaskAdminDto> list = dao.findByEmployeeID(transaction, id);
			transaction.commit();
			return list;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(TaskAdminDto dto) throws IOException {
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
