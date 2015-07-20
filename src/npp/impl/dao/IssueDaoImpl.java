package npp.impl.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import npp.condition.DynamicQuery;
import npp.dto.IssueDto;
import npp.dto.NewspaperDto;
import npp.dto.PublicationDateDto;
import npp.dto.StatusDto;
import npp.spec.dao.IssueDao;
import npp.spec.dao.NStatusDao;
import npp.spec.dao.NewspaperDao;
import npp.spec.dao.Transaction;
import npp.utils.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Stateless
public class IssueDaoImpl implements IssueDao {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private NewspaperDao newspaperDao;
	private NStatusDao statusDao;

	@Inject
	public IssueDaoImpl(NewspaperDao dao, NStatusDao statusDao) {
		this.newspaperDao = dao;
		this.statusDao = statusDao;
	}

	@Override
	public IssueDto findBySeq(Transaction transaction, int seq)
			throws IOException {
		IssueDto dto = null;
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
	public List<IssueDto> getAllList(Transaction transaction, DynamicQuery query)
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

			List<IssueDto> items = new ArrayList<IssueDto>();
			while (resultSet.next()) {
				IssueDto dto = makeDto(transaction, resultSet);
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

	private IssueDto makeDto(Transaction transaction, ResultSet resultSet)
			throws SQLException, IOException {
		int seq = resultSet.getInt(1);
		int number = resultSet.getInt(2);
		String timeStr = resultSet.getString(3);
		Date time = null;
		try {
			time = DateUtil.getDateWithTimeFormatInstance().parse(timeStr);
		} catch (ParseException e) {
			time = null;
		}
		int quantity = resultSet.getInt(4);
		int newspaperSeq = resultSet.getInt(5);
		int statusSeq = resultSet.getInt(6);

		NewspaperDto newspaperDto = newspaperDao.findBySeq(transaction,
				newspaperSeq);
		StatusDto status = statusDao.findBySeq(transaction, statusSeq);

		IssueDto dto = new IssueDto(seq, number, time, quantity, newspaperDto, status);
		return dto;
	}

	@Override
	public void add(Transaction transaction, IssueDto dto) throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setInt(1, dto.getNumber());
			prepareStatement.setString(2, DateUtil.getDateWithTimeFormatInstance().format(dto.getTime()));
			prepareStatement.setInt(3, dto.getQuantity());
			prepareStatement.setInt(4, dto.getNewspaper().getSeq());
			prepareStatement.setInt(5, dto.getStatus().getSeq());

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
	public void update(Transaction transaction, IssueDto dto)
			throws IOException {
		PreparedStatement prepareStatement = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(UPDATE);
			prepareStatement.setInt(1, dto.getNumber());
			prepareStatement.setString(2, df.format(dto.getTime()));
			prepareStatement.setInt(3, dto.getQuantity());
			prepareStatement.setInt(4, dto.getNewspaper().getSeq());
			prepareStatement.setInt(5, dto.getStatus().getSeq());
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

	@Override
	public IssueDto getNextIssueByNewspaper(Transaction transaction,
			int newspaper_seq) throws IOException {
		IssueDto dto = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_MAX_ISSUE);
			prepareStatement.setInt(1, newspaper_seq);
			resultSet = prepareStatement.executeQuery();

			dto = makeNextDto(transaction, resultSet);
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

	private IssueDto makeNextDto(Transaction transaction, ResultSet resultSet)
			throws SQLException, IOException {
		IssueDto dto = new IssueDto();
		if (resultSet.next()) {
			int newspaperSeq = resultSet.getInt(1);
			int number = resultSet.getInt(2);
			String time = resultSet.getString(3);

			NewspaperDto newspaperDto = newspaperDao.findBySeq(transaction,
					newspaperSeq);
			Date nextTime = getNextTime(time,
					newspaperDto.getPdate().getType(), newspaperDto.getPdate()
							.getDay());

			//dto.setNewspaper(newspaperDto);
			dto.setNumber(number + 1);
			dto.setTime(nextTime);
		}
		else
		{
			dto.setNumber(1);
			dto.setTime(new Date());
		}
		return dto;
	}

	private Date getNextTime(String time, int type, int day) {

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(df.parse(time));
		} catch (ParseException e) {
			return new Date();
		}

		if (type == PublicationDateDto.MONTH_TYPE) {
			c.add(Calendar.MONTH, 1);
			c.set(Calendar.DAY_OF_MONTH, day);
		} else if (type == PublicationDateDto.WEEK_TYPE) {
			c.add(Calendar.WEEK_OF_YEAR, 1);
			c.set(Calendar.DAY_OF_WEEK, day);// 设置为1号,当前日期既为本月第一天
		}

		return c.getTime();
	}

	private static final String ORDER = " order by newspaper_seq, time";
	private static final String GET_ALL = "select seq, num, time, quantity, newspaper_seq, status_seq from n_issue where 1 = 1 ";
	private static final String GET_BY_SEQ = "select seq, num, time, quantity, newspaper_seq, status_seq from n_issue where seq = ?";
	private static final String INSERT = "insert into n_issue (num, time, quantity, newspaper_seq, status_seq) values (?, ?, ?, ?, ?)";
	private static final String UPDATE = "update n_issue set num = ?, time = ?, quantity = ?,newspaper_seq = ?, status_seq = ? where seq = ?";
	private static final String DELETE = "delete from n_issue where seq = ?";
	private static final String GET_MAX_ISSUE = "select newspaper_seq, max(num), max(time) from n_issue where newspaper_seq = ? group by newspaper_seq";

}
