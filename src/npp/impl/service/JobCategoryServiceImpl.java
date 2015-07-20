package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.dto.JobCategoryDto;
import npp.spec.dao.JobCategoryDao;
import npp.spec.dao.Transaction;
import npp.spec.service.JobCategoryService;


public class JobCategoryServiceImpl implements JobCategoryService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1528029305275870761L;
	private Transaction transaction;
	private JobCategoryDao dao;

	@Inject
	public JobCategoryServiceImpl(Transaction transaction, JobCategoryDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public JobCategoryDto findBySeq(int seq) throws IOException{
		try{
			transaction.begin();
			JobCategoryDto dto = dao.findBySeq(transaction, seq);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public List<JobCategoryDto> getAllList() throws IOException{
		try{
			transaction.begin();
			List<JobCategoryDto> list = dao.getAllList(transaction);
			transaction.commit();
			return list;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(JobCategoryDto dto) throws IOException {
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
	public void update(JobCategoryDto dto) throws IOException {
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
	public JobCategoryDto findByName(String name) throws IOException {
		try{
			transaction.begin();
			JobCategoryDto dto = dao.findByName(transaction, name);
			transaction.commit();
			return dto;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

}
