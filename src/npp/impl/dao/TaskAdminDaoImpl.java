package npp.impl.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import npp.dto.TaskAdminDto;
import npp.spec.dao.TaskAdminDao;
import npp.spec.dao.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Stateless
public class TaskAdminDaoImpl implements TaskAdminDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private TaskAdminDto makeDto(Transaction transaction, ResultSet resultSet) throws SQLException, IOException {
		int taskSeq = resultSet.getInt(1);
		String employeeID = resultSet.getString(2);
		TaskAdminDto dto = new TaskAdminDto(taskSeq, employeeID);
		return dto;
	}


	@Override
	public List<TaskAdminDto> findByTaskSeq(Transaction transaction, int taskSeq) throws IOException {
		TaskAdminDto dto = null;

		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_BY_TASK_SEQ);
			prepareStatement.setInt(1, taskSeq);
			resultSet = prepareStatement.executeQuery();
			ArrayList<TaskAdminDto> list = new ArrayList<TaskAdminDto>();
			while(resultSet.next()) {
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
	public List<TaskAdminDto> findByEmployeeID(Transaction transaction, String id) throws IOException {
		TaskAdminDto dto = null;

		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_BY_EMPLOYEE_ID);
			prepareStatement.setString(1, id);
			resultSet = prepareStatement.executeQuery();
			ArrayList<TaskAdminDto> list = new ArrayList<TaskAdminDto>();
			while(resultSet.next()) {
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
	public void add(Transaction transaction,
			TaskAdminDto dto) throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setInt(1, dto.getTaskSeq());
			prepareStatement.setString(2, dto.getEmployeeID());
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
	public void delete(Transaction transaction, int taskSeq)
			throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(DELETE);
			prepareStatement.setInt(1, taskSeq);
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

	private static final String GET_BY_TASK_SEQ = "select task_seq, employee_id from S_TASK_ADMIN where task_seq = ?";
	private static final String GET_BY_EMPLOYEE_ID = "select task_seq, employee_id from S_TASK_ADMIN where employee_id = ?";
	private static final String INSERT = "insert into S_TASK_ADMIN (task_seq, employee_id) values ( ?, ?)";
	private static final String DELETE = "delete from S_TASK_ADMIN where task_seq = ?";

}
