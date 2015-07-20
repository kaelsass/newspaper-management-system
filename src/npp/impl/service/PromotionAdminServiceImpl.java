package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.dto.PromotionAdminDto;
import npp.spec.dao.PromotionAdminDao;
import npp.spec.dao.Transaction;
import npp.spec.service.PromotionAdminService;


public class PromotionAdminServiceImpl implements PromotionAdminService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1528029305275870761L;
	private Transaction transaction;
	private PromotionAdminDao dao;

	@Inject
	public PromotionAdminServiceImpl(Transaction transaction, PromotionAdminDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public List<PromotionAdminDto> findByPromotionSeq(int promotionSeq) throws IOException{
		try{
			transaction.begin();
			List<PromotionAdminDto> dto = dao.findByPromotionSeq(transaction, promotionSeq);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}
	@Override
	public List<PromotionAdminDto> findByEmployeeID(String id) throws IOException{
		try{
			transaction.begin();
			List<PromotionAdminDto> dto = dao.findByEmployeeID(transaction, id);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(PromotionAdminDto dto) throws IOException {
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
	public void delete(int trackerSeq) throws IOException {
		try{
			transaction.begin();
			dao.delete(transaction, trackerSeq);
			transaction.commit();
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}


}
