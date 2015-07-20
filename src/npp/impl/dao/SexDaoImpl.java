package npp.impl.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import npp.dto.SexDto;
import npp.spec.dao.SexDao;
import npp.spec.dao.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Stateless
public class SexDaoImpl implements SexDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<SexDto> getAllList(Transaction transaction) throws IOException {
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_ALL);
			resultSet = prepareStatement.executeQuery();

			List<SexDto> items = new ArrayList<SexDto>();
			while (resultSet.next()) {
				SexDto sexDto = makeDto(transaction, resultSet);
				items.add(sexDto);
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

	private SexDto makeDto(Transaction transaction, ResultSet resultSet) throws SQLException, IOException {
		String name = resultSet.getString(1);

		SexDto sexDto = new SexDto(name);
		return sexDto;
	}


	@Override
	public SexDto findSex(Transaction transaction, String name) throws IOException {
		SexDto sexDto = null;

		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_BY_NAME);
			prepareStatement.setString(1, name);
			resultSet = prepareStatement.executeQuery();

			if(resultSet.next()) {
				sexDto = makeDto(transaction, resultSet);
			}
			return sexDto;
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

	private static final String GET_ALL = "select name from s_sex order by name desc";
	private static final String GET_BY_NAME = "select name from s_sex where name = ?";


}
