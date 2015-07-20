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
import npp.dto.AdDto;
import npp.dto.NewspaperDto;
import npp.dto.PromotionDto;
import npp.dto.PublicationDateDto;
import npp.dto.WorkInfoDto;
import npp.spec.dao.AdDao;
import npp.spec.dao.NewspaperDao;
import npp.spec.dao.PromotionDao;
import npp.spec.dao.Transaction;
import npp.spec.dao.WorkInfoDao;
import npp.utils.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Stateless
public class AdDaoImpl implements AdDao {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private NewspaperDao newspaperDao;
	private WorkInfoDao workInfoDao;
	private PromotionDao promotionDao;

	@Inject
	public AdDaoImpl(NewspaperDao newspaperDao, WorkInfoDao workInfoDao,
			PromotionDao promotionDao) {
		this.newspaperDao = newspaperDao;
		this.workInfoDao = workInfoDao;
		this.promotionDao = promotionDao;
	}

	@Override
	public AdDto findByID(Transaction transaction, String ID)
			throws IOException {
		AdDto dto = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_BY_SEQ);
			prepareStatement.setString(1, ID);
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
	public List<AdDto> getAllList(Transaction transaction, DynamicQuery query)
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

			List<AdDto> items = new ArrayList<AdDto>();
			while (resultSet.next()) {
				AdDto dto = makeDto(transaction, resultSet);
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

	private AdDto makeDto(Transaction transaction, ResultSet resultSet)
			throws SQLException, IOException {
		// id, work_unit, address, newspaper_seq, page_count, page_area,
		// date_from, dateTo,
		// unit_price, discount, money_pay, contact_person, phone, email,
		// employee_id, order_date
		String id = resultSet.getString(1);
		String workUnit = resultSet.getString(2);
		String address = resultSet.getString(3);
		int newspaperSeq = resultSet.getInt(4);
		int pageCount = resultSet.getInt(5);
		int pageArea = resultSet.getInt(6);
		String dateFromStr = resultSet.getString(7);
		String dateToStr = resultSet.getString(8);
		double unitPrice = resultSet.getDouble(9);
		double discount = resultSet.getDouble(10);
		double moneyPay = resultSet.getDouble(11);
		String contactPerson = resultSet.getString(12);
		String phone = resultSet.getString(13);
		String email = resultSet.getString(14);
		String employeeID = resultSet.getString(15);
		String orderDateStr = resultSet.getString(16);
		int promotionSeq = resultSet.getInt(17);
		Date dateFrom = null;
		try {
			dateFrom = DateUtil.getDateWithTimeFormatInstance().parse(
					dateFromStr);
		} catch (ParseException e) {
			dateFrom = null;
		}
		Date dateTo = null;
		try {
			dateTo = DateUtil.getDateWithTimeFormatInstance().parse(dateToStr);
		} catch (ParseException e) {
			dateTo = null;
		}
		Date orderDate = null;
		try {
			orderDate = DateUtil.getDateWithTimeFormatInstance().parse(
					orderDateStr);
		} catch (ParseException e) {
			orderDate = null;
		}

		NewspaperDto newspaperDto = newspaperDao.findBySeq(transaction,
				newspaperSeq);
		WorkInfoDto workInfoDto = workInfoDao.findByID(transaction, employeeID);
		PromotionDto promotion = promotionDao.findBySeq(transaction,
				promotionSeq);

		int count = 0;
		if (newspaperDto.getPdate().getType() == 1) {
			count = DateUtil.getMonthsBetweenDates(dateFrom, dateTo);
		} else if (newspaperDto.getPdate().getType() == 2) {
			count = DateUtil.getWeeksBetweenDates(dateFrom, dateTo);
		} else {
			count = DateUtil.getDaysBetweenDates(dateFrom, dateTo);
		}

		AdDto dto = new AdDto(id, workUnit, address, newspaperDto, pageCount,
				pageArea, dateFrom, count, unitPrice, discount, moneyPay,
				contactPerson, phone, email, workInfoDto, orderDate, promotion);
		return dto;
	}

	@Override
	public void add(Transaction transaction, AdDto dto) throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			// id, work_unit, address, newspaper_seq, page_count, page_area,
			// date_from, dateTo,
			// unit_price, discount, money_pay, contact_person, phone, email,
			// employee_id, order_date
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setString(1, dto.getID());
			prepareStatement.setString(2, dto.getWorkUnit());
			prepareStatement.setString(3, dto.getAddress());
			prepareStatement.setInt(4, dto.getNewspaper().getSeq());
			prepareStatement.setInt(5, dto.getPageCount());
			prepareStatement.setInt(6, dto.getPageArea());
			prepareStatement.setString(7, DateUtil
					.getDateWithTimeFormatInstance().format(dto.getDateFrom()));
			Date dateTo = new Date();
			if (dto.getNewspaper().getPdate().getType() == PublicationDateDto.MONTH_TYPE) {
				dateTo = DateUtil.getDateAfterMonths(dto.getDateFrom(),
						dto.getCount());
			} else if (dto.getNewspaper().getPdate().getType() == PublicationDateDto.WEEK_TYPE) {
				dateTo = DateUtil.getDateAfterWeeks(dto.getDateFrom(),
						dto.getCount());
			} else {
				dateTo = DateUtil.getDateAfterDays(dto.getDateFrom(),
						dto.getCount());
			}
			prepareStatement.setString(8, DateUtil
					.getDateWithTimeFormatInstance().format(dateTo));
			prepareStatement.setDouble(9, dto.getUnitPrice());
			prepareStatement.setDouble(10, dto.getDiscount());
			prepareStatement.setDouble(11, dto.getMoneyPay());
			prepareStatement.setString(12, dto.getContactPerson());
			prepareStatement.setString(13, dto.getPhone());
			prepareStatement.setString(14, dto.getEmail());
			prepareStatement.setString(15, dto.getEmployee().getId());
			prepareStatement
					.setString(16, DateUtil.getDateWithTimeFormatInstance()
							.format(dto.getOrderDate()));
			prepareStatement.setInt(17, dto.getPromotion().getSeq());

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
	public void update(Transaction transaction, AdDto dto) throws IOException {
		PreparedStatement prepareStatement = null;
		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(UPDATE);
			prepareStatement.setString(1, dto.getWorkUnit());
			prepareStatement.setString(2, dto.getAddress());
			prepareStatement.setInt(3, dto.getNewspaper().getSeq());
			prepareStatement.setInt(4, dto.getPageCount());
			prepareStatement.setInt(5, dto.getPageArea());
			prepareStatement.setString(6, DateUtil
					.getDateWithTimeFormatInstance().format(dto.getDateFrom()));
			Date dateTo = new Date();
			if (dto.getNewspaper().getPdate().getType() == PublicationDateDto.MONTH_TYPE) {
				dateTo = DateUtil.getDateAfterMonths(dto.getDateFrom(),
						dto.getCount());
			} else if (dto.getNewspaper().getPdate().getType() == PublicationDateDto.WEEK_TYPE) {
				dateTo = DateUtil.getDateAfterWeeks(dto.getDateFrom(),
						dto.getCount());
			} else {
				dateTo = DateUtil.getDateAfterDays(dto.getDateFrom(),
						dto.getCount());
			}
			prepareStatement.setString(7, DateUtil
					.getDateWithTimeFormatInstance().format(dateTo));
			prepareStatement.setDouble(8, dto.getUnitPrice());
			prepareStatement.setDouble(9, dto.getDiscount());
			prepareStatement.setDouble(10, dto.getMoneyPay());
			prepareStatement.setString(11, dto.getContactPerson());
			prepareStatement.setString(12, dto.getPhone());
			prepareStatement.setString(13, dto.getEmail());
			prepareStatement.setString(14, dto.getEmployee().getId());
			prepareStatement
					.setString(15, DateUtil.getDateWithTimeFormatInstance()
							.format(dto.getOrderDate()));
			prepareStatement.setInt(16, dto.getPromotion().getSeq());

			prepareStatement.setString(17, dto.getID());

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
	public void delete(Transaction transaction, String id) throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(DELETE);
			prepareStatement.setString(1, id);
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

	private static final String ORDER = " order by newspaper_seq";
	private static final String GET_ALL = "select id, work_unit, address, newspaper_seq, page_count, page_area, date_from, date_to, unit_price, discount, money_pay, contact_person, phone, email, employee_id, order_date, promotion_seq from n_ad where 1 = 1 ";
	private static final String GET_BY_SEQ = "select id, work_unit, address, newspaper_seq, page_count, page_area, date_from, date_to, unit_price, discount, money_pay, contact_person, phone, email, employee_id, order_date, promotion_seq from n_ad where seq = ?";
	private static final String INSERT = "insert into n_ad (id, work_unit, address, newspaper_seq, page_count, page_area, date_from, date_to, unit_price, discount, money_pay, contact_person, phone, email, employee_id, order_date, promotion_seq) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "update n_ad set work_unit = ?, address = ?, newspaper_seq = ?, page_count = ?, page_area = ?, date_from = ?, date_to = ?, unit_price = ?, discount = ?, money_pay = ?, contact_person = ?, phone = ?, email = ?, employee_id = ?, order_date = ?, promotion_seq=? where id = ?";
	private static final String DELETE = "delete from n_ad where id = ?";

}
