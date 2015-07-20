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
import npp.dto.RetailDto;
import npp.dto.WorkInfoDto;
import npp.spec.dao.IssueDao;
import npp.spec.dao.PromotionDao;
import npp.spec.dao.RetailDao;
import npp.spec.dao.Transaction;
import npp.spec.dao.WorkInfoDao;
import npp.utils.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Stateless
public class RetailDaoImpl implements RetailDao {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private IssueDao issueDao;
	private WorkInfoDao workInfoDao;
	private PromotionDao promotionDao;


	@Inject
	public RetailDaoImpl(IssueDao issueDao,
			WorkInfoDao workInfoDao, PromotionDao promotionDao) {
		this.issueDao = issueDao;
		this.workInfoDao = workInfoDao;
		this.promotionDao = promotionDao;
	}

	@Override
	public RetailDto findBySeq(Transaction transaction, int seq)
			throws IOException {
		RetailDto dto = null;
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
	public List<RetailDto> getAllList(Transaction transaction,
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

			List<RetailDto> items = new ArrayList<RetailDto>();
			while (resultSet.next()) {
				RetailDto dto = makeDto(transaction, resultSet);
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

	private RetailDto makeDto(Transaction transaction, ResultSet resultSet)
			throws SQLException, IOException {
		int seq = resultSet.getInt(1);
		int issueSeq = resultSet.getInt(2);
		double unitPrice = resultSet.getDouble(3);
		int quantity = resultSet.getInt(4);
		double discount = resultSet.getDouble(5);
		double moneyPay = resultSet.getDouble(6);
		String employeeID = resultSet.getString(7);
		String orderDateStr = resultSet.getString(8);
		int promotionSeq = resultSet.getInt(9);
		int age = resultSet.getInt(10);
		String sex = resultSet.getString(11);
		int occupationSeq = resultSet.getInt(12);
		int educationSeq = resultSet.getInt(13);

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

		RetailDto dto = new RetailDto(seq, issueDto, unitPrice,
				quantity, discount, moneyPay,
				workInfoDto, orderDate, promotion, age, sex, occupationSeq, educationSeq);
		return dto;
	}

	@Override
	public void add(Transaction transaction, RetailDto dto)
			throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setInt(1, dto.getIssue().getSeq());
			prepareStatement.setDouble(2, dto.getUnitPrice());
			prepareStatement.setInt(3, dto.getQuantity());
			prepareStatement.setDouble(4, dto.getDiscount());
			prepareStatement.setDouble(5, dto.getMoneyPay());
			prepareStatement.setString(6, dto.getEmployee().getId());
			prepareStatement.setString(7, DateUtil.getDateWithTimeFormatInstance().format(dto.getOrderDate()));
			prepareStatement.setInt(8, dto.getPromotion().getSeq());
			prepareStatement.setInt(9, dto.getAge());
			prepareStatement.setString(10, dto.getSex());
			prepareStatement.setInt(11, dto.getOccupationSeq());
			prepareStatement.setInt(12, dto.getEducationSeq());

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
	public void update(Transaction transaction, RetailDto dto)
			throws IOException {
		PreparedStatement prepareStatement = null;
		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(UPDATE);
			prepareStatement.setInt(1, dto.getIssue().getSeq());
			prepareStatement.setDouble(2, dto.getUnitPrice());
			prepareStatement.setInt(3, dto.getQuantity());
			prepareStatement.setDouble(4, dto.getDiscount());
			prepareStatement.setDouble(5, dto.getMoneyPay());
			prepareStatement.setString(6, dto.getEmployee().getId());
			prepareStatement.setString(7, DateUtil.getDateWithTimeFormatInstance().format(dto.getOrderDate()));
			prepareStatement.setInt(8, dto.getPromotion().getSeq());
			prepareStatement.setInt(10, dto.getAge());
			prepareStatement.setString(11, dto.getSex());
			prepareStatement.setInt(12, dto.getOccupationSeq());
			prepareStatement.setInt(13, dto.getEducationSeq());
			prepareStatement.setInt(14, dto.getSeq());

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
	private static final String GET_ALL = "select seq, issue_seq, unitprice, quantity, discount, moneypay, employee_id, order_date, promotion_seq, age, sex, occupation_seq, education_seq from n_retail where 1 = 1 ";
	private static final String GET_BY_SEQ = "select seq, issue_seq, unitprice, quantity, discount, moneypay, employee_id, order_date, promotion_seq, age, sex, occupation_seq, education_seq from n_retail where seq = ?";
	private static final String INSERT = "insert into n_retail (issue_seq, unitprice, quantity, discount, moneypay, employee_id, order_date, promotion_seq, age, sex, occupation_seq, education_seq) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "update n_retail set issue_seq = ?, unitprice = ?, quantity = ?, discount = ?, moneypay = ?, employee_id = ?, order_date = ?, promotion_seq = ?, age = ?, sex = ?, occupation_seq = ?, education_seq = ? where seq = ?";
	private static final String DELETE = "delete from n_retail where seq = ?";

}
