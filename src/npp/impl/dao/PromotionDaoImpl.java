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
import npp.dto.NewspaperDto;
import npp.dto.PromotionAdminDto;
import npp.dto.PromotionDto;
import npp.dto.PromotionNewspaperDto;
import npp.dto.WorkInfoDto;
import npp.spec.dao.NewspaperDao;
import npp.spec.dao.PromotionAdminDao;
import npp.spec.dao.PromotionDao;
import npp.spec.dao.PromotionNewspaperDao;
import npp.spec.dao.Transaction;
import npp.spec.dao.WorkInfoDao;
import npp.utils.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Stateless
public class PromotionDaoImpl implements PromotionDao {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private NewspaperDao newspaperDao;
	private WorkInfoDao workInfoDao;
	private PromotionNewspaperDao pnDao;
	private PromotionAdminDao paDao;

	@Inject
	public PromotionDaoImpl(NewspaperDao newspaperDao, WorkInfoDao workInfoDao, PromotionNewspaperDao pnDao, PromotionAdminDao paDao) {
		this.newspaperDao = newspaperDao;
		this.workInfoDao = workInfoDao;
		this.pnDao = pnDao;
		this.paDao = paDao;
	}

	@Override
	public PromotionDto findBySeq(Transaction transaction, int seq)
			throws IOException {
		PromotionDto dto = null;
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
			prepareStatement = connection.prepareStatement(GET_NEW_INSERTED_SEQ);
			resultSet = prepareStatement.executeQuery();

			if (resultSet.next()) {
				seq = resultSet.getInt(1);
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
	public List<PromotionDto> getAllList(Transaction transaction, DynamicQuery query)
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

			List<PromotionDto> items = new ArrayList<PromotionDto>();
			while (resultSet.next()) {
				PromotionDto dto = makeDto(transaction, resultSet);
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
	public List<PromotionDto> getAllEditList(Transaction transaction, DynamicQuery query)
			throws IOException {
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			query.setBaseSql(GET_EDIT_ALL);
			query.setSuffix(ORDER);
			prepareStatement = connection.prepareStatement(query.generateSql());
			query.fillPreparedStatement(prepareStatement);

			resultSet = prepareStatement.executeQuery();

			List<PromotionDto> items = new ArrayList<PromotionDto>();
			while (resultSet.next()) {
				PromotionDto dto = makeDto(transaction, resultSet);
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

	private PromotionDto makeDto(Transaction transaction, ResultSet resultSet)
			throws SQLException, IOException {
		// id, work_unit, address, newspaper_seq, page_count, page_area,
		// date_from, dateTo,
		// unit_price, discount, money_pay, contact_person, phone, email,
		// employee_id, order_date
		int seq = resultSet.getInt(1);
		String name = resultSet.getString(2);
		String place = resultSet.getString(3);
		String dateFromStr = resultSet.getString(4);
		String dateToStr = resultSet.getString(5);
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

		List<PromotionNewspaperDto> pnds = pnDao.findByPromotionSeq(transaction, seq);
		List<PromotionAdminDto> pads = paDao.findByPromotionSeq(transaction, seq);

		List<NewspaperDto> newspapers = new ArrayList<NewspaperDto>();
		List<WorkInfoDto> admins = new ArrayList<WorkInfoDto>();
		for(PromotionNewspaperDto dto : pnds)
		{
			NewspaperDto newspaperDto = newspaperDao.findBySeq(transaction,
					dto.getNewspaperSeq());
			newspapers.add(newspaperDto);
		}
		for(PromotionAdminDto dto : pads)
		{
			WorkInfoDto admin = workInfoDao.findByID(transaction,
					dto.getEmployeeID());
			admins.add(admin);
		}

		PromotionDto dto = new PromotionDto(seq, name, place, newspapers, admins, dateFrom, dateTo);
		return dto;
	}

	@Override
	public void add(Transaction transaction, PromotionDto dto)
			throws IOException {
		PreparedStatement prepareStatement = null;

		try {

			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setString(1, dto.getName());
			prepareStatement.setString(2, dto.getPlace());
			prepareStatement.setString(3, DateUtil.getDateWithTimeFormatInstance().format(dto.getDateFrom()));
			prepareStatement.setString(4, DateUtil.getDateWithTimeFormatInstance().format(dto.getDateTo()));
			prepareStatement.executeUpdate();

			int seq = getNewInsertedSeq(transaction);
			for(NewspaperDto newspaper : dto.getNewspapers())
			{
				PromotionNewspaperDto nd = new PromotionNewspaperDto(seq, newspaper.getSeq());
				pnDao.add(transaction, nd);
			}
			for(WorkInfoDto employee : dto.getAdmins())
			{
				PromotionAdminDto nd = new PromotionAdminDto(seq, employee.getId());
				paDao.add(transaction, nd);
			}

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
	public void update(Transaction transaction, PromotionDto dto) throws IOException {
		PreparedStatement prepareStatement = null;
		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(UPDATE);
			prepareStatement.setString(1, dto.getName());
			prepareStatement.setString(2, dto.getPlace());
			prepareStatement.setString(3, DateUtil.getDateWithTimeFormatInstance().format(dto.getDateFrom()));
			prepareStatement.setString(4, DateUtil.getDateWithTimeFormatInstance().format(dto.getDateTo()));
			prepareStatement.setInt(5, dto.getSeq());

			pnDao.delete(transaction, dto.getSeq());
			paDao.delete(transaction, dto.getSeq());
			for(NewspaperDto newspaper : dto.getNewspapers())
			{
				PromotionNewspaperDto nd = new PromotionNewspaperDto(dto.getSeq(), newspaper.getSeq());
				pnDao.add(transaction, nd);
			}
			for(WorkInfoDto employee : dto.getAdmins())
			{
				PromotionAdminDto nd = new PromotionAdminDto(dto.getSeq(), employee.getId());
				paDao.add(transaction, nd);
			}

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

	private static final String GET_NEW_INSERTED_SEQ = "select max(seq) from N_PROMOTION";
	private static final String ORDER = " order by name";
	private static final String GET_ALL = "select seq, name, place, start_date, end_date from N_PROMOTION where 1 = 1 ";
	private static final String GET_EDIT_ALL = "select seq, name, place, start_date, end_date from N_PROMOTION where seq != 1 and seq != 2 ";
	private static final String GET_BY_SEQ = "select seq, name, place, start_date, end_date from N_PROMOTION where seq = ?";
	private static final String INSERT = "insert into N_PROMOTION (name, place, start_date, end_date) values (?, ?, ?, ?)";
	private static final String UPDATE = "update N_PROMOTION set name = ?, place = ?, start_date = ?, end_date = ? where seq = ?";
	private static final String DELETE = "delete from N_PROMOTION where seq = ?";

}
