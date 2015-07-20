package npp.impl.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import npp.condition.DynamicQuery;
import npp.dto.NewspaperDto;
import npp.dto.PayTypeDto;
import npp.dto.PromotionDto;
import npp.dto.SubscriberDto;
import npp.dto.WorkInfoDto;
import npp.spec.dao.NewspaperDao;
import npp.spec.dao.PayTypeDao;
import npp.spec.dao.PromotionDao;
import npp.spec.dao.SubscriberDao;
import npp.spec.dao.Transaction;
import npp.spec.dao.WorkInfoDao;
import npp.utils.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Stateless
public class SubscriberDaoImpl implements SubscriberDao {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private NewspaperDao newspaperDao;
	private WorkInfoDao workInfoDao;
	private PayTypeDao payTypeDao;
	private PromotionDao promotionDao;


	@Inject
	public SubscriberDaoImpl(NewspaperDao newspaperDao,
			WorkInfoDao workInfoDao, PayTypeDao payTypeDao, PromotionDao promotionDao) {
		this.newspaperDao = newspaperDao;
		this.workInfoDao = workInfoDao;
		this.payTypeDao = payTypeDao;
		this.promotionDao = promotionDao;
	}

	@Override
	public SubscriberDto findBySeq(Transaction transaction, int seq)
			throws IOException {
		SubscriberDto dto = null;
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
	public List<SubscriberDto> getAllList(Transaction transaction,
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

			List<SubscriberDto> items = new ArrayList<SubscriberDto>();
			while (resultSet.next()) {
				SubscriberDto userDto = makeDto(transaction, resultSet);
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

	private SubscriberDto makeDto(Transaction transaction, ResultSet resultSet)
			throws SQLException, IOException {
		int seq = resultSet.getInt(1);
		String name = resultSet.getString(2);
		String address = resultSet.getString(3);
		String zipcode = resultSet.getString(4);
		int newspaperSeq = resultSet.getInt(5);
		String dateFromStr = resultSet.getString(6);
		String dateToStr = resultSet.getString(7);
		String orderDateStr = resultSet.getString(8);
		int quantity = resultSet.getInt(9);
		double discount = resultSet.getDouble(10);
		double moneyPay = resultSet.getDouble(11);
		String employeeID = resultSet.getString(12);
		int payTypeSeq = resultSet.getInt(13);
		int promotionSeq = resultSet.getInt(14);
		int age = resultSet.getInt(15);
		String sex = resultSet.getString(16);
		int occupationSeq = resultSet.getInt(17);
		int educationSeq = resultSet.getInt(18);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateFrom = null;
		try {
			dateFrom = df.parse(dateFromStr);
		} catch (ParseException e) {
			dateFrom = null;
		}
		Date dateTo = null;
		try {
			dateTo = df.parse(dateToStr);
		} catch (ParseException e) {
			dateTo = null;
		}
		Date orderDate = null;
		try {
			orderDate = df.parse(orderDateStr);
		} catch (ParseException e) {
			orderDate = null;
		}
		int monthNumber = DateUtil.getMonthsBetweenDates(dateFrom, dateTo);

		NewspaperDto newspaperDto = newspaperDao.findBySeq(transaction,
				newspaperSeq);
		WorkInfoDto workInfoDto = workInfoDao.findByID(transaction, employeeID);
		PayTypeDto payTypeDto = payTypeDao.findBySeq(transaction, payTypeSeq);
		PromotionDto promotion = promotionDao.findBySeq(transaction, promotionSeq);

		SubscriberDto dto = new SubscriberDto(seq, name, address, zipcode,
				newspaperDto, dateFrom, monthNumber, orderDate, quantity,
				discount, moneyPay, workInfoDto, payTypeDto, promotion, age, sex, occupationSeq, educationSeq);
		return dto;
	}

	@Override
	public void add(Transaction transaction, SubscriberDto dto)
			throws IOException {
		PreparedStatement prepareStatement = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setString(1, dto.getName());
			prepareStatement.setString(2, dto.getAddress());
			prepareStatement.setString(3, dto.getZipcode());
			prepareStatement.setInt(4, dto.getNewspaper().getSeq());
			prepareStatement.setString(5, df.format(dto.getDateFrom()));
			Date dateTo = DateUtil.getDateAfterMonths(dto.getDateFrom(), dto.getMonthNumber());
			prepareStatement.setString(6, df.format(dateTo));
			prepareStatement.setString(7, df.format(dto.getOrderDate()));
			prepareStatement.setInt(8, dto.getQuantity());
			prepareStatement.setDouble(9, dto.getDiscount());
			prepareStatement.setDouble(10, dto.getMoneyPay());
			WorkInfoDto employee = workInfoDao.findByName(transaction, dto.getEmployee().getName());
			prepareStatement.setString(11, employee.getId());
			prepareStatement.setInt(12, dto.getPayType().getSeq());
			prepareStatement.setInt(13, dto.getPromotion().getSeq());
			prepareStatement.setInt(14, dto.getAge());
			prepareStatement.setString(15, dto.getSex());
			prepareStatement.setInt(16, dto.getOccupationSeq());
			prepareStatement.setInt(17, dto.getEducationSeq());

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
	public void update(Transaction transaction, SubscriberDto dto)
			throws IOException {
		PreparedStatement prepareStatement = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(UPDATE);
			prepareStatement.setString(1, dto.getName());
			prepareStatement.setString(2, dto.getAddress());
			prepareStatement.setString(3, dto.getZipcode());
			prepareStatement.setInt(4, dto.getNewspaper().getSeq());
			prepareStatement.setString(5, df.format(dto.getDateFrom()));
			Date dateTo = DateUtil.getDateAfterMonths(dto.getDateFrom(), dto.getMonthNumber());
			prepareStatement.setString(6, df.format(dateTo));
			prepareStatement.setString(7, df.format(dto.getOrderDate()));
			prepareStatement.setInt(8, dto.getQuantity());
			prepareStatement.setDouble(9, dto.getDiscount());
			prepareStatement.setDouble(10, dto.getMoneyPay());
			WorkInfoDto employee = workInfoDao.findByName(transaction, dto.getEmployee().getName());
			prepareStatement.setString(11, employee.getId());
			prepareStatement.setInt(12, dto.getPayType().getSeq());
			prepareStatement.setInt(13, dto.getPromotion().getSeq());
			prepareStatement.setInt(14, dto.getAge());
			prepareStatement.setString(15, dto.getSex());
			prepareStatement.setInt(16, dto.getOccupationSeq());
			prepareStatement.setInt(17, dto.getEducationSeq());
			prepareStatement.setInt(18, dto.getSeq());
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

	private static final String ORDER = " order by newspaper_seq, quantity";
	private static final String GET_ALL = "select seq, name, address, zipcode, newspaper_seq, date_from, date_to, order_date, quantity, discount, money_pay, employee_id, paytype_seq, promotion_seq, age, sex, occupation_seq, education_seq  from n_subscriber where 1 = 1 ";
	private static final String GET_BY_SEQ = "select seq, name, address, zipcode, newspaper_seq, date_from, date_to, order_date, quantity, discount, money_pay, employee_id, paytype_seq, promotion_seq, age, sex, occupation_seq, education_seq from n_subscriber where seq = ?";
	private static final String INSERT = "insert into n_subscriber (name, address, zipcode, newspaper_seq, date_from, date_to, order_date, quantity, discount, money_pay, employee_id, paytype_seq, promotion_seq, age, sex, occupation_seq, education_seq) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "update n_subscriber set name = ?, address = ?, zipcode = ?, newspaper_seq = ?, date_from = ?, date_to = ?, order_date = ?, quantity = ?, discount = ?, money_pay = ?, employee_id = ?, paytype_seq = ?, promotion_seq = ?, age = ?, sex = ?, occupation_seq = ?, education_seq = ? where seq = ?";
	private static final String DELETE = "delete from n_subscriber where seq = ?";

}
