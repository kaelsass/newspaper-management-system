package npp.impl.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import npp.dto.AgDto;
import npp.dto.AgLog;
import npp.spec.dao.AgLogDao;
import npp.spec.dao.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Stateless
public class AgLogDaoImpl implements AgLogDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public AgLog find(Transaction transaction, AgDto dto, String employeeID) throws IOException {
		AgLog log = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_ALL);
			prepareStatement.setInt(1, dto.getAppraisalSeq());
			prepareStatement.setString(2, employeeID);
			prepareStatement.setInt(3, dto.getGoal().getSeq());
			resultSet = prepareStatement.executeQuery();

			if (resultSet.next()) {
				log = new AgLog();
				log.setAg(dto);
				log.setEmployeeID(employeeID);
				log.setRate(resultSet.getInt(1));
				log.setComment(resultSet.getString(2));
			}
			return log;
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
			AgLog log) throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setInt(1, log.getAg().getAppraisalSeq());
			prepareStatement.setString(2, log.getEmployeeID());
			prepareStatement.setInt(3, log.getAg().getGoal().getSeq());
			prepareStatement.setInt(4, log.getRate());
			prepareStatement.setString(5, log.getComment());
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
	public void update(Transaction transaction, AgLog log)
			throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(UPDATE);
			prepareStatement.setInt(1, log.getRate());
			prepareStatement.setString(2, log.getComment());
			prepareStatement.setInt(3, log.getAg().getAppraisalSeq());
			prepareStatement.setString(4, log.getEmployeeID());
			prepareStatement.setInt(5, log.getAg().getGoal().getSeq());
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
	public void delete(Transaction transaction, AgLog log)
			throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(DELETE);
			prepareStatement.setInt(1, log.getAg().getAppraisalSeq());
			prepareStatement.setString(2, log.getEmployeeID());
			prepareStatement.setInt(3, log.getAg().getGoal().getSeq());
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

	private static final String GET_ALL = "select RATE, COMMENTS from S_AG_LOG where appraisal_seq = ? and employee_id = ? and goal_seq = ?";
	private static final String INSERT = "insert into S_AG_LOG (appraisal_seq, employee_id, goal_seq, RATE, COMMENTS) values ( ?, ?, ?, ?, ?)";
	private static final String UPDATE = "update S_AG_LOG set RATE = ?, COMMENTS = ? where appraisal_seq = ? and employee_id = ? and goal_seq = ?";
	private static final String DELETE = "delete from S_AG_LOG where appraisal_seq = ? and employee_id = ? and goal_seq = ?";

}
