package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.dto.JobTitleDto;
import npp.spec.dao.JobTitleDao;
import npp.spec.dao.Transaction;
import npp.spec.service.JobTitleService;


public class JobTitleServiceImpl implements JobTitleService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1986366504228477129L;
	private Transaction transaction;
	private JobTitleDao dao;

	@Inject
	public JobTitleServiceImpl(Transaction transaction, JobTitleDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public JobTitleDto findBySeq(int seq) throws IOException{
		try{
			transaction.begin();
			JobTitleDto dto = dao.findBySeq(transaction, seq);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public List<JobTitleDto> getAllList() throws IOException{
		try{

			transaction.begin();
			List<JobTitleDto> list = dao.getAllList(transaction);
			transaction.commit();
			return list;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(JobTitleDto dto) throws IOException {
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
	public void update(JobTitleDto dto) throws IOException {
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
	public JobTitleDto findByName(String name) throws IOException {
		try{
			transaction.begin();
			JobTitleDto dto = dao.findByName(transaction, name);
			transaction.commit();
			return dto;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

}
