package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.condition.InventoryOccupationCondition;
import npp.dto.InventoryDto;
import npp.spec.dao.InventoryOccupationDao;
import npp.spec.dao.Transaction;
import npp.spec.service.InventoryOccupationService;


public class InventoryOccupationServiceImpl implements InventoryOccupationService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -2059874612494299149L;
	private Transaction transaction;
	private InventoryOccupationDao dao;

	@Inject
	public InventoryOccupationServiceImpl(Transaction transaction, InventoryOccupationDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public List<InventoryDto> getAllList(InventoryOccupationCondition condition) throws IOException{
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
