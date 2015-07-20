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

import npp.dto.TrackerDto;
import npp.dto.TrackerReviewerDto;
import npp.dto.WorkInfoDto;
import npp.spec.dao.TrackerDao;
import npp.spec.dao.TrackerReviewerDao;
import npp.spec.dao.Transaction;
import npp.spec.dao.WorkInfoDao;
import npp.utils.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Stateless
public class TrackerDaoImpl implements TrackerDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private WorkInfoDao workInfoDao;
	private TrackerReviewerDao trackerReviewerDao;

	@Inject
	public TrackerDaoImpl(WorkInfoDao workInfoDao, TrackerReviewerDao trackerReviewerDao)
	{
		this.workInfoDao = workInfoDao;
		this.trackerReviewerDao = trackerReviewerDao;
	}

	@Override
	public List<TrackerDto> getAllList(Transaction transaction) throws IOException {
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_ALL);
			resultSet = prepareStatement.executeQuery();

			List<TrackerDto> items = new ArrayList<TrackerDto>();
			while (resultSet.next()) {

				TrackerDto dto = makeDto(transaction, resultSet);
				items.add(dto);
			}
			return items;
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

	private TrackerDto makeDto(Transaction transaction, ResultSet resultSet) throws SQLException, IOException {
		int seq = resultSet.getInt(1);
		String name = resultSet.getString(2);
		String employeeID = resultSet.getString(3);
		String addDateStr = resultSet.getString(4);
		String modDateStr = resultSet.getString(5);

		Date addDate = null;
		try {
			addDate = DateUtil.getDateWithTimeFormatInstance().parse(addDateStr);
		} catch (ParseException e) {
			addDate = null;
		}
		Date modDate = null;
		try {
			modDate = DateUtil.getDateWithTimeFormatInstance().parse(modDateStr);
		} catch (ParseException e) {
			modDate = null;
		}

		WorkInfoDto employee = workInfoDao.findByID(transaction, employeeID);
		ArrayList<WorkInfoDto> reviewers = new ArrayList<WorkInfoDto>();
		List<TrackerReviewerDto> list = trackerReviewerDao.findByTrackerSeq(transaction, seq);
		for(TrackerReviewerDto dto : list)
		{
			reviewers.add(workInfoDao.findByID(transaction, dto.getEmployeeID()));
		}

		TrackerDto dto = new TrackerDto(seq, name, employee, reviewers, addDate, modDate);
		return dto;
	}


	@Override
	public TrackerDto findBySeq(Transaction transaction, int seq) throws IOException {
		TrackerDto dto = null;

		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_BY_SEQ);
			prepareStatement.setInt(1, seq);
			resultSet = prepareStatement.executeQuery();

			if(resultSet.next()) {
				dto = makeDto(transaction, resultSet);
			}
			return dto;
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
	public void add(Transaction transaction,
			TrackerDto dto) throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setString(1, dto.getName());
			dto.getEmployee().setId(workInfoDao.findByName(transaction, dto.getEmployee().getName()).getId());
			prepareStatement.setString(2, dto.getEmployee().getId());

			prepareStatement.setString(3, DateUtil.getDateWithTimeFormatInstance().format(dto.getAddDate()));
			prepareStatement.setString(4, DateUtil.getDateWithTimeFormatInstance().format(dto.getModDate()));
			prepareStatement.executeUpdate();

			int seq = getCurrentSeq(transaction);
			for(WorkInfoDto employee : dto.getReviewers())
			{
				TrackerReviewerDto trd = new TrackerReviewerDto();
				trd.setTrackerSeq(seq);
				trd.setEmployeeID(employee.getId());
				trackerReviewerDao.add(transaction, trd);
			}
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

	private int getCurrentSeq(Transaction transaction) throws IOException {
		int seq = 0;

		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_NEW_INSERT);
			resultSet = prepareStatement.executeQuery();

			if(resultSet.next()) {
				seq = resultSet.getInt(1);
			}
			return seq;
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
	public void update(Transaction transaction,
			TrackerDto dto) throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(UPDATE);
			prepareStatement.setString(1, dto.getName());
			dto.getEmployee().setId(workInfoDao.findByName(transaction, dto.getEmployee().getName()).getId());
			prepareStatement.setString(2, dto.getEmployee().getId());
			prepareStatement.setString(3, DateUtil.getDateWithTimeFormatInstance().format(dto.getAddDate()));
			prepareStatement.setString(4, DateUtil.getDateWithTimeFormatInstance().format(dto.getModDate()));
			prepareStatement.setInt(5, dto.getSeq());
			prepareStatement.executeUpdate();

			trackerReviewerDao.delete(transaction, dto.getSeq());
			for(WorkInfoDto employee : dto.getReviewers())
			{
				TrackerReviewerDto trd = new TrackerReviewerDto();
				trd.setTrackerSeq(dto.getSeq());
				trd.setEmployeeID(employee.getId());
				trackerReviewerDao.add(transaction, trd);
			}
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
	public void delete(Transaction transaction, int seq)
			throws IOException {
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

	private static final String GET_ALL = "select seq, name, employee_id, add_date, mod_date from S_TRACKER order by employee_id";
	private static final String GET_NEW_INSERT = "select max(seq) from S_TRACKER";
	private static final String GET_BY_SEQ = "select seq, name, employee_id, add_date, mod_date from S_TRACKER where seq = ?";
	private static final String INSERT = "insert into S_TRACKER (name, employee_id, add_date, mod_date) values ( ?, ?, ?, ?)";
	private static final String UPDATE = "update S_TRACKER set name = ?, employee_id = ?, add_date = ?, mod_date = ? where seq = ?";
	private static final String DELETE = "delete from S_TRACKER where seq = ?";


}
