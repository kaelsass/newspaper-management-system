package npp.impl.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import npp.condition.DynamicQuery;
import npp.dto.ActivityDto;
import npp.dto.TaskAdminDto;
import npp.dto.TaskDto;
import npp.dto.WorkInfoDto;
import npp.spec.dao.ActivityDao;
import npp.spec.dao.TaskAdminDao;
import npp.spec.dao.TaskDao;
import npp.spec.dao.Transaction;
import npp.spec.dao.WorkInfoDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Stateless
public class TaskDaoImpl implements TaskDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private WorkInfoDao workInfoDao;

	private TaskAdminDao taskAdminDao;

	private ActivityDao activityDao;

	@Inject
	public TaskDaoImpl(WorkInfoDao workInfoDao, TaskAdminDao taskAdminDao, ActivityDao activityDao)
	{
		this.workInfoDao = workInfoDao;
		this.taskAdminDao = taskAdminDao;
		this.activityDao = activityDao;
	}

	@Override
	public List<TaskDto> getAllList(Transaction transaction, DynamicQuery query) throws IOException {
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			query.setBaseSql(GET_ALL);
			prepareStatement = connection.prepareStatement(query.generateSql());
			query.fillPreparedStatement(prepareStatement);
			resultSet = prepareStatement.executeQuery();

			List<TaskDto> items = new ArrayList<TaskDto>();
			while (resultSet.next()) {

				TaskDto dto = makeDto(transaction, resultSet);
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

	private TaskDto makeDto(Transaction transaction, ResultSet resultSet) throws SQLException, IOException {
		int seq = resultSet.getInt(1);
		String name = resultSet.getString(2);
		String description = resultSet.getString(3);

		ArrayList<WorkInfoDto> admins = new ArrayList<WorkInfoDto>();
		List<TaskAdminDto> tas = taskAdminDao.findByTaskSeq(transaction, seq);
		List<ActivityDto> activities = activityDao.findByTaskSeq(transaction, seq);

		for(TaskAdminDto dto : tas)
		{
			admins.add(workInfoDao.findByID(transaction, dto.getEmployeeID()));
		}

		TaskDto dto = new TaskDto(seq, name, description, admins, activities);
		return dto;
	}


	@Override
	public TaskDto findBySeq(Transaction transaction, int seq) throws IOException {
		TaskDto dto = null;

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
	public int getNewInsertedSeq(Transaction transaction) throws IOException {
		int seq = 0;

		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_NEW_INSERTED_SEQ);
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
	public void add(Transaction transaction,
			TaskDto dto) throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setString(1, dto.getName());
			prepareStatement.setString(2, dto.getDescription());
			prepareStatement.executeUpdate();

			int seq = getNewInsertedSeq(transaction);
			for(WorkInfoDto employee : dto.getAdmins())
			{
				TaskAdminDto tad = new TaskAdminDto();
				tad.setTaskSeq(seq);
				tad.setEmployeeID(employee.getId());
				taskAdminDao.add(transaction, tad);
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
	public void update(Transaction transaction,
			TaskDto dto) throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(UPDATE);
			prepareStatement.setString(1, dto.getName());
			prepareStatement.setString(2, dto.getDescription());
			prepareStatement.setInt(3, dto.getSeq());
			prepareStatement.executeUpdate();

			taskAdminDao.delete(transaction, dto.getSeq());
			for(WorkInfoDto employee : dto.getAdmins())
			{
				TaskAdminDto trd = new TaskAdminDto();
				trd.setTaskSeq(dto.getSeq());
				trd.setEmployeeID(employee.getId());
				taskAdminDao.add(transaction, trd);
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

	private static final String GET_ALL = "select seq, name, description from S_TASK where 1 = 1 ";
	private static final String GET_NEW_INSERTED_SEQ = "select max(seq) from S_TASK";
	private static final String GET_BY_SEQ = "select seq, name, description from S_TASK where seq = ?";
	private static final String INSERT = "insert into S_TASK (name, description) values (?, ?)";
	private static final String UPDATE = "update S_TASK set name = ?, description = ? where seq = ?";
	private static final String DELETE = "delete from S_TASK where seq = ?";


}
