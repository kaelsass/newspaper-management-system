package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.dto.StatusDto;
import npp.spec.dao.GoalTypeDao;
import npp.spec.dao.Transaction;
import npp.spec.service.GoalTypeService;


public class GoalTypeServiceImpl implements GoalTypeService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1528029305275870761L;
	private Transaction transaction;
	private GoalTypeDao dao;

	@Inject
	public GoalTypeServiceImpl(Transaction transaction, GoalTypeDao dao){
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
			List<StatusDto> List = dao.getAllList(transaction);
			transaction.commit();
			return List;
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

	@Override
	public StatusDto findByName(String name) throws IOException {
		try{
			transaction.begin();
			StatusDto dto = dao.findByName(transaction, name);
			transaction.commit();
			return dto;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

}
