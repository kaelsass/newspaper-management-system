package npp.impl.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import npp.dto.AcDto;
import npp.dto.AcLog;
import npp.spec.dao.AcLogDao;
import npp.spec.dao.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Stateless
public class AcLogDaoImpl implements AcLogDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public AcLog find(Transaction transaction, AcDto ac, String employeeID) throws IOException {
		AcLog log = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_ALL);
			prepareStatement.setInt(1, ac.getAppraisalSeq());
			prepareStatement.setString(2, employeeID);
			prepareStatement.setInt(3, ac.getCompetency().getSeq());
			resultSet = prepareStatement.executeQuery();

			if (resultSet.next()) {
				log = new AcLog();
				log.setAc(ac);
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
			AcLog log) throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setInt(1, log.getAc().getAppraisalSeq());
			prepareStatement.setString(2, log.getEmployeeID());
			prepareStatement.setInt(3, log.getAc().getCompetency().getSeq());
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
	public void update(Transaction transaction, AcLog log)
			throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(UPDATE);
			prepareStatement.setInt(1, log.getRate());
			prepareStatement.setString(2, log.getComment());
			prepareStatement.setInt(3, log.getAc().getAppraisalSeq());
			prepareStatement.setString(4, log.getEmployeeID());
			prepareStatement.setInt(5, log.getAc().getCompetency().getSeq());
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
	public void delete(Transaction transaction, AcLog log)
			throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(DELETE);
			prepareStatement.setInt(1, log.getAc().getAppraisalSeq());
			prepareStatement.setString(2, log.getEmployeeID());
			prepareStatement.setInt(3, log.getAc().getCompetency().getSeq());
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

	private static final String GET_ALL = "select RATE, COMMENTS from S_AC_LOG where appraisal_seq = ? and employee_id = ? and competency_seq = ?";
	private static final String INSERT = "insert into S_AC_LOG (appraisal_seq, employee_id, competency_seq, RATE, COMMENTS) values ( ?, ?, ?, ?, ?)";
	private static final String UPDATE = "update S_AC_LOG set RATE = ?, COMMENTS = ? where appraisal_seq = ? and employee_id = ? and competency_seq = ?";
	private static final String DELETE = "delete from S_AC_LOG where appraisal_seq = ? and employee_id = ? and competency_seq = ?";

}
