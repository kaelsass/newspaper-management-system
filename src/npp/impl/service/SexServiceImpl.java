package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.dto.SexDto;
import npp.spec.dao.SexDao;
import npp.spec.dao.Transaction;
import npp.spec.service.SexService;


public class SexServiceImpl implements SexService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1393209958287351065L;
	/**
	 *
	 */
	private Transaction transaction;
	private SexDao sexDao;

	@Inject
	public SexServiceImpl(Transaction transaction,SexDao sexDao){
		this.transaction = transaction;
		this.sexDao = sexDao;
	}
	@Override
	public List<SexDto> getAllList() throws IOException {
		try{
			transaction.begin();
			List<SexDto> sexList = sexDao.getAllList(transaction);
			transaction.commit();
			return sexList;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}
	@Override
	public SexDto findSex(String name) throws IOException {
		try{
			transaction.begin();
			SexDto sexDto = sexDao.findSex(transaction, name);
			transaction.commit();
			return sexDto;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}
//	@Override
//	public int findSeqByName(String name) throws IOException {
//		try{
//			transaction.begin();
//			SexDto sexDto = sexDao.findSexByName(transaction, name);
//			transaction.commit();
//			if(sexDto == null)
//				return 0;
//			return sexDto.getSeq();
//		}catch(IOException e){
//			transaction.rollback();
//			throw e;
//		}
//	}
}
