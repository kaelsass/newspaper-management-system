package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.condition.DynamicQuery;
import npp.dto.AuthorDto;
import npp.spec.dao.AuthorDao;
import npp.spec.dao.Transaction;
import npp.spec.service.AuthorService;


public class AuthorServiceImpl implements AuthorService, Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -5208973850845676751L;
	private Transaction transaction;
	private AuthorDao dao;

	@Inject
	public AuthorServiceImpl(Transaction transaction, AuthorDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public AuthorDto findBySeq(int seq) throws IOException{
		try{
			transaction.begin();
			AuthorDto dto = dao.findBySeq(transaction, seq);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public List<AuthorDto> getAllList(DynamicQuery query) throws IOException{
		try{
			transaction.begin();
			List<AuthorDto> list = dao.getAllList(transaction, query);
			transaction.commit();
			return list;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(AuthorDto dto) throws IOException {
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
	public void update(AuthorDto dto) throws IOException {
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
	public AuthorDto findByName(String name) throws IOException {
		try{
			transaction.begin();
			AuthorDto dto = dao.findByName(transaction, name);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

}
