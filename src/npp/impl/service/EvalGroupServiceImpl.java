package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.dto.EvalGroupDto;
import npp.spec.dao.EvalGroupDao;
import npp.spec.dao.Transaction;
import npp.spec.service.EvalGroupService;


public class EvalGroupServiceImpl implements EvalGroupService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1528029305275870761L;
	private Transaction transaction;
	private EvalGroupDao dao;

	@Inject
	public EvalGroupServiceImpl(Transaction transaction, EvalGroupDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public EvalGroupDto findBySeq(int seq) throws IOException{
		try{
			transaction.begin();
			EvalGroupDto dto = dao.findBySeq(transaction, seq);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public List<EvalGroupDto> getAllList() throws IOException{
		try{
			transaction.begin();
			List<EvalGroupDto> List = dao.getAllList(transaction);
			transaction.commit();
			return List;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(EvalGroupDto dto) throws IOException {
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
	public void update(EvalGroupDto dto) throws IOException {
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
	public EvalGroupDto findByName(String name) throws IOException {
		try{
			transaction.begin();
			EvalGroupDto dto = dao.findByName(transaction, name);
			transaction.commit();
			return dto;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

}
