package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.condition.DynamicQuery;
import npp.dto.WorkInfoDto;
import npp.spec.dao.Transaction;
import npp.spec.dao.WorkInfoDao;
import npp.spec.service.WorkInfoService;


public class WorkInfoServiceImpl implements WorkInfoService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 6001497697161300068L;
	private Transaction transaction;
	private WorkInfoDao workInfoDao;

	@Inject
	public WorkInfoServiceImpl(Transaction transaction, WorkInfoDao dao){
		this.transaction =  transaction;
		this.workInfoDao = dao;
	}

	@Override
	public WorkInfoDto findByID(String id) throws IOException {
		try{
			transaction.begin();
			WorkInfoDto userDto = workInfoDao.findByID(transaction, id);
			transaction.commit();
			return userDto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public List<WorkInfoDto> getAllList(DynamicQuery query) throws IOException{
		try{
			transaction.begin();
			List<WorkInfoDto> employeeList = workInfoDao.getAllList(transaction, query);
			transaction.commit();
			return employeeList;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(WorkInfoDto employeeDto) throws IOException {
		try{
			transaction.begin();
			workInfoDao.add(transaction, employeeDto);
			transaction.commit();
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void update(WorkInfoDto employeeDto) throws IOException {
		try{
			transaction.begin();
			workInfoDao.update(transaction, employeeDto);
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
			workInfoDao.delete(transaction, id);
			transaction.commit();
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public WorkInfoDto findByName(String name) throws IOException {
		try{
			transaction.begin();
			WorkInfoDto dto = workInfoDao.findByName(transaction, name);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

}
