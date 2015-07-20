package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.dto.PurposeDto;
import npp.spec.dao.Transaction;
import npp.spec.service.PurposeDao;
import npp.spec.service.PurposeService;


public class PurposeServiceImpl implements PurposeService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 4489515223928182709L;
	private Transaction transaction;
	private PurposeDao dao;

	@Inject
	public PurposeServiceImpl(Transaction transaction, PurposeDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public PurposeDto findBySeq(int seq) throws IOException{
		try{
			transaction.begin();
			PurposeDto dto = dao.findBySeq(transaction, seq);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public List<PurposeDto> getAllList() throws IOException{
		try{
			transaction.begin();
			List<PurposeDto> list = dao.getAllList(transaction);
			transaction.commit();
			return list;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(PurposeDto dto) throws IOException {
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
	public void update(PurposeDto dto) throws IOException {
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
