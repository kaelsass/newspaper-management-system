package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.condition.DynamicQuery;
import npp.dto.OccupationDto;
import npp.spec.dao.OccupationDao;
import npp.spec.dao.Transaction;
import npp.spec.service.OccupationService;


public class OccupationServiceImpl implements OccupationService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1528029305275870761L;
	private Transaction transaction;
	private OccupationDao dao;

	@Inject
	public OccupationServiceImpl(Transaction transaction, OccupationDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public OccupationDto findBySeq(int seq) throws IOException{
		try{
			transaction.begin();
			OccupationDto dto = dao.findBySeq(transaction, seq);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public List<OccupationDto> getAllList(DynamicQuery query) throws IOException{
		try{
			transaction.begin();
			List<OccupationDto> List = dao.getAllList(transaction, query);
			transaction.commit();
			return List;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(OccupationDto dto) throws IOException {
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
	public void update(OccupationDto dto) throws IOException {
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
	public OccupationDto findByName(String name) throws IOException {
		try{
			transaction.begin();
			OccupationDto dto = dao.findByName(transaction, name);
			transaction.commit();
			return dto;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

}
