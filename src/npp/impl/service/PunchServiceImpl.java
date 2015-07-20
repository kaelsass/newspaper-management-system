package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;

import npp.condition.DynamicQuery;
import npp.dto.PunchDto;
import npp.spec.dao.PunchDao;
import npp.spec.dao.Transaction;
import npp.spec.service.PunchService;


public class PunchServiceImpl implements PunchService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 6618357296233537217L;
	private Transaction transaction;
	private PunchDao dao;

	@Inject
	public PunchServiceImpl(Transaction transaction, PunchDao s_userDao){
		this.transaction =  transaction;
		this.dao = s_userDao;
	}

	@Override
	public PunchDto findBySeq(int seq) throws IOException {
		try{
			transaction.begin();
			PunchDto dto = dao.findBySeq(transaction, seq);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public List<PunchDto> getAllList(DynamicQuery query) throws IOException{
		try{
			transaction.begin();
			List<PunchDto> list = dao.getAllList(transaction, query);
			transaction.commit();
			return list;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	@RolesAllowed("admin")
	public void add(PunchDto dto) throws IOException {
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
	@RolesAllowed("admin")
	public void update(PunchDto dto) throws IOException {
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
	@RolesAllowed("admin")
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
	public List<PunchDto> getUnfinishedList() throws IOException {
		try{
			transaction.begin();
			List<PunchDto> list = dao.getUnfinishedList(transaction);
			transaction.commit();
			return list;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

}
