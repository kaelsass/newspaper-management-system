package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.condition.DynamicQuery;
import npp.dto.NonSubscriberDto;
import npp.spec.dao.NonSubscriberDao;
import npp.spec.dao.Transaction;
import npp.spec.service.NonSubscriberService;


public class NonSubscriberServiceImpl implements NonSubscriberService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1528029305275870761L;
	private Transaction transaction;
	private NonSubscriberDao dao;

	@Inject
	public NonSubscriberServiceImpl(Transaction transaction, NonSubscriberDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public NonSubscriberDto findBySeq(int seq) throws IOException{
		try{
			transaction.begin();
			NonSubscriberDto dto = dao.findBySeq(transaction, seq);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public List<NonSubscriberDto> getAllList(DynamicQuery query) throws IOException{
		try{
			transaction.begin();
			List<NonSubscriberDto> list = dao.getAllList(transaction, query);
			transaction.commit();
			return list;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(NonSubscriberDto dto) throws IOException {
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
	public void update(NonSubscriberDto dto) throws IOException {
		try{
			transaction.begin();
			dao.update(transaction, dto);
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
