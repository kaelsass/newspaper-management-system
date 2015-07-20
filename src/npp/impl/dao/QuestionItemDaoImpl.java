package npp.impl.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import npp.dto.QuestionItemDto;
import npp.spec.dao.QuestionItemDao;
import npp.spec.dao.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Stateless
public class QuestionItemDaoImpl implements QuestionItemDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<QuestionItemDto> getAllList(Transaction transaction, int questionSeq) throws IOException {
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_ALL);
			prepareStatement.setInt(1, questionSeq);
			resultSet = prepareStatement.executeQuery();

			List<QuestionItemDto> items = new ArrayList<QuestionItemDto>();
			while (resultSet.next()) {

				QuestionItemDto dto = makeDto(transaction, resultSet);
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


	private QuestionItemDto makeDto(Transaction transaction, ResultSet resultSet) throws SQLException, IOException {
		int questionSeq = resultSet.getInt(1);
		String item = resultSet.getString(2);

		QuestionItemDto dto = new QuestionItemDto(questionSeq, item);
		return dto;
	}

	@Override
	public void add(Transaction transaction,
			QuestionItemDto dto) throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setInt(1, dto.getQuestionSeq());
			prepareStatement.setString(2, dto.getItem());
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
	public void delete(Transaction transaction, int questionSeq)
			throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(DELETE);
			prepareStatement.setInt(1, questionSeq);
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

	private static final String GET_ALL = "select question_seq, item from N_ITEM where question_seq = ?";
	private static final String INSERT = "insert into N_ITEM (question_seq, item) values ( ?, ?)";
	private static final String DELETE = "delete from N_ITEM where question_seq = ?";



}
