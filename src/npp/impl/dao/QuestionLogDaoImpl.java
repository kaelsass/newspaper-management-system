package npp.impl.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import npp.dto.QuestionDto;
import npp.dto.QuestionLogDto;
import npp.spec.dao.QuestionLogDao;
import npp.spec.dao.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Stateless
public class QuestionLogDaoImpl implements QuestionLogDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<QuestionLogDto> getAllList(Transaction transaction, int recordSeq, QuestionDto dto) throws IOException {
		List<QuestionLogDto> list = new ArrayList<QuestionLogDto>();
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_ALL);
			prepareStatement.setInt(1, recordSeq);
			prepareStatement.setInt(2, dto.getSeq());
			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				QuestionLogDto log = makeDto(resultSet);
				list.add(log);
			}
			return list;
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

	private QuestionLogDto makeDto(ResultSet resultSet) throws SQLException {
		int recordSeq = resultSet.getInt(1);
		int questionSeq = resultSet.getInt(2);
		String item = resultSet.getString(3);
		String answer = resultSet.getString(4);

		return new QuestionLogDto(recordSeq, questionSeq, item, answer);
	}

	@Override
	public void add(Transaction transaction,
			QuestionLogDto log) throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setInt(1, log.getRecordSeq());
			prepareStatement.setInt(2, log.getQuestionSeq());
			prepareStatement.setString(3, log.getItem());
			prepareStatement.setString(4, log.getAnswer());
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
	public void delete(Transaction transaction, QuestionLogDto log)
			throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(DELETE);
			prepareStatement.setInt(1, log.getRecordSeq());
			prepareStatement.setInt(2, log.getQuestionSeq());
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

	private static final String GET_ALL = "select record_seq, question_seq, item, answer from N_QUESTION_LOG where record_seq = ? and  question_seq = ? ";
	private static final String INSERT = "insert into N_QUESTION_LOG (record_seq, question_seq, item, answer) values ( ?, ?, ?, ?)";
	private static final String DELETE = "delete from N_QUESTION_LOG where record_seq = ? and  question_seq = ?";

}
