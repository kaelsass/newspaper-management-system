package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.condition.DynamicQuery;
import npp.dto.AdDto;
import npp.spec.dao.AdDao;
import npp.spec.dao.Transaction;
import npp.spec.service.AdService;


public class AdServiceImpl implements AdService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1528029305275870761L;
	private Transaction transaction;
	private AdDao dao;

	@Inject
	public AdServiceImpl(Transaction transaction, AdDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public AdDto findByID(String ID) throws IOException{
		try{
			transaction.begin();
			AdDto dto = dao.findByID(transaction, ID);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public List<AdDto> getAllList(DynamicQuery query) throws IOException{
		try{
			transaction.begin();
			List<AdDto> list = dao.getAllList(transaction, query);
			transaction.commit();
			return list;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(AdDto dto) throws IOException {
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
	public void update(AdDto dto) throws IOException {
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
	public void delete(String id) throws IOException {
		try{
			transaction.begin();
			dao.delete(transaction, id);
			transaction.commit();
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

}
