package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.condition.DynamicQuery;
import npp.dto.SectionDto;
import npp.spec.dao.SectionDao;
import npp.spec.dao.Transaction;
import npp.spec.service.SectionService;


public class SectionServiceImpl implements SectionService, Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -5208973850845676751L;
	private Transaction transaction;
	private SectionDao dao;

	@Inject
	public SectionServiceImpl(Transaction transaction, SectionDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public SectionDto findBySeq(int seq) throws IOException{
		try{
			transaction.begin();
			SectionDto dto = dao.findBySeq(transaction, seq);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public List<SectionDto> getAllList(DynamicQuery query) throws IOException{
		try{
			transaction.begin();
			List<SectionDto> list = dao.getAllList(transaction, query);
			transaction.commit();
			return list;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(SectionDto dto) throws IOException {
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
	public void update(SectionDto dto) throws IOException {
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
