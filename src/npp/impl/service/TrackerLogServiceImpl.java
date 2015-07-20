package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import npp.dto.TrackerLogDto;
import npp.spec.dao.TrackerLogDao;
import npp.spec.dao.Transaction;
import npp.spec.service.TrackerLogService;

public class TrackerLogServiceImpl implements TrackerLogService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1528029305275870761L;
	private Transaction transaction;
	private TrackerLogDao dao;

	@Inject
	public TrackerLogServiceImpl(Transaction transaction, TrackerLogDao dao) {
		this.transaction = transaction;
		this.dao = dao;
	}

	@Override
	public List<TrackerLogDto> findByTrackerSeq(
			int trackerSeq) throws IOException {
		try {
			transaction.begin();
			List<TrackerLogDto> list = dao.findByTrackerSeq(transaction, trackerSeq);
			transaction.commit();
			return list;
		} catch (IOException e) {
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void add(TrackerLogDto dto) throws IOException {
		try {
			transaction.begin();
			dao.add(transaction, dto);
			transaction.commit();
		} catch (IOException e) {
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void update(TrackerLogDto dto) throws IOException {
		try {
			transaction.begin();
			dao.update(transaction, dto);
			transaction.commit();
		} catch (IOException e) {
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void delete(int seq) throws IOException {
		try {
			transaction.begin();
			dao.delete(transaction, seq);
			transaction.commit();
		} catch (IOException e) {
			transaction.rollback();
			throw e;
		}
	}
}
