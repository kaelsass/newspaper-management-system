package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;

import javax.inject.Inject;

import npp.spec.dao.LoginDao;
import npp.spec.dao.Transaction;
import npp.spec.service.LoginService;


public class LoginServiceImpl implements LoginService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1528029305275870761L;
	private Transaction transaction;
	private LoginDao dao;

	@Inject
	public LoginServiceImpl(Transaction transaction, LoginDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public boolean isValid(String username, String password) throws IOException{
		try{
			transaction.begin();
			boolean value = dao.isValid(transaction, username, password);
			transaction.commit();
			return value;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public String getRole(String username) throws IOException{
		try{
			transaction.begin();
			String role = dao.getRoleByUserName(transaction, username);
			transaction.commit();
			return role;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}
}
