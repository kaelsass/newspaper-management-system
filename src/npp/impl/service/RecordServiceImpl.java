package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.condition.DynamicQuery;
import npp.dto.RecordDto;
import npp.spec.dao.RecordDao;
import npp.spec.dao.Transaction;
import npp.spec.service.RecordService;


public class RecordServiceImpl implements RecordService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1528029305275870761L;
	private Transaction transaction;
	private RecordDao dao;

	@Inject
	public RecordServiceImpl(Transaction transaction, RecordDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public List<RecordDto> getAllList(DynamicQuery query) throws IOException{
		try{
			transaction.begin();
			List<RecordDto> list = dao.getAllList(transaction, query);
			transaction.commit();
			return list;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}


	@Override
	public void add(RecordDto dto) throws IOException {
		try{
			transaction.begin();
			dao.add(transaction, dto);
			transaction.commit();
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void delete(int seq) throws IOException {
		try{
			transaction.begin();
			dao.delete(transaction, seq);
			transaction.commit();
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

}
