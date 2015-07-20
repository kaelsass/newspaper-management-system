package npp.impl.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import npp.dto.ArDto;
import npp.dto.EvalGroupDto;
import npp.dto.WorkInfoDto;
import npp.spec.dao.AppraisalReviewerDao;
import npp.spec.dao.EvalGroupDao;
import npp.spec.dao.Transaction;
import npp.spec.dao.WorkInfoDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Stateless
public class AppraisalReviewerDaoImpl implements AppraisalReviewerDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private WorkInfoDao workInfoDao;
	private EvalGroupDao evalGroupDao;

	@Inject
	public AppraisalReviewerDaoImpl(WorkInfoDao workInfoDao, EvalGroupDao evalGroupDao)
	{
		this.workInfoDao = workInfoDao;
		this.evalGroupDao = evalGroupDao;
	}
	@Override
	public List<ArDto> getAllList(Transaction transaction, int appraisalSeq) throws IOException {
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_ALL);
			prepareStatement.setInt(1, appraisalSeq);
			resultSet = prepareStatement.executeQuery();

			List<ArDto> items = new ArrayList<ArDto>();
			while (resultSet.next()) {

				ArDto dto = makeDto(transaction, resultSet);
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
	@Override
	public List<ArDto> findByEmployeeID(Transaction transaction, String id)
			throws IOException {
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_BY_EMPLOYEE_ID);
			prepareStatement.setString(1, id);
			resultSet = prepareStatement.executeQuery();

			List<ArDto> items = new ArrayList<ArDto>();
			while (resultSet.next()) {

				ArDto dto = makeDto(transaction, resultSet);
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

	private ArDto makeDto(Transaction transaction, ResultSet resultSet) throws SQLException, IOException {
		int appraisalSeq = resultSet.getInt(1);
		String employeeID = resultSet.getString(2);
		int evalGroupSeq = resultSet.getInt(3);

		WorkInfoDto employee = workInfoDao.findByID(transaction, employeeID);
		EvalGroupDto evalGroup = evalGroupDao.findBySeq(transaction, evalGroupSeq);

		ArDto dto = new ArDto(appraisalSeq, employee, evalGroup);
		return dto;
	}

	@Override
	public void add(Transaction transaction,
			ArDto dto) throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setInt(1, dto.getAppraisalSeq());
			prepareStatement.setString(2, dto.getEmployee().getId());
			prepareStatement.setInt(3, dto.getEvalGroup().getSeq());
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
	public void delete(Transaction transaction, int appraisalSeq)
			throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(DELETE);
			prepareStatement.setInt(1, appraisalSeq);
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

	private static final String GET_ALL = "select appraisal_seq, employee_id, eg_seq from S_APPRAISAL_REVIEWER where appraisal_seq = ?";
	private static final String GET_BY_EMPLOYEE_ID = "select appraisal_seq, employee_id, eg_seq from S_APPRAISAL_REVIEWER where employee_id = ?";
	private static final String INSERT = "insert into S_APPRAISAL_REVIEWER (appraisal_seq, employee_id, eg_seq) values ( ?, ?, ?)";
	private static final String DELETE = "delete from S_APPRAISAL_REVIEWER where appraisal_seq = ?";



}
