package npp.impl.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import npp.dto.SupervisorDto;
import npp.spec.dao.SupervisorDao;
import npp.spec.dao.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Stateless
public class SupervisorDaoImpl implements SupervisorDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public SupervisorDto findByID(Transaction transaction, String id) throws IOException {
		SupervisorDto supervisor = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_BY_ID);
			prepareStatement.setString(1, id);
			resultSet = prepareStatement.executeQuery();
			if(resultSet.next()) {
				supervisor = makeDto(resultSet);
			}
			return supervisor;
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
	private SupervisorDto makeDto(ResultSet resultSet) throws SQLException {
		String id = resultSet.getString(1);
		String name = resultSet.getString(2);
		SupervisorDto dto = new SupervisorDto(id, name);
		return dto;
	}
	private static final String GET_BY_ID = "select id, name from s_employees where id = ?";


}
