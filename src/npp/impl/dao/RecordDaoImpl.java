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
import npp.dto.QqDto;
import npp.dto.QuestionDto;
import npp.dto.QuestionLogDto;
import npp.dto.RecordDto;
import npp.spec.dao.QuestionLogDao;
import npp.spec.dao.QuestionnaireQuestionDao;
import npp.spec.dao.RecordDao;
import npp.spec.dao.Transaction;
import npp.utils.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Stateless
public class RecordDaoImpl implements RecordDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private QuestionnaireQuestionDao qqDao;
	private QuestionLogDao questionLogDao;

	@Inject
	public RecordDaoImpl(QuestionnaireQuestionDao qqDao, QuestionLogDao questionLogDao)
	{
		this.qqDao = qqDao;
		this.questionLogDao = questionLogDao;
	}
	@Override
	public List<RecordDto> getAllList(Transaction transaction, DynamicQuery query) throws IOException {
		List<RecordDto> list = new ArrayList<RecordDto>();
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			query.setBaseSql(GET_ALL);
			query.setSuffix(ORDER_BY);
			prepareStatement = connection.prepareStatement(query.generateSql());
			query.fillPreparedStatement(prepareStatement);
			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				RecordDto log = makeDto(transaction, resultSet);
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

	private RecordDto makeDto(Transaction transaction, ResultSet resultSet) throws SQLException, IOException {
		int seq = resultSet.getInt(1);
		int questionnaireSeq = resultSet.getInt(2);
		String dateStr = resultSet.getString(3);
		int time = resultSet.getInt(4);
		String ip = resultSet.getString(5);

		Date date = null;
		try {
			date = DateUtil.getDateWithTimeFormatInstance().parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<QqDto> qqs = qqDao.getAllList(transaction, questionnaireSeq);
		List<QuestionDto> questions = new ArrayList<QuestionDto>();
		for (QqDto qq : qqs) {
			QuestionDto question = qq.getQuestion();
			List<QuestionLogDto> list = questionLogDao.getAllList(transaction, seq, question);
			List<String> itemlist = getItems(list);
			if(question.getType().getSeq() == QuestionDto.MULTI_CHOICE)
			{
				question.setSelectedItems(itemlist);
			}
			else if(question.getType().getSeq() == QuestionDto.SINGLE_CHOICE)
			{
				question.setSelectedItem((itemlist.size() == 0 ? "" : itemlist.get(0)));
			}
			else
			{
				question.setAnswer(getAnswer(list));
			}

			questions.add(question);
		}

		return new RecordDto(seq, questionnaireSeq, date, time, ip, questions);
	}

	private String getAnswer(List<QuestionLogDto> list) {
		StringBuffer sb = new StringBuffer();
		for(QuestionLogDto dto : list)
		{
			sb.append(dto.getAnswer());
		}
		return sb.toString();
	}
	private List<String> getItems(List<QuestionLogDto> list) {
		List<String> ret = new ArrayList<String>();
		for(QuestionLogDto dto : list)
		{
			ret.add(dto.getItem());
		}
		return ret;
	}
	@Override
	public void add(Transaction transaction,
			RecordDto dto) throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setInt(1, dto.getQuestionnaireSeq());
			prepareStatement.setString(2, dto.getFormatDate());
			prepareStatement.setInt(3, dto.getTime());
			prepareStatement.setString(4, dto.getIp());
			prepareStatement.executeUpdate();

			int seq = getNewInsertedSeq(transaction);

			for (QuestionDto question : dto.getQuestions()) {
				if (question.getType().getSeq() == QuestionDto.MULTI_CHOICE) {
					for (String item : question.getSelectedItems())
						questionLogDao.add(transaction, new QuestionLogDto(seq,
								question.getSeq(), item, ""));
				}
				else if(question.getType().getSeq() == QuestionDto.SINGLE_CHOICE)
				{
					questionLogDao.add(transaction, new QuestionLogDto(seq,
							question.getSeq(), question.getSelectedItem(), ""));
				}
				else
				{
					questionLogDao.add(transaction, new QuestionLogDto(seq,
							question.getSeq(), "", question.getAnswer()));
				}
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
			prepareStatement = connection
					.prepareStatement(GET_NEW_INSERTED_SEQ);
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
	public void delete(Transaction transaction, int seq)
			throws IOException {
		System.out.println("delete : " + seq);
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
	private static final String GET_NEW_INSERTED_SEQ = "select max(seq) from N_QUESTIONNAIRE_RECORD";
	private static final String ORDER_BY = " order by add_date ";
	private static final String GET_ALL = "select seq, questionnaire_seq, add_date, eclapsed_time, ip from N_QUESTIONNAIRE_RECORD where 1 = 1 ";
	private static final String INSERT = "insert into N_QUESTIONNAIRE_RECORD (questionnaire_seq, add_date, eclapsed_time, ip) values ( ?, ?, ?, ?)";
	private static final String DELETE = "delete from N_QUESTIONNAIRE_RECORD where seq = ? ";

}
