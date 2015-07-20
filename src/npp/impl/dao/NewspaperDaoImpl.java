package npp.impl.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.NewspaperDto;
import npp.dto.PublicationDateDto;
import npp.spec.dao.NewspaperDao;
import npp.spec.dao.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Stateless
public class NewspaperDaoImpl implements NewspaperDao {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public NewspaperDto findBySeq(Transaction transaction, int seq)
			throws IOException {
		NewspaperDto dto = null;
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
	public List<NewspaperDto> getAllList(
			Transaction transaction, DynamicQuery query) throws IOException {
		List<NewspaperDto> items = new ArrayList<NewspaperDto>();

		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			query.setBaseSql(GET_ALL);
			query.setSuffix(ORDER);
			prepareStatement = connection.prepareStatement(query.generateSql());
			query.fillPreparedStatement(prepareStatement);

			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				System.out.println("next");
				NewspaperDto dto = makeDto(transaction,
						resultSet);
				items.add(dto);
			}
			System.out.println("item size: "+ items.size());
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


	private NewspaperDto makeDto(Transaction transaction,
			ResultSet resultSet) throws SQLException, IOException {
		int seq = resultSet.getInt(1);
		String name = resultSet.getString(2);
		String issn = resultSet.getString(3);
		double dayPrice = resultSet.getDouble(4);
		double monthPrice = resultSet.getDouble(5);
		int pday = resultSet.getInt(6);
		int ptype = resultSet.getInt(7);

		NewspaperDto dto = new NewspaperDto(seq, name, issn, dayPrice, monthPrice, new PublicationDateDto(pday, ptype));
		return dto;
	}

	@Override
	public void add(Transaction transaction, NewspaperDto dto)
			throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setString(1, dto.getName());
			prepareStatement.setString(2, dto.getIssn());
			prepareStatement.setDouble(3, dto.getIssuePrice());
			prepareStatement.setDouble(4, dto.getMonthPrice());
			prepareStatement.setInt(5, dto.getPdate().getDay());
			prepareStatement.setInt(6, dto.getPdate().getType());

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
	public void update(Transaction transaction, NewspaperDto dto)
			throws IOException {
		PreparedStatement prepareStatement = null;
		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(UPDATE);
			prepareStatement.setString(1, dto.getName());
			prepareStatement.setString(2, dto.getIssn());
			prepareStatement.setDouble(3, dto.getIssuePrice());
			prepareStatement.setDouble(4, dto.getMonthPrice());
			prepareStatement.setInt(5, dto.getPdate().getDay());
			prepareStatement.setInt(6, dto.getPdate().getType());
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
	private static final String ORDER = " order by name";
	private static final String GET_ALL = "select seq, name, issn, issue_price, month_price, pday, ptype from n_newspaper where 1 = 1 ";
	private static final String GET_BY_SEQ = "select seq, name, issn, issue_price, month_price, pday, ptype from n_newspaper where seq = ?";
	private static final String INSERT = "insert into n_newspaper (name, issn, issue_price, month_price, pday, ptype) values (?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "update n_newspaper set name = ?, issn = ?, issue_price = ?, month_price = ?, pday = ?, ptype = ? where seq = ?";
	private static final String DELETE = "delete from n_newspaper where seq = ?";

}
