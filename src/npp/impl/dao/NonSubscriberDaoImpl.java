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
import npp.dto.NonSubscriberDto;
import npp.dto.PromotionDto;
import npp.dto.PurposeDto;
import npp.dto.WorkInfoDto;
import npp.spec.dao.IssueDao;
import npp.spec.dao.NonSubscriberDao;
import npp.spec.dao.PromotionDao;
import npp.spec.dao.Transaction;
import npp.spec.dao.WorkInfoDao;
import npp.spec.service.PurposeDao;
import npp.utils.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Stateless
public class NonSubscriberDaoImpl implements NonSubscriberDao {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private IssueDao issueDao;
	private WorkInfoDao workInfoDao;
	private PurposeDao purposeDao;
	private PromotionDao promotionDao;

	@Inject
	public NonSubscriberDaoImpl(IssueDao issueDao,
			WorkInfoDao workInfoDao, PurposeDao purposeDao, PromotionDao promotionDao) {
		this.issueDao = issueDao;
		this.workInfoDao = workInfoDao;
		this.purposeDao = purposeDao;
		this.promotionDao = promotionDao;
	}

	@Override
	public NonSubscriberDto findBySeq(Transaction transaction, int seq)
			throws IOException {
		NonSubscriberDto dto = null;
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
	public List<NonSubscriberDto> getAllList(Transaction transaction,
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

			List<NonSubscriberDto> items = new ArrayList<NonSubscriberDto>();
			while (resultSet.next()) {
				NonSubscriberDto userDto = makeDto(transaction, resultSet);
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

	private NonSubscriberDto makeDto(Transaction transaction, ResultSet resultSet)
			throws SQLException, IOException {
		int seq = resultSet.getInt(1);
		String name = resultSet.getString(2);
		String address = resultSet.getString(3);
		String zipcode = resultSet.getString(4);
		String workunit = resultSet.getString(5);
		int issueSeq = resultSet.getInt(6);
		int quantity = resultSet.getInt(7);
		int purposeSeq = resultSet.getInt(8);
		String phone = resultSet.getString(9);
		String email = resultSet.getString(10);
		String employeeID = resultSet.getString(11);
		String orderDateStr = resultSet.getString(12);
		int promotionSeq = resultSet.getInt(13);
		int age = resultSet.getInt(14);
		String sex = resultSet.getString(15);
		int occupationSeq = resultSet.getInt(16);
		int educationSeq = resultSet.getInt(17);

		Date orderDate = null;
		try {
			orderDate = DateUtil.getDateWithTimeFormatInstance().parse(orderDateStr);
		} catch (ParseException e) {
			orderDate = null;
		}

		IssueDto issueDto = issueDao.findBySeq(transaction,
				issueSeq);
		WorkInfoDto workInfoDto = workInfoDao.findByID(transaction, employeeID);
		PurposeDto purposeDto = purposeDao.findBySeq(transaction, purposeSeq);
		PromotionDto promotion = promotionDao.findBySeq(transaction, promotionSeq);

		NonSubscriberDto dto = new NonSubscriberDto(seq, name, address, zipcode,
				workunit, issueDto, quantity, purposeDto, phone, email,
				workInfoDto, orderDate, promotion, age, sex, occupationSeq, educationSeq);
		return dto;
	}

	@Override
	public void add(Transaction transaction, NonSubscriberDto dto)
			throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setString(1, dto.getName());
			prepareStatement.setString(2, dto.getAddress());
			prepareStatement.setString(3, dto.getZipcode());
			prepareStatement.setString(4, dto.getWorkunit());
			prepareStatement.setInt(5, dto.getIssue().getSeq());
			prepareStatement.setInt(6, dto.getQuantity());
			prepareStatement.setInt(7, dto.getPurpose().getSeq());
			prepareStatement.setString(8, dto.getPhone());
			prepareStatement.setString(9, dto.getEmail());
			prepareStatement.setString(10, dto.getEmployee().getId());
			prepareStatement.setString(11, DateUtil.getDateWithTimeFormatInstance().format(dto.getOrderDate()));
			prepareStatement.setInt(12, dto.getPromotion().getSeq());
			prepareStatement.setInt(13, dto.getAge());
			prepareStatement.setString(14, dto.getSex());
			prepareStatement.setInt(15, dto.getOccupationSeq());
			prepareStatement.setInt(16, dto.getEducationSeq());

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
	public void update(Transaction transaction, NonSubscriberDto dto)
			throws IOException {
		PreparedStatement prepareStatement = null;
		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(UPDATE);
			prepareStatement.setString(1, dto.getName());
			prepareStatement.setString(2, dto.getAddress());
			prepareStatement.setString(3, dto.getZipcode());
			prepareStatement.setString(4, dto.getWorkunit());
			prepareStatement.setInt(5, dto.getIssue().getSeq());
			prepareStatement.setInt(6, dto.getQuantity());
			prepareStatement.setInt(7, dto.getPurpose().getSeq());
			prepareStatement.setString(8, dto.getPhone());
			prepareStatement.setString(9, dto.getEmail());
			prepareStatement.setString(10, dto.getEmployee().getId());
			prepareStatement.setString(11, DateUtil.getDateWithTimeFormatInstance().format(dto.getOrderDate()));
			prepareStatement.setInt(12, dto.getPromotion().getSeq());
			prepareStatement.setInt(13, dto.getAge());
			prepareStatement.setString(14, dto.getSex());
			prepareStatement.setInt(15, dto.getOccupationSeq());
			prepareStatement.setInt(16, dto.getEducationSeq());
			prepareStatement.setInt(17, dto.getSeq());
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
	private static final String GET_ALL = "select seq, name, address, zipcode, workunit, issue_seq, quantity, purpose_seq, phone, email, employee_id, order_date, promotion_seq, age, sex, occupation_seq, education_seq from n_nonsubscriber where 1 = 1 ";
	private static final String GET_BY_SEQ = "select seq, name, address, zipcode, workunit, issue_seq, quantity, purpose_seq, phone, email, employee_id, order_date, promotion_seq, age, sex, occupation_seq, education_seq from n_nonsubscriber where seq = ?";
	private static final String INSERT = "insert into n_nonsubscriber (name, address, zipcode, workunit, issue_seq, quantity, purpose_seq, phone, email, employee_id, order_date, promotion_seq, age, sex, occupation_seq, education_seq) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "update n_nonsubscriber set name = ?, address = ?, zipcode = ?, workunit = ?, issue_seq = ?, quantity = ?, purpose_seq = ?, phone = ?, email = ?, employee_id = ?, order_date = ?, promotion_seq = ?, age = ?, sex = ?, occupation_seq = ?, education_seq = ? where seq = ?";
	private static final String DELETE = "delete from n_nonsubscriber where seq = ?";

}
