package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.dto.SubjectDto;
import npp.spec.dao.SubjectDao;
import npp.spec.dao.Transaction;
import npp.spec.service.SubjectService;


public class SubjectServiceImpl implements SubjectService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1069655030868538783L;
	private Transaction transaction;
	private SubjectDao dao;

	@Inject
	public SubjectServiceImpl(Transaction transaction, SubjectDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public SubjectDto findBySeq(int seq) throws IOException{
		try{
			transaction.begin();
			SubjectDto dto = dao.findBySeq(transaction, seq);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public List<SubjectDto> getAllList() throws IOException{
		try{
			transaction.begin();
			List<SubjectDto> list = dao.getAllList(transaction);
			transaction.commit();
			return list;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(SubjectDto dto) throws IOException {
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
	public void update(SubjectDto dto) throws IOException {
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
