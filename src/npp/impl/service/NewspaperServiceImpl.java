package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.condition.DynamicQuery;
import npp.dto.NewspaperDto;
import npp.spec.dao.NewspaperDao;
import npp.spec.dao.Transaction;
import npp.spec.service.NewspaperService;


public class NewspaperServiceImpl implements NewspaperService, Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -5208973850845676751L;
	private Transaction transaction;
	private NewspaperDao dao;

	@Inject
	public NewspaperServiceImpl(Transaction transaction, NewspaperDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public NewspaperDto findBySeq(int seq) throws IOException{
		try{
			transaction.begin();
			NewspaperDto dto = dao.findBySeq(transaction, seq);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public List<NewspaperDto> getAllList(DynamicQuery query) throws IOException{
		try{
			transaction.begin();
			List<NewspaperDto> list = dao.getAllList(transaction, query);
			transaction.commit();
			return list;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(NewspaperDto dto) throws IOException {
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
	public void update(NewspaperDto dto) throws IOException {
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
