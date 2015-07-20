package npp.impl.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import npp.dto.AqDto;
import npp.dto.StatusDto;
import npp.spec.dao.AppraisalQuestionDao;
import npp.spec.dao.QuestionDao;
import npp.spec.dao.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Stateless
public class AppraisalQuestionDaoImpl implements AppraisalQuestionDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private QuestionDao questionDao;

	@Inject
	public AppraisalQuestionDaoImpl(QuestionDao questionDao)
	{
		this.questionDao = questionDao;
	}
	@Override
	public List<AqDto> getAllList(Transaction transaction, int appraisalSeq) throws IOException {
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_ALL);
			prepareStatement.setInt(1, appraisalSeq);
			resultSet = prepareStatement.executeQuery();

			List<AqDto> items = new ArrayList<AqDto>();
			while (resultSet.next()) {

				AqDto dto = makeDto(transaction, resultSet);
				items.add(dto);
			}
			List<StatusDto> bases = questionDao.getAllList(transaction);
			List<AqDto> allList = new ArrayList<AqDto>();
			for(StatusDto cur : bases)
			{
				AqDto dto = new AqDto(appraisalSeq, cur, 0, false);
				allList.add(dto);
			}
			for(int i = 0; i < allList.size(); i++)
			{
				for(AqDto selected : items)
				{
					if(selected.getQuestion().getSeq() == allList.get(i).getQuestion().getSeq())
					{
						allList.set(i, selected);
					}
				}
			}

			return allList;
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

	private AqDto makeDto(Transaction transaction, ResultSet resultSet) throws SQLException, IOException {
		int appraisalSeq = resultSet.getInt(1);
		int questionSeq = resultSet.getInt(2);
		int ratio = resultSet.getInt(3);

		StatusDto question = questionDao.findBySeq(transaction, questionSeq);

		AqDto dto = new AqDto(appraisalSeq, question, ratio, true);
		return dto;
	}

	@Override
	public void add(Transaction transaction,
			AqDto dto) throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setInt(1, dto.getAppraisalSeq());
			prepareStatement.setInt(2, dto.getQuestion().getSeq());
			prepareStatement.setInt(3, dto.getRatio());
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
	public void delete(Transaction transaction, int appraisalSeq)
			throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(DELETE);
			prepareStatement.setInt(1, appraisalSeq);
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

	private static final String GET_ALL = "select appraisal_seq, question_seq, ratio from S_APPRAISAL_QUESTION where appraisal_seq = ?";
	private static final String INSERT = "insert into S_APPRAISAL_QUESTION (appraisal_seq, question_seq, ratio) values ( ?, ?, ?)";
	private static final String DELETE = "delete from S_APPRAISAL_QUESTION where appraisal_seq = ?";

}
