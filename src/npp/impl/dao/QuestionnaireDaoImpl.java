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
import npp.dto.PromotionDto;
import npp.dto.QqDto;
import npp.dto.QuestionDto;
import npp.dto.QuestionnaireDto;
import npp.spec.dao.PromotionDao;
import npp.spec.dao.QuestionnaireDao;
import npp.spec.dao.QuestionnaireQuestionDao;
import npp.spec.dao.Transaction;
import npp.utils.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Stateless
public class QuestionnaireDaoImpl implements QuestionnaireDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private PromotionDao promotionDao;
	private QuestionnaireQuestionDao qqDao;

	@Inject
	public QuestionnaireDaoImpl(PromotionDao promotionDao,
			QuestionnaireQuestionDao qqDao) {
		this.promotionDao = promotionDao;
		this.qqDao = qqDao;
	}

	@Override
	public List<QuestionnaireDto> getAllList(Transaction transaction,
			DynamicQuery query) throws IOException {
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			query.setBaseSql(GET_ALL);
			prepareStatement = connection.prepareStatement(query.generateSql());
			query.fillPreparedStatement(prepareStatement);
			resultSet = prepareStatement.executeQuery();

			List<QuestionnaireDto> items = new ArrayList<QuestionnaireDto>();
			while (resultSet.next()) {

				QuestionnaireDto dto = makeDto(transaction, resultSet);
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

	private QuestionnaireDto makeDto(Transaction transaction,
			ResultSet resultSet) throws SQLException, IOException {
		// prepareStatement.setString(1, dto.getName());
		// prepareStatement.setString(2, dto.getDescription());
		// prepareStatement.setInt(3, dto.getPromotion().getSeq());
		// prepareStatement.setString(4, dto.getFormatAddDate());
		// prepareStatement.setString(5, dto.getFormatModDate());
		// prepareStatement.setBoolean(6, dto.isPublished());
		int seq = resultSet.getInt(1);
		String name = resultSet.getString(2);
		String description = resultSet.getString(3);
		int promotionSeq = resultSet.getInt(4);
		String addStr = resultSet.getString(5);
		String modStr = resultSet.getString(6);
		boolean published = resultSet.getBoolean(7);

		PromotionDto promotion = promotionDao.findBySeq(transaction,
				promotionSeq);
		Date addDate = null;
		Date modDate = null;

		try {
			addDate = DateUtil.getDateFormatInstance().parse(addStr);
			modDate = DateUtil.getDateFormatInstance().parse(modStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<QqDto> qqs = qqDao.getAllList(transaction, seq);
		List<QuestionDto> questions = new ArrayList<QuestionDto>();
		for (QqDto qq : qqs) {
			questions.add(qq.getQuestion());
		}


		QuestionnaireDto dto = new QuestionnaireDto(seq, name, description,
				promotion, addDate, modDate, published, questions);
		return dto;
	}

	@Override
	public QuestionnaireDto findBySeq(Transaction transaction, int seq)
			throws IOException {
		QuestionnaireDto dto = null;

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
	public void add(Transaction transaction, QuestionnaireDto dto)
			throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setString(1, dto.getName());
			prepareStatement.setString(2, dto.getDescription());
			prepareStatement.setInt(3, dto.getPromotion().getSeq());
			prepareStatement.setString(4, dto.getFormatAddDate());
			prepareStatement.setString(5, dto.getFormatModDate());
			prepareStatement.setBoolean(6, dto.isPublished());
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

	// SEQ NUMBER(8, 0) NOT NULL PRIMARY KEY,
	// NAME VARCHAR2(500),
	// DESCRIPTION VARCHAR2(1000),
	// PROMOTION_SEQ NUMBER(8, 0),
	// ADD_DATE VARCHAR2(30),
	// MOD_DATE VARCHAR2(30),
	// PUBLISHED NUMBER(1)

	@Override
	public void update(Transaction transaction, QuestionnaireDto dto)
			throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(UPDATE);
			prepareStatement.setString(1, dto.getName());
			prepareStatement.setString(2, dto.getDescription());
			prepareStatement.setInt(3, dto.getPromotion().getSeq());
			prepareStatement.setString(4, dto.getFormatAddDate());
			prepareStatement.setString(5, dto.getFormatModDate());
			prepareStatement.setBoolean(6, dto.isPublished());
			prepareStatement.setInt(7, dto.getSeq());
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

	private static final String GET_ALL = "select seq, name, description, promotion_seq, add_date, mod_date, published from N_QUESTIONNAIRE where 1 = 1 ";
	private static final String GET_BY_SEQ = "select seq, name, description, promotion_seq, add_date, mod_date, published from N_QUESTIONNAIRE where seq = ?";
	private static final String INSERT = "insert into N_QUESTIONNAIRE (name, description, promotion_seq, add_date, mod_date, published) values (?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "update N_QUESTIONNAIRE set name = ?, description = ?, promotion_seq = ?, add_date = ?, mod_date = ?, published = ? where seq = ?";
	private static final String DELETE = "delete from N_QUESTIONNAIRE where seq = ?";

}
