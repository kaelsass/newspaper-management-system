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
import npp.dto.QuestionDto;
import npp.dto.QuestionItemDto;
import npp.dto.StatusDto;
import npp.spec.dao.NquestionDao;
import npp.spec.dao.QuestionItemDao;
import npp.spec.dao.Transaction;
import npp.spec.dao.TypeDao;
import npp.utils.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Stateless
public class NquestionDaoImpl implements NquestionDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private QuestionItemDao questionItemDao;
	private TypeDao typeDao;

	@Inject
	public NquestionDaoImpl(QuestionItemDao questionItemDao, TypeDao typeDao)
	{
		this.questionItemDao = questionItemDao;
		this.typeDao = typeDao;
	}

	@Override
	public List<QuestionDto> getAllList(Transaction transaction, DynamicQuery query) throws IOException {
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			query.setBaseSql(GET_ALL);
			prepareStatement = connection.prepareStatement(query.generateSql());
			query.fillPreparedStatement(prepareStatement);
			resultSet = prepareStatement.executeQuery();

			List<QuestionDto> items = new ArrayList<QuestionDto>();
			while (resultSet.next()) {

				QuestionDto dto = makeDto(transaction, resultSet);
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

	private QuestionDto makeDto(Transaction transaction, ResultSet resultSet) throws SQLException, IOException {
		int seq = resultSet.getInt(1);
		String name = resultSet.getString(2);
		int typeSeq = resultSet.getInt(3);
		String addStr = resultSet.getString(4);
		String modStr = resultSet.getString(5);

		StatusDto type = typeDao.findBySeq(transaction, typeSeq);

		Date addDate = null;
		Date modDate = null;

		try {
			addDate = DateUtil.getDateFormatInstance().parse(addStr);
			modDate = DateUtil.getDateFormatInstance().parse(modStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<QuestionItemDto> list = questionItemDao.getAllList(transaction, seq);
		List<String> items = new ArrayList<String>();
		for(QuestionItemDto dto : list)
		{
			items.add(dto.getItem());
		}

		QuestionDto dto = new QuestionDto(seq, name, type, addDate, modDate, items);
		return dto;
	}


	@Override
	public QuestionDto findBySeq(Transaction transaction, int seq) throws IOException {
		QuestionDto dto = null;

		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_BY_SEQ);
			prepareStatement.setInt(1, seq);
			resultSet = prepareStatement.executeQuery();

			if(resultSet.next()) {
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
	public void add(Transaction transaction,
			QuestionDto dto) throws IOException {
		PreparedStatement prepareStatement = null;
		System.out.println("here");

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setString(1, dto.getName());
			prepareStatement.setInt(2, dto.getType().getSeq());
			prepareStatement.setString(3, dto.getFormatAddDate());
			prepareStatement.setString(4, dto.getFormatModDate());
			prepareStatement.executeUpdate();

			dto.setSeq(getNewInsertedSeq(transaction));

			questionItemDao.delete(transaction, dto.getSeq());
			for(String item : dto.getItems())
			{
				questionItemDao.add(transaction, new QuestionItemDto(dto.getSeq(), item));
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

	private int getNewInsertedSeq(Transaction transaction) throws IOException {
		int seq = 0;

		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_NEW_INSERTED_SEQ);
			resultSet = prepareStatement.executeQuery();

			if(resultSet.next()) {
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
	public void update(Transaction transaction,
			QuestionDto dto) throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(UPDATE);
			prepareStatement.setString(1, dto.getName());
			prepareStatement.setInt(2, dto.getType().getSeq());
			prepareStatement.setString(3, dto.getFormatAddDate());
			prepareStatement.setString(4, dto.getFormatModDate());
			prepareStatement.setInt(5, dto.getSeq());
			prepareStatement.executeUpdate();

			questionItemDao.delete(transaction, dto.getSeq());
			for(String item : dto.getItems())
			{
				questionItemDao.add(transaction, new QuestionItemDto(dto.getSeq(), item));
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

	private static final String GET_NEW_INSERTED_SEQ = "select max(seq) from N_QUESTION";
	private static final String GET_ALL = "select seq, name, type_seq, add_date, mod_date from N_QUESTION where 1 = 1 ";
	private static final String GET_BY_SEQ = "select seq, name, type_seq, add_date, mod_date from N_QUESTION where seq = ?";
	private static final String INSERT = "insert into N_QUESTION ( name, type_seq, add_date, mod_date) values ( ?, ?, ?, ?)";
	private static final String UPDATE = "update N_QUESTION set name = ?, type_seq = ?, add_date = ?, mod_date = ? where seq = ?";
	private static final String DELETE = "delete from N_QUESTION where seq = ?";

}
