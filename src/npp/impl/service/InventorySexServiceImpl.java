package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.condition.InventorySexCondition;
import npp.dto.InventoryDto;
import npp.spec.dao.InventorySexDao;
import npp.spec.dao.Transaction;
import npp.spec.service.InventorySexService;


public class InventorySexServiceImpl implements InventorySexService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -2059874612494299149L;
	private Transaction transaction;
	private InventorySexDao dao;

	@Inject
	public InventorySexServiceImpl(Transaction transaction, InventorySexDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public List<InventoryDto> getAllList(InventorySexCondition condition) throws IOException{
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
