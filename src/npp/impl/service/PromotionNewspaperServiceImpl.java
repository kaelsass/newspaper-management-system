package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.dto.PromotionNewspaperDto;
import npp.spec.dao.PromotionNewspaperDao;
import npp.spec.dao.Transaction;
import npp.spec.service.PromotionNewspaperService;


public class PromotionNewspaperServiceImpl implements PromotionNewspaperService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1528029305275870761L;
	private Transaction transaction;
	private PromotionNewspaperDao dao;

	@Inject
	public PromotionNewspaperServiceImpl(Transaction transaction, PromotionNewspaperDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public List<PromotionNewspaperDto> findByPromotionSeq(int promotionSeq) throws IOException{
		try{
			transaction.begin();
			List<PromotionNewspaperDto> dto = dao.findByPromotionSeq(transaction, promotionSeq);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}
	@Override
	public List<PromotionNewspaperDto> findByNewspaperSeq(int newspaperSeq) throws IOException{
		try{
			transaction.begin();
			List<PromotionNewspaperDto> dto = dao.findByNewspaperSeq(transaction, newspaperSeq);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(PromotionNewspaperDto dto) throws IOException {
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
	public void delete(int promotionSeq) throws IOException {
		try{
			transaction.begin();
			dao.delete(transaction, promotionSeq);
			transaction.commit();
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

}
