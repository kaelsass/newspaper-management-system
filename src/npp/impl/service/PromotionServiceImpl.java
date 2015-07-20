package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.condition.DynamicQuery;
import npp.dto.PromotionDto;
import npp.spec.dao.PromotionDao;
import npp.spec.dao.Transaction;
import npp.spec.service.PromotionService;


public class PromotionServiceImpl implements PromotionService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1528029305275870761L;
	private Transaction transaction;
	private PromotionDao dao;

	@Inject
	public PromotionServiceImpl(Transaction transaction, PromotionDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public PromotionDto findBySeq(int seq) throws IOException{
		try{
			transaction.begin();
			PromotionDto dto = dao.findBySeq(transaction, seq);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public int getNewInsertedSeq() throws IOException{
		try{
			transaction.begin();
			int seq = dao.getNewInsertedSeq(transaction);
			transaction.commit();
			return seq;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public List<PromotionDto> getAllEditList(DynamicQuery query) throws IOException{
		try{
			transaction.begin();
			List<PromotionDto> list = dao.getAllEditList(transaction, query);
			transaction.commit();
			return list;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}
	@Override
	public List<PromotionDto> getAllList(DynamicQuery query) throws IOException{
		try{
			transaction.begin();
			List<PromotionDto> list = dao.getAllList(transaction, query);
			transaction.commit();
			return list;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(PromotionDto dto) throws IOException {
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
	public void update(PromotionDto dto) throws IOException {
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
