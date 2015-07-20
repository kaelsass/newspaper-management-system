package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.dto.CompetencyDto;
import npp.spec.dao.CompetencyDao;
import npp.spec.dao.Transaction;
import npp.spec.service.CompetencyService;


public class CompetencyServiceImpl implements CompetencyService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1986366504228477129L;
	private Transaction transaction;
	private CompetencyDao dao;

	@Inject
	public CompetencyServiceImpl(Transaction transaction, CompetencyDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public CompetencyDto findBySeq(int seq) throws IOException{
		try{
			transaction.begin();
			CompetencyDto dto = dao.findBySeq(transaction, seq);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public List<CompetencyDto> getAllList() throws IOException{
		try{

			transaction.begin();
			List<CompetencyDto> list = dao.getAllList(transaction);
			transaction.commit();
			return list;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(CompetencyDto dto) throws IOException {
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
	public void update(CompetencyDto dto) throws IOException {
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
	public CompetencyDto findByName(String name) throws IOException {
		try{
			transaction.begin();
			CompetencyDto dto = dao.findByName(transaction, name);
			transaction.commit();
			return dto;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

}
