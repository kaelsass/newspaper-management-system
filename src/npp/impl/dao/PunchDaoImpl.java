package npp.impl.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;

import npp.condition.DynamicQuery;
import npp.dto.PunchDto;
import npp.dto.WorkInfoDto;
import npp.spec.dao.PunchDao;
import npp.spec.dao.Transaction;
import npp.spec.dao.WorkInfoDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Stateless
public class PunchDaoImpl implements PunchDao {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private WorkInfoDao employeeDao;

	@Inject
	public PunchDaoImpl(WorkInfoDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	@Override
	public PunchDto findBySeq(Transaction transaction, int seq)
			throws IOException {
		PunchDto dto = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_BY_SEQ);
			prepareStatement.setInt(1, seq);
			resultSet = prepareStatement.executeQuery();

			if (resultSet.next()) {
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
	public List<PunchDto> getAllList(
			Transaction transaction, DynamicQuery query) throws IOException {
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			query.setBaseSql(GET_ALL);
			query.setSuffix(ORDER);
			prepareStatement = connection.prepareStatement(query.generateSql());
			query.fillPreparedStatement(prepareStatement);

			resultSet = prepareStatement.executeQuery();

			List<PunchDto> items = new ArrayList<PunchDto>();
			while (resultSet.next()) {
				PunchDto userDto = makeDto(transaction,
						resultSet);
				items.add(userDto);
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
	public List<PunchDto> getUnfinishedList(
			Transaction transaction) throws IOException {
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_NOT_FINISHED);

			resultSet = prepareStatement.executeQuery();

			List<PunchDto> items = new ArrayList<PunchDto>();
			while (resultSet.next()) {
				PunchDto userDto = makeDto(transaction,
						resultSet);
				items.add(userDto);
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

	private PunchDto makeDto(Transaction transaction,
			ResultSet resultSet) throws SQLException, IOException {
		int seq = resultSet.getInt(1);
		String employeeid = resultSet.getString(2);
		String intime = resultSet.getString(3);
		String innote = resultSet.getString(4);
		String outtime = resultSet.getString(5);
		String outnote = resultSet.getString(6);

		WorkInfoDto employee = employeeDao.findByID(transaction, employeeid);

		PunchDto dto = new PunchDto(seq, employee, intime, innote, outtime, outnote);
		return dto;
	}

	@Override
	public void add(Transaction transaction, PunchDto dto)
			throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setString(1, dto.getEmployee().getId());
			prepareStatement.setString(2, dto.getPunchInTime());
			prepareStatement.setString(3, dto.getPunchInNote());
			prepareStatement.setString(4, dto.getPunchOutTime());
			prepareStatement.setString(5, dto.getPunchOutNote());
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
	@RolesAllowed("admin")
	public void update(Transaction transaction, PunchDto dto)
			throws IOException {
		PreparedStatement prepareStatement = null;
		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(UPDATE);
			prepareStatement.setString(1, dto.getEmployee().getId());
			prepareStatement.setString(2, dto.getPunchInTime());
			prepareStatement.setString(3, dto.getPunchInNote());
			prepareStatement.setString(4, dto.getPunchOutTime());
			prepareStatement.setString(5, dto.getPunchOutNote());
			prepareStatement.setInt(6, dto.getSeq());
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
	private static final String ORDER = " order by employeeid, intime";
	private static final String GET_ALL = "select seq, employeeid, intime, innote, outtime, outnote from s_punch where 1 = 1 ";
	private static final String GET_NOT_FINISHED = "select seq, employeeid, intime, innote, outtime, outnote from s_punch where outtime is NULL";

	private static final String GET_BY_SEQ = "select seq, employeeid, intime, innote, outtime, outnote from s_punch where seq = ? order by employeeid";
	private static final String INSERT = "insert into s_punch (employeeid, intime, innote, outtime, outnote) values (?, ?, ?, ?, ?)";
	private static final String UPDATE = "update s_punch set employeeid = ?, intime = ? , innote=?, outtime=?, outnote= ?  where seq = ?";
	private static final String DELETE = "delete from s_punch where seq = ?";

}
