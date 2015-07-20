package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.dto.TrackerReviewerDto;
import npp.spec.dao.TrackerReviewerDao;
import npp.spec.dao.Transaction;
import npp.spec.service.TrackerReviewerService;


public class TrackerReviewerServiceImpl implements TrackerReviewerService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1528029305275870761L;
	private Transaction transaction;
	private TrackerReviewerDao dao;

	@Inject
	public TrackerReviewerServiceImpl(Transaction transaction, TrackerReviewerDao dao){
		this.transaction =  transaction;
		this.dao = dao;
	}

	@Override
	public List<TrackerReviewerDto> findByTrackerSeq(int trackerSeq) throws IOException{
		try{
			transaction.begin();
			List<TrackerReviewerDto> dto = dao.findByTrackerSeq(transaction, trackerSeq);
			transaction.commit();
			return dto;
		}catch (IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(TrackerReviewerDto dto) throws IOException {
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
