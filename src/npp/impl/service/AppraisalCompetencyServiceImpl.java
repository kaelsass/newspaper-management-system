package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.dto.AcDto;
import npp.spec.dao.AppraisalCompetencyDao;
import npp.spec.dao.Transaction;
import npp.spec.service.AppraisalCompetencyService;


public class AppraisalCompetencyServiceImpl implements AppraisalCompetencyService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1528029305275870761L;
	private Transaction transaction;
	private AppraisalCompetencyDao dao;

	@Inject
	public AppraisalCompetencyServiceImpl(Transaction transaction, AppraisalCompetencyDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public List<AcDto> getAllList(int appraisalSeq) throws IOException{
		try{
			transaction.begin();
			List<AcDto> list = dao.getAllList(transaction, appraisalSeq);
			transaction.commit();
			return list;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}


	@Override
	public void add(AcDto dto) throws IOException {
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
