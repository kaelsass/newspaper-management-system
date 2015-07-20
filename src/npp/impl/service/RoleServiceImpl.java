package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.dto.RoleDto;
import npp.spec.dao.RoleDao;
import npp.spec.dao.Transaction;
import npp.spec.service.RoleService;


public class RoleServiceImpl implements RoleService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 4822474486634242542L;
	private Transaction transaction;
	private RoleDao roleDao;

	@Inject
	public RoleServiceImpl(Transaction transaction,RoleDao roleDao){
		this.transaction = transaction;
		this.roleDao = roleDao;
	}
	@Override
	public List<RoleDto> getAllList() throws IOException {
		try{
			transaction.begin();
			List<RoleDto> roleList = roleDao.getAllList(transaction);
			transaction.commit();
			return roleList;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}
}
