package npp.impl.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import npp.dto.JobTitleDto;
import npp.spec.dao.JobTitleDao;
import npp.spec.dao.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Stateless
public class JobTitleDaoImpl implements JobTitleDao {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<JobTitleDto> getAllList(Transaction transaction) throws IOException {
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_ALL);
			resultSet = prepareStatement.executeQuery();

			List<JobTitleDto> items = new ArrayList<JobTitleDto>();

			while (resultSet.next()) {
				JobTitleDto dto = makeDto(transaction, resultSet);
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

	private JobTitleDto makeDto(Transaction transaction, ResultSet resultSet) throws SQLException, IOException {
		int seq = resultSet.getInt(1);
		String name = resultSet.getString(2);
		String despt = resultSet.getString(3);
		String note = resultSet.getString(4);

		JobTitleDto dto = new JobTitleDto(seq, name, despt, note);
		return dto;
	}


	@Override
	public JobTitleDto findBySeq(Transaction transaction, int seq) throws IOException {
		JobTitleDto positionDto = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_BY_SEQ);
			prepareStatement.setInt(1, seq);
			resultSet = prepareStatement.executeQuery();

			if(resultSet.next()) {
				positionDto = makeDto(transaction, resultSet);
			}
			return positionDto;
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
	public void add(Transaction transaction,
			JobTitleDto dto) throws IOException {
		PreparedStatement prepareStatement = null;
		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setString(1, dto.getName());
			prepareStatement.setString(2, dto.getDescription());
			prepareStatement.setString(3, dto.getNote());
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
	public void update(Transaction transaction,
			JobTitleDto dto) throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(UPDATE);
			prepareStatement.setString(1, dto.getName());
			prepareStatement.setString(2, dto.getDescription());
			prepareStatement.setString(3, dto.getNote());
			prepareStatement.setInt(4, dto.getSeq());
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

	@Override
	public JobTitleDto findByName(Transaction transaction, String name)
			throws IOException {
		JobTitleDto dto = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_BY_NAME);
			prepareStatement.setString(1, name);
			resultSet = prepareStatement.executeQuery();

			if(resultSet.next()) {
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


	private static final String GET_ALL = "select seq, name, description, note from s_jobtitle order by seq";
	private static final String GET_BY_SEQ = "select seq, name, description, note from s_jobtitle where seq = ?";
	private static final String GET_BY_NAME = "select seq, name, description, note from s_jobtitle where name = ?";
	private static final String INSERT = "insert into s_jobtitle (name, description, note) values (?, ?, ?)";
	private static final String UPDATE = "update s_jobtitle set name = ?, description = ?, note = ? where seq = ?";
	private static final String DELETE = "delete from s_jobtitle where seq = ?";

}
