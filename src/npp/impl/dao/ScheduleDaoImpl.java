package npp.impl.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import npp.dto.EventDto;
import npp.dto.ReminderDto;
import npp.spec.dao.ReminderDao;
import npp.spec.dao.ScheduleDao;
import npp.spec.dao.Transaction;
import npp.spec.manager.SessionManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Stateless
public class ScheduleDaoImpl implements ScheduleDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private SessionManager sessionManager;
	private ReminderDao reminderDao;

	@Inject
	public ScheduleDaoImpl(SessionManager sessionManager,
			ReminderDao reminderDao) {
		this.sessionManager = sessionManager;
		this.reminderDao = reminderDao;
	}

	@Override
	public List<EventDto> getAllList(Transaction transaction)
			throws IOException {
		List<EventDto> list = new ArrayList<EventDto>();
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_ALL);
			prepareStatement.setString(1, sessionManager.getLoginUser()
					.getEmployee().getId());
			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				EventDto event = makeDto(transaction, resultSet);
				list.add(event);
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
	public List<EventDto> getNotificationEvents(Transaction transaction)
			throws IOException {
		List<EventDto> allList = getAllList(transaction);
		List<EventDto> list = new ArrayList<EventDto>();

		for(EventDto dto : allList)
		{
			if(dto.isNeedNotify())
				list.add(dto);
		}
		return list;
	}

	private EventDto makeDto(Transaction transaction, ResultSet resultSet)
			throws SQLException, IOException {
		EventDto event = new EventDto();

		try {
			String id = resultSet.getString(1);
			String title = resultSet.getString(2);
			String from = resultSet.getString(3);
			String to = resultSet.getString(4);
			int reminderSeq = resultSet.getInt(5);
			String note = resultSet.getString(6);
			int complete = resultSet.getInt(7);
			boolean emailNotification = (resultSet.getInt(8) == 0 ? false : true);
			String email = resultSet.getString(9);

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date start = df.parse(from);
			Date end = df.parse(to);
			ReminderDto reminder = reminderDao.findBySeq(transaction,
					reminderSeq);
			event = new EventDto(id, title, start, end, reminder, note, complete, emailNotification, email);
			return event;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public void add(Transaction transaction, EventDto dto) throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setString(1, dto.getId());
			prepareStatement.setString(2, dto.getTitle());
			prepareStatement.setString(3, sessionManager.getLoginUser()
					.getEmployee().getId());
			prepareStatement.setString(4, df.format(dto.getDateFrom()));
			prepareStatement.setString(5, df.format(dto.getDateTo()));
			prepareStatement.setInt(6, dto.getReminder().getSeq());
			prepareStatement.setString(7, dto.getNote());
			prepareStatement.setInt(8, dto.getComplete());
			prepareStatement.setBoolean(9, dto.isEmailNotification());
			prepareStatement.setString(10, dto.getEmail());
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
	public void update(Transaction transaction, EventDto dto)
			throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(UPDATE);
			prepareStatement.setString(1, dto.getTitle());
			prepareStatement.setString(2, df.format(dto.getDateFrom()));
			prepareStatement.setString(3, df.format(dto.getDateTo()));
			prepareStatement.setInt(4, dto.getReminder().getSeq());
			prepareStatement.setString(5, dto.getNote());
			prepareStatement.setInt(6, dto.getComplete());
			prepareStatement.setBoolean(7, dto.isEmailNotification());
			prepareStatement.setString(8, dto.getEmail());
			prepareStatement.setString(9, dto.getId());
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
	public void delete(Transaction transaction, String id) throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(DELETE);
			prepareStatement.setString(1, id);
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

	private static final String GET_ALL = "select id, title, date_from, date_to, reminder_seq, note, complete, notification, email from s_schedule where employee_id = ?";
	private static final String INSERT = "insert into s_schedule (id, title, employee_id, date_from, date_to, reminder_seq, note, complete, notification, email) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "update s_schedule set title = ?, date_from = ?, date_to = ?, reminder_seq = ?, note = ?, complete = ?, notification = ?, email = ? where id = ?";
	private static final String DELETE = "delete from s_schedule where id = ?";

}
