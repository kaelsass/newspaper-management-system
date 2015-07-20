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
import npp.dto.TimesheetDto;
import npp.dto.WorkInfoDto;
import npp.spec.dao.TimesheetDao;
import npp.spec.dao.Transaction;
import npp.spec.dao.WorkInfoDao;
import npp.utils.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Stateless
public class TimesheetDaoImpl implements TimesheetDao {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private WorkInfoDao workInfoDao;

	@Inject
	public TimesheetDaoImpl(WorkInfoDao workInfoDao) {
		this.workInfoDao = workInfoDao;
	}

	@Override
	public TimesheetDto findBySeq(Transaction transaction, int seq)
			throws IOException {
		TimesheetDto dto = null;
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
	public int getNewInsertedSeq(Transaction transaction)
			throws IOException {
		int seq = 0;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_NEW_INSERT_SEQ);
			resultSet = prepareStatement.executeQuery();

			if (resultSet.next()) {
				seq =  resultSet.getInt(1);
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
	public List<TimesheetDto> getAllList(Transaction transaction, DynamicQuery query)
			throws IOException {
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			query.setBaseSql(GET_ALL);
			query.setSuffix(ORDER);
			prepareStatement = connection.prepareStatement(query.generateSql());
			query.fillPreparedStatement(prepareStatement);

			resultSet = prepareStatement.executeQuery();

			List<TimesheetDto> items = new ArrayList<TimesheetDto>();
			while (resultSet.next()) {
				TimesheetDto dto = makeDto(transaction, resultSet);
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

	private TimesheetDto makeDto(Transaction transaction, ResultSet resultSet)
			throws SQLException, IOException {
		// id, work_unit, address, newspaper_seq, page_count, page_area,
		// date_from, dateTo,
		// unit_price, discount, money_pay, contact_person, phone, email,
		// employee_id, order_date
		int seq = resultSet.getInt(1);
		String employeeID = resultSet.getString(2);
		String dateFromStr = resultSet.getString(3);
		String dateToStr = resultSet.getString(4);
		Date dateFrom = null;
		try {
			dateFrom = DateUtil.getDateFormatInstance().parse(
					dateFromStr);
		} catch (ParseException e) {
			dateFrom = null;
		}
		Date dateTo = null;
		try {
			dateTo = DateUtil.getDateFormatInstance().parse(dateToStr);
		} catch (ParseException e) {
			dateTo = null;
		}

		WorkInfoDto employee = workInfoDao.findByID(transaction, employeeID);

		TimesheetDto dto = new TimesheetDto(seq, employee, dateFrom, dateTo);
		return dto;
	}

	@Override
	public void add(Transaction transaction, TimesheetDto dto)
			throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			//id, work_unit, address, newspaper_seq, page_count, page_area, date_from, dateTo,
			//unit_price, discount, money_pay, contact_person, phone, email, employee_id, order_date
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setString(1, dto.getEmployee().getId());
			prepareStatement.setString(2, DateUtil.getDateFormatInstance().format(dto.getStartDate()));
			prepareStatement.setString(3, DateUtil.getDateFormatInstance().format(dto.getEndDate()));
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
	public void update(Transaction transaction, TimesheetDto dto) throws IOException {
		PreparedStatement prepareStatement = null;
		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(UPDATE);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setString(1, dto.getEmployee().getId());
			prepareStatement.setString(2, DateUtil.getDateFormatInstance().format(dto.getStartDate()));
			prepareStatement.setString(3, DateUtil.getDateFormatInstance().format(dto.getEndDate()));
			prepareStatement.setInt(4, dto.getSeq());

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

	private static final String ORDER = " order by employee_id";
	private static final String GET_NEW_INSERT_SEQ = "select max(seq) from S_TIMESHEET";
	private static final String GET_ALL = "select seq, employee_id, start_date, end_date from S_TIMESHEET where 1 = 1 ";
	private static final String GET_BY_SEQ = "select seq, employee_id, start_date, end_date from S_TIMESHEET where seq = ?";
	private static final String INSERT = "insert into S_TIMESHEET (employee_id, start_date, end_date) values (?, ?, ?)";
	private static final String UPDATE = "update S_TIMESHEET set employee_id = ?, start_date = ?, end_date = ? where seq = ?";
	private static final String DELETE = "delete from S_TIMESHEET where seq = ?";

}
