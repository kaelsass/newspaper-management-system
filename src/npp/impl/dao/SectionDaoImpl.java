package npp.impl.dao;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import npp.condition.DynamicQuery;
import npp.dto.IssueDto;
import npp.dto.SectionDto;
import npp.dto.StatusDto;
import npp.dto.SubjectDto;
import npp.spec.dao.IssueDao;
import npp.spec.dao.NStatusDao;
import npp.spec.dao.SectionDao;
import npp.spec.dao.SubjectDao;
import npp.spec.dao.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Stateless
public class SectionDaoImpl implements SectionDao {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private IssueDao issueDao;
	private NStatusDao statusDao;
	private SubjectDao subjectDao;

	@Inject
	public SectionDaoImpl(IssueDao dao, NStatusDao statusDao,
			SubjectDao subjectDao) {
		this.issueDao = dao;
		this.statusDao = statusDao;
		this.subjectDao = subjectDao;
	}

	@Override
	public SectionDto findBySeq(Transaction transaction, int seq)
			throws IOException {
		SectionDto dto = null;
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
	public List<SectionDto> getAllList(Transaction transaction,
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

			List<SectionDto> items = new ArrayList<SectionDto>();
			while (resultSet.next()) {
				SectionDto dto = makeDto(transaction, resultSet);
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

	private SectionDto makeDto(Transaction transaction, ResultSet resultSet)
			throws SQLException, IOException {
		int seq = resultSet.getInt(1);
		int number = resultSet.getInt(2);
		int subjectSeq = resultSet.getInt(3);
		int issueSeq = resultSet.getInt(4);
		int statusSeq = resultSet.getInt(5);
		String photoName = resultSet.getString(6);
		Clob clob = resultSet.getClob(7);
		String html = convertClobToString(clob);

		IssueDto issue = issueDao.findBySeq(transaction, issueSeq);
		StatusDto status = statusDao.findBySeq(transaction, statusSeq);
		SubjectDto subject = subjectDao.findBySeq(transaction, subjectSeq);

		SectionDto dto = new SectionDto(seq, number, subject, issue, status,
				photoName, html);
		return dto;
	}

	public String convertClobToString(Clob clob) {
		String reString = "";
		try {
			Reader is = clob.getCharacterStream();// 得到流
			BufferedReader br = new BufferedReader(is);
			String s = br.readLine();
			StringBuffer sb = new StringBuffer();
			while (s != null) {
				sb.append(s);
				sb.append("\n");
				s = br.readLine();
			}
			reString = sb.toString().trim();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reString;
	}

	@Override
	public void add(Transaction transaction, SectionDto dto) throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setInt(1, dto.getNumber());
			prepareStatement.setInt(2, dto.getSubject().getSeq());
			prepareStatement.setInt(3, dto.getIssue().getSeq());
			prepareStatement.setInt(4, dto.getStatus().getSeq());
			prepareStatement.setString(5, dto.getPhotoName());
			prepareStatement.setString(6, dto.getHtml());

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
	public void update(Transaction transaction, SectionDto dto)
			throws IOException {
		PreparedStatement prepareStatement = null;
		Statement stmt = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(UPDATE);
			prepareStatement.setInt(1, dto.getNumber());
			prepareStatement.setInt(2, dto.getSubject().getSeq());
			prepareStatement.setInt(3, dto.getIssue().getSeq());
			prepareStatement.setInt(4, dto.getStatus().getSeq());
			prepareStatement.setString(5, dto.getPhotoName());

			// java.sql.Clob clob = oracle.sql.CLOB.createTemporary(connection,
			// false, oracle.sql.CLOB.DURATION_SESSION);
			// clob.setString(1, dto.getHtml());
			//
			// prepareStatement.setClob(6, clob);
			// prepareStatement.setCharacterStream(6, new
			// StringReader(dto.getHtml()));

			// ((OraclePreparedStatement) prepareStatement).setStringForClob(6,
			// dto.getHtml());

			// prepareStatement.setString(6, dto.getHtml());

			prepareStatement.setInt(6, dto.getSeq());
			prepareStatement.executeUpdate();

			// 锁定数据行进行更新，注意“for update”语句
			String sqlUpd = "select html from n_section where seq = "
					+ dto.getSeq() + " for update";
			System.out.println(sqlUpd);
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sqlUpd);
			if (rs.next()) {
				oracle.sql.CLOB clobnew = ((oracle.jdbc.OracleResultSet) rs)
						.getCLOB("html");

				byte[] bytes = dto.getHtml().getBytes();
				ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
				InputStreamReader isr = new InputStreamReader(bais);
				PrintWriter pw = new PrintWriter(
						clobnew.getCharacterOutputStream());

				BufferedReader br = new BufferedReader(isr);
				// new FileReader( new File("D:\\test.txt") ) );
				String lineIn = null;
				while ((lineIn = br.readLine()) != null)
					pw.println(lineIn);
				pw.close();
				br.close();
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
			if (stmt != null) {
				try {
					stmt.close();
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

	private static final String ORDER = " order by issue_seq, num";
	private static final String GET_ALL = "select seq, num, subject_seq, issue_seq, status_seq, photoname, html from n_section where 1 = 1 ";
	private static final String GET_BY_SEQ = "select seq, num, subject_seq, issue_seq, status_seq, photoname, html from n_section where seq = ?";
	private static final String INSERT = "insert into n_section (num, subject_seq, issue_seq, status_seq, photoname, html) values (?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "update n_section set num = ?, subject_seq = ?, issue_seq = ?, status_seq = ?, photoname = ?, html = empty_clob() where seq = ?";
	private static final String DELETE = "delete from n_section where seq = ?";

}
