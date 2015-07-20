package npp.impl.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import npp.condition.DynamicQuery;
import npp.dto.ArticleDto;
import npp.dto.AuthorDto;
import npp.dto.SectionDto;
import npp.dto.StatusDto;
import npp.dto.SubjectDto;
import npp.spec.dao.ArticleDao;
import npp.spec.dao.AuthorDao;
import npp.spec.dao.NStatusDao;
import npp.spec.dao.SectionDao;
import npp.spec.dao.SubjectDao;
import npp.spec.dao.Transaction;
import npp.utils.FileUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Stateless
public class ArticleDaoImpl implements ArticleDao {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private SectionDao sectionDao;

	private SubjectDao subjectDao;

	private AuthorDao authorDao;

	private NStatusDao statusDao;

	@Inject
	public ArticleDaoImpl(SectionDao sectionDao, SubjectDao subjectDao,
			AuthorDao authorDao, NStatusDao statusDao) {
		this.sectionDao = sectionDao;
		this.subjectDao = subjectDao;
		this.authorDao = authorDao;
		this.statusDao = statusDao;
	}

	@Override
	public ArticleDto findBySeq(Transaction transaction, int seq)
			throws IOException {
		ArticleDto dto = null;
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
	public List<ArticleDto> getAllList(Transaction transaction,
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

			List<ArticleDto> items = new ArrayList<ArticleDto>();
			while (resultSet.next()) {
				ArticleDto userDto = makeDto(transaction, resultSet);
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

	private ArticleDto makeDto(Transaction transaction, ResultSet resultSet)
			throws SQLException, IOException {
		int seq = resultSet.getInt(1);
		String title = resultSet.getString(2);
		InputStream is = resultSet.getClob(3).getAsciiStream();
		String html = FileUtil.readTxtFromInputStream(is);

		int sectionSeq = resultSet.getInt(4);
		int subjectSeq = resultSet.getInt(5);
		int authorSeq = resultSet.getInt(6);
		String timeStr = resultSet.getString(7);
		int statusSeq = resultSet.getInt(8);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date time = null;
		try {
			time = df.parse(timeStr);
		} catch (ParseException e) {
			time = null;
		}

		SectionDto section = sectionDao.findBySeq(transaction, sectionSeq);
		SubjectDto subject = subjectDao.findBySeq(transaction, subjectSeq);
		AuthorDto author = authorDao.findBySeq(transaction, authorSeq);
		StatusDto status = statusDao.findBySeq(transaction, statusSeq);

		ArticleDto dto = new ArticleDto(seq, title, html, section, subject,
				author, time, status);
		return dto;
	}

	@Override
	public void add(Transaction transaction, ArticleDto dto) throws IOException {
		PreparedStatement prepareStatement = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setString(1, dto.getTitle());
			prepareStatement.setString(2, dto.getHtml());
			prepareStatement.setInt(3, dto.getSection().getSeq());
			prepareStatement.setInt(4, dto.getSubject().getSeq());
			prepareStatement.setInt(5, dto.getAuthor().getSeq());
			prepareStatement.setString(6, df.format(new Date()));
			prepareStatement.setInt(7, dto.getStatus().getSeq());
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
	public void update(Transaction transaction, ArticleDto dto)
			throws IOException {
		PreparedStatement prepareStatement = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(UPDATE);
			prepareStatement.setString(1, dto.getTitle());
			prepareStatement.setString(2, dto.getHtml());
			prepareStatement.setInt(3, dto.getSection().getSeq());
			prepareStatement.setInt(4, dto.getSubject().getSeq());
			prepareStatement.setInt(5, dto.getAuthor().getSeq());
			prepareStatement.setString(6, df.format(new Date()));
			prepareStatement.setInt(7, dto.getStatus().getSeq());
			prepareStatement.setInt(8, dto.getSeq());
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

	private static final String ORDER = " order by subject_seq, time desc";
	private static final String GET_ALL = "select seq, title, html, section_seq, subject_seq, author_seq, time, status_seq from n_article where 1 = 1 ";
	private static final String GET_BY_SEQ = "select seq, title, html, section_seq, subject_seq, author_seq, time, status_seq from n_article where seq = ?";
	private static final String INSERT = "insert into n_article (title, html, section_seq, subject_seq, author_seq, time, status_seq) values (?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "update n_article set title = ?, html = ? , section_seq=?, subject_seq=?, author_seq= ?, time = ?, status_seq = ? where seq = ?";
	private static final String DELETE = "delete from n_article where seq = ?";

}
