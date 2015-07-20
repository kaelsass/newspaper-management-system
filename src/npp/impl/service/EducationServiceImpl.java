package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.condition.DynamicQuery;
import npp.dto.EducationDto;
import npp.spec.dao.EducationDao;
import npp.spec.dao.Transaction;
import npp.spec.service.EducationService;


public class EducationServiceImpl implements EducationService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1528029305275870761L;
	private Transaction transaction;
	private EducationDao dao;

	@Inject
	public EducationServiceImpl(Transaction transaction, EducationDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public EducationDto findBySeq(int seq) throws IOException{
		try{
			transaction.begin();
			EducationDto dto = dao.findBySeq(transaction, seq);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public List<EducationDto> getAllList(DynamicQuery query) throws IOException{
		try{
			transaction.begin();
			List<EducationDto> List = dao.getAllList(transaction, query);
			transaction.commit();
			return List;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(EducationDto dto) throws IOException {
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
	public void update(EducationDto dto) throws IOException {
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
	public EducationDto findByName(String name) throws IOException {
		try{
			transaction.begin();
			EducationDto dto = dao.findByName(transaction, name);
			transaction.commit();
			return dto;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

}
