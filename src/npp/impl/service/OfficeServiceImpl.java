package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.dto.OfficeDto;
import npp.spec.dao.OfficeDao;
import npp.spec.dao.Transaction;
import npp.spec.service.OfficeService;


public class OfficeServiceImpl implements OfficeService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 5622083316246367666L;
	private Transaction transaction;
	private OfficeDao dao;

	@Inject
	public OfficeServiceImpl(Transaction transaction, OfficeDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public OfficeDto findBySeq(int seq) throws IOException{
		try{
			transaction.begin();
			OfficeDto dto = dao.findBySeq(transaction, seq);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public List<OfficeDto> getAllList() throws IOException{
		try{
			transaction.begin();
			List<OfficeDto> list = dao.getAllList(transaction);
			transaction.commit();
			return list;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(OfficeDto dto) throws IOException {
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
	public void update(OfficeDto dto) throws IOException {
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

	@Override
	public OfficeDto findByName(String name) throws IOException {
		try{
			transaction.begin();
			OfficeDto dto = dao.findByName(transaction, name);
			transaction.commit();
			return dto;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

}
