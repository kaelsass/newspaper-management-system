package npp.impl.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import npp.dto.AqDto;
import npp.dto.AqLog;
import npp.spec.dao.AqLogDao;
import npp.spec.dao.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Stateless
public class AqLogDaoImpl implements AqLogDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public AqLog find(Transaction transaction, AqDto dto, String employeeID) throws IOException {
		AqLog log = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_ALL);
			prepareStatement.setInt(1, dto.getAppraisalSeq());
			prepareStatement.setString(2, employeeID);
			prepareStatement.setInt(3, dto.getQuestion().getSeq());
			resultSet = prepareStatement.executeQuery();

			if (resultSet.next()) {
				log = new AqLog();
				log.setAq(dto);
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
			AqLog log) throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setInt(1, log.getAq().getAppraisalSeq());
			prepareStatement.setString(2, log.getEmployeeID());
			prepareStatement.setInt(3, log.getAq().getQuestion().getSeq());
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
	public void update(Transaction transaction, AqLog log)
			throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(UPDATE);
			prepareStatement.setInt(1, log.getRate());
			prepareStatement.setString(2, log.getComment());
			prepareStatement.setInt(3, log.getAq().getAppraisalSeq());
			prepareStatement.setString(4, log.getEmployeeID());
			prepareStatement.setInt(5, log.getAq().getQuestion().getSeq());
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
	public void delete(Transaction transaction, AqLog log)
			throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(DELETE);
			prepareStatement.setInt(1, log.getAq().getAppraisalSeq());
			prepareStatement.setString(2, log.getEmployeeID());
			prepareStatement.setInt(3, log.getAq().getQuestion().getSeq());
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

	private static final String GET_ALL = "select RATE, COMMENTS from S_AQ_LOG where appraisal_seq = ? and employee_id = ? and question_seq = ?";
	private static final String INSERT = "insert into S_AQ_LOG (appraisal_seq, employee_id, question_seq, RATE, COMMENTS) values ( ?, ?, ?, ?, ?)";
	private static final String UPDATE = "update S_AQ_LOG set RATE = ?, COMMENTS = ? where appraisal_seq = ? and employee_id = ? and question_seq = ?";
	private static final String DELETE = "delete from S_AQ_LOG where appraisal_seq = ? and employee_id = ? and question_seq = ?";

}
