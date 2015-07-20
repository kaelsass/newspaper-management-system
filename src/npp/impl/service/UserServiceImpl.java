package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;

import npp.condition.DynamicQuery;
import npp.dto.UserDto;
import npp.spec.dao.Transaction;
import npp.spec.dao.UserDao;
import npp.spec.service.UserService;


public class UserServiceImpl implements UserService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 6618357296233537217L;
	private Transaction transaction;
	private UserDao userDao;

	@Inject
	public UserServiceImpl(Transaction transaction, UserDao s_userDao){
		this.transaction =  transaction;
		this.userDao = s_userDao;
	}

	@Override
	public UserDto findById(String id) throws IOException {
		try{
			transaction.begin();
			UserDto userDto = userDao.findBySeq(transaction, id);
			transaction.commit();
			return userDto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public List<UserDto> getAllList(DynamicQuery query) throws IOException{
		try{
			transaction.begin();
			List<UserDto> userList = userDao.getAllList(transaction, query);
			transaction.commit();
			return userList;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	@RolesAllowed("admin")
	public void add(UserDto userDto) throws IOException {
		try{
			transaction.begin();
			userDao.add(transaction, userDto);
			transaction.commit();
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}


	@Override
	@RolesAllowed("admin")
	public void update(UserDto userDto) throws IOException {
		try{
			transaction.begin();
			userDao.update(transaction, userDto);
			transaction.commit();
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	@RolesAllowed("admin")
	public void delete(String id) throws IOException {
		try{
			transaction.begin();
			userDao.delete(transaction, id);
			transaction.commit();
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

}
