package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.condition.InventoryCondition;
import npp.dto.InventoryDto;
import npp.spec.dao.InventoryDao;
import npp.spec.dao.Transaction;
import npp.spec.service.InventoryService;


public class InventoryServiceImpl implements InventoryService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -2059874612494299149L;
	private Transaction transaction;
	private InventoryDao dao;

	@Inject
	public InventoryServiceImpl(Transaction transaction, InventoryDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public List<InventoryDto> getAllList(InventoryCondition condition) throws IOException{
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
