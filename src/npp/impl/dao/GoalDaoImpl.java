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

import npp.condition.DynamicQuery;
import npp.dto.GoalDto;
import npp.dto.StatusDto;
import npp.dto.WorkInfoDto;
import npp.spec.dao.GoalDao;
import npp.spec.dao.GoalStatusDao;
import npp.spec.dao.GoalTypeDao;
import npp.spec.dao.Transaction;
import npp.spec.dao.WorkInfoDao;
import npp.utils.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Stateless
public class GoalDaoImpl implements GoalDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private WorkInfoDao workInfoDao;
	private GoalStatusDao goalStatusDao;
	private GoalTypeDao goalTypeDao;

	@Inject
	public GoalDaoImpl(WorkInfoDao workInfoDao, GoalStatusDao goalStatusDao, GoalTypeDao goalTypeDao)
	{
		this.workInfoDao = workInfoDao;
		this.goalStatusDao = goalStatusDao;
		this.goalTypeDao = goalTypeDao;
	}
	@Override
	public List<GoalDto> getAllList(Transaction transaction, DynamicQuery query) throws IOException {
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			query.setBaseSql(GET_ALL);
			prepareStatement = connection.prepareStatement(query.generateSql());
			query.fillPreparedStatement(prepareStatement);
			resultSet = prepareStatement.executeQuery();

			List<GoalDto> items = new ArrayList<GoalDto>();
			while (resultSet.next()) {

				GoalDto departmentDto = makeDto(transaction, resultSet);
				items.add(departmentDto);
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

	private GoalDto makeDto(Transaction transaction, ResultSet resultSet) throws SQLException, IOException {
		int seq = resultSet.getInt(1);
		String name = resultSet.getString(2);
		int completion = resultSet.getInt(3);
		String employeeID = resultSet.getString(4);
		String dueStr = resultSet.getString(5);
		int statusSeq = resultSet.getInt(6);
		int typeSeq = resultSet.getInt(7);
		String description = resultSet.getString(8);
		WorkInfoDto employee = workInfoDao.findByID(transaction, employeeID);
		Date due = null;
		try {
			due = DateUtil.getDateFormatInstance().parse(dueStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StatusDto status = goalStatusDao.findBySeq(transaction, statusSeq);
		StatusDto type = goalTypeDao.findBySeq(transaction, typeSeq);

		GoalDto dto = new GoalDto(seq, name, completion, employee, due, status, type, description);
		return dto;
	}


	@Override
	public GoalDto findBySeq(Transaction transaction, int seq) throws IOException {
		GoalDto dto = null;

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
			GoalDto dto) throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setString(1, dto.getName());
			prepareStatement.setInt(2, dto.getCompletion());
			WorkInfoDto employee = workInfoDao.findByName(transaction, dto.getEmployee().getName());
			prepareStatement.setString(3, employee.getId());
			prepareStatement.setString(4, DateUtil.getDateFormatInstance().format(dto.getDue()));
			prepareStatement.setInt(5, dto.getStatus().getSeq());
			prepareStatement.setInt(6, dto.getType().getSeq());
			prepareStatement.setString(7, dto.getDescription());
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
	public void update(Transaction transaction,
			GoalDto dto) throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(UPDATE);
			prepareStatement.setString(1, dto.getName());
			prepareStatement.setInt(2, dto.getCompletion());
			WorkInfoDto employee = workInfoDao.findByName(transaction, dto.getEmployee().getName());
			prepareStatement.setString(3, employee.getId());
			prepareStatement.setString(4, DateUtil.getDateFormatInstance().format(dto.getDue()));
			prepareStatement.setInt(5, dto.getStatus().getSeq());
			prepareStatement.setInt(6, dto.getType().getSeq());
			prepareStatement.setString(7, dto.getDescription());
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


	@Override
	public GoalDto findByName(Transaction transaction,
			String name) throws IOException {
		GoalDto dto = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_BY_NAME);
			prepareStatement.setString(1, name);
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

	private static final String GET_ALL = "select seq, name, completion, employee_id, due, status_seq, type_seq, description from S_GOAL where 1 = 1 ";
	private static final String GET_BY_SEQ = "select seq, name, completion, employee_id, due, status_seq, type_seq, description from S_GOAL where seq = ?";
	private static final String GET_BY_NAME = "select seq, name, completion, employee_id, due, status_seq, type_seq, description from S_GOAL where name = ?";
	private static final String INSERT = "insert into S_GOAL (name, completion, employee_id, due, status_seq, type_seq, description) values ( ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "update S_GOAL set name = ?, completion = ?, employee_id = ?, due = ?, status_seq = ?, type_seq = ?, description = ? where seq = ?";
	private static final String DELETE = "delete from S_GOAL where seq = ?";

}
