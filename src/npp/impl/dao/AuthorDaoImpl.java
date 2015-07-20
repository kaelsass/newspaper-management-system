package npp.impl.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.AuthorDto;
import npp.spec.dao.AuthorDao;
import npp.spec.dao.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Stateless
public class AuthorDaoImpl implements AuthorDao {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());


	@Override
	public AuthorDto findBySeq(Transaction transaction, int seq)
			throws IOException {
		AuthorDto dto = null;
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
	public AuthorDto findByName(Transaction transaction, String name)
			throws IOException {
		AuthorDto dto = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_BY_NAME);
			prepareStatement.setString(1, name);
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
	public List<AuthorDto> getAllList(
			Transaction transaction, DynamicQuery query) throws IOException {
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			query.setBaseSql(GET_ALL);
			query.setSuffix(ORDER);
			prepareStatement = connection.prepareStatement(query.generateSql());
			query.fillPreparedStatement(prepareStatement);

			resultSet = prepareStatement.executeQuery();

			List<AuthorDto> items = new ArrayList<AuthorDto>();
			while (resultSet.next()) {
				AuthorDto dto = makeDto(transaction,
						resultSet);
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


	private AuthorDto makeDto(Transaction transaction,
			ResultSet resultSet) throws SQLException, IOException {
		int seq = resultSet.getInt(1);
		String name = resultSet.getString(2);
		String address = resultSet.getString(3);
		String workunit = resultSet.getString(4);
		String zipcode = resultSet.getString(5);
		String phone = resultSet.getString(6);
		String email = resultSet.getString(7);
		String account = resultSet.getString(8);


		AuthorDto dto = new AuthorDto(seq, name, address, workunit, zipcode, phone, email, account);
		return dto;
	}

	@Override
	public void add(Transaction transaction, AuthorDto dto)
			throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setString(1, dto.getName());
			prepareStatement.setString(2, dto.getAddress());
			prepareStatement.setString(3, dto.getWorkUnit());
			prepareStatement.setString(4, dto.getZipcode());
			prepareStatement.setString(5, dto.getPhone());
			prepareStatement.setString(6, dto.getEmail());
			prepareStatement.setString(7, dto.getAccount());
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
	public void update(Transaction transaction, AuthorDto dto)
			throws IOException {
		PreparedStatement prepareStatement = null;
		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(UPDATE);
			prepareStatement.setString(1, dto.getName());
			prepareStatement.setString(2, dto.getAddress());
			prepareStatement.setString(3, dto.getWorkUnit());
			prepareStatement.setString(4, dto.getZipcode());
			prepareStatement.setString(5, dto.getPhone());
			prepareStatement.setString(6, dto.getEmail());
			prepareStatement.setString(7, dto.getAccount());
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
	private static final String ORDER = " order by name, workunit";
	private static final String GET_ALL = "select seq, name, address, workunit, zipcode, phone, email, account from n_author where 1 = 1 ";
	private static final String GET_BY_SEQ = "select seq, name, address, workunit, zipcode, phone, email, account from n_author where seq = ?";
	private static final String GET_BY_NAME = "select seq, name, address, workunit, zipcode, phone, email, account from n_author where name = ?";
	private static final String INSERT = "insert into n_author (name, address, workunit, zipcode, phone, email, account) values (?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "update n_author set name = ?, address = ?, workunit = ?, zipcode = ?, phone = ?, email = ?, account = ? where seq = ?";
	private static final String DELETE = "delete from n_author where seq = ?";



}
