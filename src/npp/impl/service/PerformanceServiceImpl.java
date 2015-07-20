package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.dto.PerformanceDto;
import npp.spec.dao.PerformanceDao;
import npp.spec.dao.Transaction;
import npp.spec.service.PerformanceService;

public class PerformanceServiceImpl implements PerformanceService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1528029305275870761L;
	private Transaction transaction;
	private PerformanceDao dao;

	@Inject
	public PerformanceServiceImpl(Transaction transaction, PerformanceDao dao) {
		this.transaction = transaction;
		this.dao = dao;
	}

	@Override
	public List<PerformanceDto> getAllList() throws IOException{
		try{
			transaction.begin();
			List<PerformanceDto> list = dao.getAllList(transaction);
			transaction.commit();
			return list;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}
}
