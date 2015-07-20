package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.dto.PersonInfoDto;
import npp.spec.dao.PersonInfoDao;
import npp.spec.dao.Transaction;
import npp.spec.service.PersonInfoService;


public class PersonInfoServiceImpl implements PersonInfoService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 6001497697161300068L;
	private Transaction transaction;
	private PersonInfoDao personInfoDao;

	@Inject
	public PersonInfoServiceImpl(Transaction transaction, PersonInfoDao dao){
		this.transaction =  transaction;
		this.personInfoDao = dao;
	}

	@Override
	public PersonInfoDto findByID(String id) throws IOException {
		try{
			transaction.begin();
			PersonInfoDto dto = personInfoDao.findByID(transaction, id);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public List<PersonInfoDto> getAllList() throws IOException{
		try{
			transaction.begin();
			List<PersonInfoDto> list = personInfoDao.getAllList(transaction);
			transaction.commit();
			return list;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(PersonInfoDto employeeDto) throws IOException {
		try{
			transaction.begin();
			personInfoDao.add(transaction, employeeDto);
			transaction.commit();
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void update(PersonInfoDto employeeDto) throws IOException {
		try{
			transaction.begin();
			personInfoDao.update(transaction, employeeDto);
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
			personInfoDao.delete(transaction, id);
			transaction.commit();
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

}
