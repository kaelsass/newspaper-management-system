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

import npp.dto.ActivityDto;
import npp.dto.TaskDto;
import npp.dto.TimesheetDto;
import npp.dto.TimesheetPerWeekDto;
import npp.spec.dao.ActivityDao;
import npp.spec.dao.TaskDao;
import npp.spec.dao.TimesheetDao;
import npp.spec.dao.TimesheetPerWeekDao;
import npp.spec.dao.Transaction;
import npp.utils.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Stateless
public class TimesheetPerWeekDaoImpl implements TimesheetPerWeekDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private TimesheetDao timesheetDao;
	private TaskDao taskDao;
	private ActivityDao activityDao;

	@Inject
	public TimesheetPerWeekDaoImpl(TimesheetDao timesheetDao, TaskDao taskDao,
			ActivityDao activityDao) {
		this.timesheetDao = timesheetDao;
		this.taskDao = taskDao;
		this.activityDao = activityDao;
	}



	@Override
	public List<TimesheetPerWeekDto> findByTimesheetSeq(
			Transaction transaction, int timesheetSeq) throws IOException {
		List<TimesheetPerWeekDto> list = new ArrayList<TimesheetPerWeekDto>();

		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection
					.prepareStatement(GET_BY_TIMESHEET_SEQ);
			prepareStatement.setInt(1, timesheetSeq);
			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				TimesheetPerWeekDto dto = makeDto(transaction, resultSet);
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
	private TimesheetPerWeekDto makeDto(Transaction transaction,
			ResultSet resultSet) throws SQLException, IOException {
		int seq = resultSet.getInt(1);
		int timesheetSeq = resultSet.getInt(2);
		int activitySeq = resultSet.getInt(3);
		String time1Str = resultSet.getString(4);
		String time2Str = resultSet.getString(5);
		String time3Str = resultSet.getString(6);
		String time4Str = resultSet.getString(7);
		String time5Str = resultSet.getString(8);
		String time6Str = resultSet.getString(9);
		String time7Str = resultSet.getString(10);

		TimesheetDto timesheet = timesheetDao.findBySeq(transaction,
				timesheetSeq);
		ActivityDto activity = activityDao.findBySeq(transaction, activitySeq);
		TaskDto task = taskDao.findBySeq(transaction, activity.getTaskSeq());

		Date time1 = null;
		Date time2 = null;
		Date time3 = null;
		Date time4 = null;
		Date time5 = null;
		Date time6 = null;
		Date time7 = null;
		try {
			time1 = DateUtil.getDateWithTimeFormatInstance().parse(time1Str);
			time2 = DateUtil.getDateWithTimeFormatInstance().parse(time2Str);
			time3 = DateUtil.getDateWithTimeFormatInstance().parse(time3Str);
			time4 = DateUtil.getDateWithTimeFormatInstance().parse(time4Str);
			time5 = DateUtil.getDateWithTimeFormatInstance().parse(time5Str);
			time6 = DateUtil.getDateWithTimeFormatInstance().parse(time6Str);
			time7 = DateUtil.getDateWithTimeFormatInstance().parse(time7Str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		TimesheetPerWeekDto dto = new TimesheetPerWeekDto(seq, timesheet, task,
				activity, time1, time2, time3, time4, time5, time6, time7);
		return dto;
	}

	@Override
	public void add(Transaction transaction, TimesheetPerWeekDto dto)
			throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setInt(1, dto.getTimesheet().getSeq());
			prepareStatement.setInt(2, dto.getActivity().getSeq());
			prepareStatement.setString(3, DateUtil
					.getDateWithTimeFormatInstance().format(dto.getTime1()));
			prepareStatement.setString(4, DateUtil
					.getDateWithTimeFormatInstance().format(dto.getTime2()));
			prepareStatement.setString(5, DateUtil
					.getDateWithTimeFormatInstance().format(dto.getTime3()));
			prepareStatement.setString(6, DateUtil
					.getDateWithTimeFormatInstance().format(dto.getTime4()));
			prepareStatement.setString(7, DateUtil
					.getDateWithTimeFormatInstance().format(dto.getTime5()));
			prepareStatement.setString(8, DateUtil
					.getDateWithTimeFormatInstance().format(dto.getTime6()));
			prepareStatement.setString(9, DateUtil
					.getDateWithTimeFormatInstance().format(dto.getTime7()));

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
	public void update(Transaction transaction, TimesheetPerWeekDto dto)
			throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(UPDATE);
			prepareStatement.setInt(1, dto.getTimesheet().getSeq());
			prepareStatement.setInt(2, dto.getActivity().getSeq());
			prepareStatement.setString(3, DateUtil
					.getDateWithTimeFormatInstance().format(dto.getTime1()));
			prepareStatement.setString(4, DateUtil
					.getDateWithTimeFormatInstance().format(dto.getTime2()));
			prepareStatement.setString(5, DateUtil
					.getDateWithTimeFormatInstance().format(dto.getTime3()));
			prepareStatement.setString(6, DateUtil
					.getDateWithTimeFormatInstance().format(dto.getTime4()));
			prepareStatement.setString(7, DateUtil
					.getDateWithTimeFormatInstance().format(dto.getTime5()));
			prepareStatement.setString(8, DateUtil
					.getDateWithTimeFormatInstance().format(dto.getTime6()));
			prepareStatement.setString(9, DateUtil
					.getDateWithTimeFormatInstance().format(dto.getTime7()));
			prepareStatement.setInt(10, dto.getSeq());
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

	private static final String GET_BY_TIMESHEET_SEQ = "select seq, timesheet_seq, activity_seq, time1, time2, time3, time4, time5, time6, time7 from S_TIMESHEET_PER_WEEK where timesheet_seq = ?";
	private static final String INSERT = "insert into S_TIMESHEET_PER_WEEK (timesheet_seq, activity_seq, time1, time2, time3, time4, time5, time6, time7) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "update S_TIMESHEET_PER_WEEK set timesheet_seq = ?, activity_seq = ?, time1 = ?, time2 = ?, time3 = ?, time4 = ?, time5 = ?, time6 = ?, time7 = ? where seq = ?";
	private static final String DELETE = "delete from S_TIMESHEET_PER_WEEK where seq = ?";

}
