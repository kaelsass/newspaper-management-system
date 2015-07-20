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
import npp.dto.IssueDto;
import npp.dto.PromotionDto;
import npp.dto.ReturnDto;
import npp.dto.WorkInfoDto;
import npp.spec.dao.IssueDao;
import npp.spec.dao.PromotionDao;
import npp.spec.dao.ReturnDao;
import npp.spec.dao.Transaction;
import npp.spec.dao.WorkInfoDao;
import npp.utils.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Stateless
public class ReturnDaoImpl implements ReturnDao {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private IssueDao issueDao;
	private WorkInfoDao workInfoDao;
	private PromotionDao promotionDao;

	@Inject
	public ReturnDaoImpl(IssueDao issueDao,
			WorkInfoDao workInfoDao, PromotionDao promotionDao) {
		this.issueDao = issueDao;
		this.workInfoDao = workInfoDao;
		this.promotionDao = promotionDao;
	}

	@Override
	public ReturnDto findBySeq(Transaction transaction, int seq)
			throws IOException {
		ReturnDto dto = null;
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
	public List<ReturnDto> getAllList(Transaction transaction,
			DynamicQuery query) throws IOException {
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			query.setBaseSql(GET_ALL);
			query.setSuffix(ORDER);
			prepareStatement = connection.prepareStatement(query.generateSql());
			query.fillPreparedStatement(prepareStatement);

			resultSet = prepareStatement.executeQuery();

			List<ReturnDto> items = new ArrayList<ReturnDto>();
			while (resultSet.next()) {
				ReturnDto dto = makeDto(transaction, resultSet);
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

	private ReturnDto makeDto(Transaction transaction, ResultSet resultSet)
			throws SQLException, IOException {
		int seq = resultSet.getInt(1);
		int issueSeq = resultSet.getInt(2);
		double unitPrice = resultSet.getDouble(3);
		int quantity = resultSet.getInt(4);
		double discount = resultSet.getDouble(5);
		String employeeID = resultSet.getString(6);
		String orderDateStr = resultSet.getString(7);
		int promotionSeq = resultSet.getInt(8);

		Date orderDate = null;
		try {
			orderDate = DateUtil.getDateWithTimeFormatInstance().parse(orderDateStr);
		} catch (ParseException e) {
			orderDate = null;
		}

		IssueDto issueDto = issueDao.findBySeq(transaction,
				issueSeq);
		WorkInfoDto workInfoDto = workInfoDao.findByID(transaction, employeeID);
		PromotionDto promotion = promotionDao.findBySeq(transaction, promotionSeq);

		ReturnDto dto = new ReturnDto(seq, issueDto, unitPrice,
				quantity, discount,
				workInfoDto, orderDate, promotion);
		return dto;
	}

	@Override
	public void add(Transaction transaction, ReturnDto dto)
			throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setInt(1, dto.getIssue().getSeq());
			prepareStatement.setDouble(2, dto.getUnitPrice());
			prepareStatement.setInt(3, dto.getQuantity());
			prepareStatement.setDouble(4, dto.getDiscount());
			prepareStatement.setString(5, dto.getEmployee().getId());
			prepareStatement.setString(6, DateUtil.getDateWithTimeFormatInstance().format(dto.getOrderDate()));

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
	public void update(Transaction transaction, ReturnDto dto)
			throws IOException {
		PreparedStatement prepareStatement = null;
		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(UPDATE);
			prepareStatement.setInt(1, dto.getIssue().getSeq());
			prepareStatement.setDouble(2, dto.getUnitPrice());
			prepareStatement.setInt(3, dto.getQuantity());
			prepareStatement.setDouble(4, dto.getDiscount());
			prepareStatement.setString(5, dto.getEmployee().getId());
			prepareStatement.setString(6, DateUtil.getDateWithTimeFormatInstance().format(dto.getOrderDate()));
			prepareStatement.setInt(7, dto.getSeq());

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

	private static final String ORDER = " order by issue_seq, quantity";
	private static final String GET_ALL = "select seq, issue_seq, unitprice, quantity, discount, employee_id, order_date, promotion_seq from n_return where 1 = 1 ";
	private static final String GET_BY_SEQ = "select seq, issue_seq, unitprice, quantity, discount, employee_id, order_date, promotion_seq from n_return where seq = ?";
	private static final String INSERT = "insert into n_return (issue_seq, unitprice, quantity, discount, employee_id, order_date, promotion_seq) values (?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "update n_return set issue_seq = ?, unitprice = ?, quantity = ?, discount = ?, employee_id = ?, order_date = ?, promotion_seq = ? where seq = ?";
	private static final String DELETE = "delete from n_return where seq = ?";

}
