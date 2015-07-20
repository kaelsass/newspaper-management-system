package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.condition.InventoryAgeCondition;
import npp.dto.InventoryDto;
import npp.spec.dao.InventoryAgeDao;
import npp.spec.dao.Transaction;
import npp.spec.service.InventoryAgeService;


public class InventoryAgeServiceImpl implements InventoryAgeService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -2059874612494299149L;
	private Transaction transaction;
	private InventoryAgeDao dao;

	@Inject
	public InventoryAgeServiceImpl(Transaction transaction, InventoryAgeDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public List<InventoryDto> getAllList(InventoryAgeCondition condition) throws IOException{
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
