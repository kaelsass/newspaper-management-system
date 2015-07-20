package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.dto.StatusDto;
import npp.spec.dao.Transaction;
import npp.spec.dao.TypeDao;
import npp.spec.service.TypeService;


public class TypeServiceImpl implements TypeService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1069655030868538783L;
	private Transaction transaction;
	private TypeDao dao;

	@Inject
	public TypeServiceImpl(Transaction transaction, TypeDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public StatusDto findBySeq(int seq) throws IOException{
		try{
			transaction.begin();
			StatusDto dto = dao.findBySeq(transaction, seq);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public List<StatusDto> getAllList() throws IOException{
		try{
			transaction.begin();
			List<StatusDto> list = dao.getAllList(transaction);
			transaction.commit();
			return list;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(StatusDto dto) throws IOException {
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
	public void update(StatusDto dto) throws IOException {
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
