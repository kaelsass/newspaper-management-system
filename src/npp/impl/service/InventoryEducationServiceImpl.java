package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.condition.InventoryEducationCondition;
import npp.dto.InventoryDto;
import npp.spec.dao.InventoryEducationDao;
import npp.spec.dao.Transaction;
import npp.spec.service.InventoryEducationService;


public class InventoryEducationServiceImpl implements InventoryEducationService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -2059874612494299149L;
	private Transaction transaction;
	private InventoryEducationDao dao;

	@Inject
	public InventoryEducationServiceImpl(Transaction transaction, InventoryEducationDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public List<InventoryDto> getAllList(InventoryEducationCondition condition) throws IOException{
		try{
			transaction.begin();
			List<InventoryDto> list = dao.getAllList(transaction, condition);
			transaction.commit();
			return list;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

}
