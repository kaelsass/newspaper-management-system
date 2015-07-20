package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.condition.DynamicQuery;
import npp.dto.PublicationDateDto;
import npp.spec.dao.PdateDao;
import npp.spec.dao.Transaction;
import npp.spec.service.PdateService;


public class PdateServiceImpl implements PdateService, Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -5208973850845676751L;
	private Transaction transaction;
	private PdateDao dao;

	@Inject
	public PdateServiceImpl(Transaction transaction, PdateDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public List<PublicationDateDto> getAllList(DynamicQuery query) throws IOException{
		try{
			transaction.begin();
			List<PublicationDateDto> list = dao.getAllList(transaction, query);
			transaction.commit();
			return list;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}
}
