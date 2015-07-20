package npp.impl.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import npp.dto.QqDto;
import npp.dto.QuestionDto;
import npp.spec.dao.NquestionDao;
import npp.spec.dao.QuestionnaireQuestionDao;
import npp.spec.dao.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Stateless
public class QuestionnaireQuestionDaoImpl implements QuestionnaireQuestionDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private NquestionDao questionDao;

	@Inject
	public QuestionnaireQuestionDaoImpl(NquestionDao questionDao)
	{
		this.questionDao = questionDao;
	}
	@Override
	public List<QqDto> getAllList(Transaction transaction, int qustionnaireSeq) throws IOException {
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_ALL);
			prepareStatement.setInt(1, qustionnaireSeq);
			resultSet = prepareStatement.executeQuery();

			List<QqDto> items = new ArrayList<QqDto>();
			while (resultSet.next()) {

				QqDto dto = makeDto(transaction, resultSet);
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

	private QqDto makeDto(Transaction transaction, ResultSet resultSet) throws SQLException, IOException {
		int questionnaireSeq = resultSet.getInt(1);
		int questionSeq = resultSet.getInt(2);
		int index = resultSet.getInt(3);

		QuestionDto question = questionDao.findBySeq(transaction, questionSeq);
		if(question != null)
			question.setIndex(index);

		QqDto dto = new QqDto(questionnaireSeq, question);
		return dto;
	}

	@Override
	public void add(Transaction transaction,
			QqDto dto) throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setInt(1, dto.getQuestionnaireSeq());
			prepareStatement.setInt(2, dto.getQuestion().getSeq());
			prepareStatement.setInt(3, dto.getQuestion().getIndex());
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
	public void delete(Transaction transaction, int questionnaire_seq)
			throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(DELETE);
			prepareStatement.setInt(1, questionnaire_seq);
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

	private static final String GET_ALL = "select questionnaire_seq, question_seq, index_number from N_QUESTIONNAIRE_QUESTION where questionnaire_seq = ? order by index_number";
	private static final String INSERT = "insert into N_QUESTIONNAIRE_QUESTION (questionnaire_seq, question_seq, index_number) values (?, ?, ?)";
	private static final String DELETE = "delete from N_QUESTIONNAIRE_QUESTION where questionnaire_seq = ?";



}
