package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.condition.DynamicQuery;
import npp.dto.GoalDto;
import npp.spec.dao.GoalDao;
import npp.spec.dao.Transaction;
import npp.spec.service.GoalService;


public class GoalServiceImpl implements GoalService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1528029305275870761L;
	private Transaction transaction;
	private GoalDao dao;

	@Inject
	public GoalServiceImpl(Transaction transaction, GoalDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public GoalDto findBySeq(int seq) throws IOException{
		try{
			transaction.begin();
			GoalDto dto = dao.findBySeq(transaction, seq);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public List<GoalDto> getAllList(DynamicQuery query) throws IOException{
		try{
			transaction.begin();
			List<GoalDto> List = dao.getAllList(transaction, query);
			transaction.commit();
			return List;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(GoalDto dto) throws IOException {
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
	public void update(GoalDto dto) throws IOException {
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
	public GoalDto findByName(String name) throws IOException {
		try{
			transaction.begin();
			GoalDto dto = dao.findByName(transaction, name);
			transaction.commit();
			return dto;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

}
