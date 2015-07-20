package npp.impl.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import npp.dto.PerformanceDto;
import npp.dto.TrackerDto;
import npp.dto.TrackerLogDto;
import npp.dto.WorkInfoDto;
import npp.spec.dao.PerformanceDao;
import npp.spec.dao.TrackerDao;
import npp.spec.dao.TrackerLogDao;
import npp.spec.dao.Transaction;
import npp.spec.dao.WorkInfoDao;
import npp.utils.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Stateless
public class TrackerLogDaoImpl implements TrackerLogDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private TrackerDao trackerDao;
	private WorkInfoDao workInfoDao;
	private PerformanceDao performanceDao;

	@Inject
	public TrackerLogDaoImpl(TrackerDao trackerDao, WorkInfoDao workInfoDao,
			PerformanceDao performanceDao) {
		this.trackerDao = trackerDao;
		this.workInfoDao = workInfoDao;
		this.performanceDao = performanceDao;
	}

	private TrackerLogDto makeDto(Transaction transaction, ResultSet resultSet)
			throws SQLException, IOException {
		int seq = resultSet.getInt(1);
		int trackerSeq = resultSet.getInt(2);
		String employeeID = resultSet.getString(3);
		String log = resultSet.getString(4);
		String comments = resultSet.getString(5);
		int performanceSeq = resultSet.getInt(6);
		String addDateStr = resultSet.getString(7);
		String modDateStr = resultSet.getString(8);
		Date addDate = null;
		if (addDateStr != null) {
			try {
				addDate = DateUtil.getDateWithTimeFormatInstance().parse(
						addDateStr);
			} catch (ParseException e) {
				addDate = null;
			}
		}

		Date modDate = null;
		if (modDateStr != null) {
			try {
				modDate = DateUtil.getDateWithTimeFormatInstance().parse(
						modDateStr);
			} catch (ParseException e) {
				modDate = null;
			}
		}

		TrackerDto tracker = trackerDao.findBySeq(transaction, trackerSeq);
		WorkInfoDto reviewer = workInfoDao.findByID(transaction, employeeID);
		PerformanceDto performance = performanceDao.findBySeq(transaction,
				performanceSeq);
		TrackerLogDto dto = new TrackerLogDto(seq, tracker, reviewer, log,
				comments, performance, addDate, modDate);
		return dto;
	}

	@Override
	public List<TrackerLogDto> findByTrackerSeq(Transaction transaction,
			int trackerSeq) throws IOException {
		TrackerLogDto dto = null;

		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_BY_TRACKER_SEQ);
			prepareStatement.setInt(1, trackerSeq);
			resultSet = prepareStatement.executeQuery();
			ArrayList<TrackerLogDto> list = new ArrayList<TrackerLogDto>();
			while (resultSet.next()) {
				dto = makeDto(transaction, resultSet);
				list.add(dto);
			}
			return list;
		} catch (SQLException e) {
			throw new IOException(e);
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					logger.warn(e.getMessage(), e);
				}
			}

			if (prepareStatement != null) {
				try {
					prepareStatement.close();
				} catch (SQLException e) {
					logger.warn(e.getMessage(), e);
				}
			}
		}
	}

	@Override
	public void add(Transaction transaction, TrackerLogDto dto)
			throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setInt(1, dto.getTracker().getSeq());
			prepareStatement.setString(2, dto.getReviewer().getId());
			prepareStatement.setString(3, dto.getLog());
			prepareStatement.setString(4, dto.getComments());
			prepareStatement.setInt(5, dto.getPerformance().getSeq());
			String addDateStr = "";
			if (dto.getAddDate() != null) {
				addDateStr = DateUtil.getDateWithTimeFormatInstance().format(
						dto.getAddDate());
			}
			prepareStatement.setString(6, addDateStr);
			String modDateStr = "";
			if (dto.getModDate() != null) {
				modDateStr = DateUtil.getDateWithTimeFormatInstance().format(
						dto.getModDate());
			}
			prepareStatement.setString(7, modDateStr);
			prepareStatement.executeUpdate();
		} catch (SQLException e) {
			throw new IOException(e);
		} finally {
			if (prepareStatement != null) {
				try {
					prepareStatement.close();
				} catch (SQLException e) {
					logger.warn(e.getMessage(), e);
				}
			}
		}

	}

	@Override
	public void update(Transaction transaction, TrackerLogDto dto)
			throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(UPDATE);
			prepareStatement.setInt(1, dto.getTracker().getSeq());
			prepareStatement.setString(2, dto.getReviewer().getId());
			prepareStatement.setString(3, dto.getLog());
			prepareStatement.setString(4, dto.getComments());
			prepareStatement.setInt(5, dto.getPerformance().getSeq());
			prepareStatement.setString(6, DateUtil
					.getDateWithTimeFormatInstance().format(dto.getAddDate()));
			prepareStatement.setString(7, DateUtil
					.getDateWithTimeFormatInstance().format(dto.getModDate()));
			prepareStatement.setInt(8, dto.getSeq());
			prepareStatement.executeUpdate();
		} catch (SQLException e) {
			throw new IOException(e);
		} finally {
			if (prepareStatement != null) {
				try {
					prepareStatement.close();
				} catch (SQLException e) {
					logger.warn(e.getMessage(), e);
				}
			}
		}

	}

	@Override
	public void delete(Transaction transaction, int seq) throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(DELETE);
			prepareStatement.setInt(1, seq);
			prepareStatement.executeUpdate();
		} catch (SQLException e) {
			throw new IOException(e);
		} finally {
			if (prepareStatement != null) {
				try {
					prepareStatement.close();
				} catch (SQLException e) {
					logger.warn(e.getMessage(), e);
				}
			}
		}
	}

	private static final String GET_BY_TRACKER_SEQ = "select seq, tracker_seq, employee_id, log, comments, performance_seq, add_date, mod_date from S_TRACKER_LOG where tracker_seq = ?";
	private static final String INSERT = "insert into S_TRACKER_LOG (tracker_seq, employee_id, log, comments, performance_seq, add_date, mod_date) values ( ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "update S_TRACKER_LOG set tracker_seq = ?, employee_id = ?, log = ?, comments = ?, performance_seq = ?, add_date = ?, mod_date = ? where seq = ?";
	private static final String DELETE = "delete from S_TRACKER_LOG where seq = ?";
}
