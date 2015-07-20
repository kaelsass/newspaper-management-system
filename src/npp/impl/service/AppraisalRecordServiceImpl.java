package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.dto.AppraisalRecord;
import npp.spec.dao.AppraisalRecordDao;
import npp.spec.dao.Transaction;
import npp.spec.service.AppraisalRecordService;


public class AppraisalRecordServiceImpl implements AppraisalRecordService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1528029305275870761L;
	private Transaction transaction;
	private AppraisalRecordDao dao;

	@Inject
	public AppraisalRecordServiceImpl(Transaction transaction, AppraisalRecordDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}
	@Override
	public List<AppraisalRecord> getAllList(int appraisalSeq) throws IOException{
		try{
			transaction.begin();
			List<AppraisalRecord> list = dao.getAllList(transaction, appraisalSeq);
			transaction.commit();
			return list;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}
}
