package npp.impl.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import npp.dto.RoleDto;
import npp.spec.dao.RoleDao;
import npp.spec.dao.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Stateless
public class RoleDaoImpl implements RoleDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<RoleDto> getAllList(Transaction transaction) throws IOException {
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_ALL);
			resultSet = prepareStatement.executeQuery();

			List<RoleDto> items = new ArrayList<RoleDto>();
			while (resultSet.next()) {
				RoleDto roleDto = makeDto(transaction, resultSet);
				items.add(roleDto);
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

	@Override
	public RoleDto findByID(Transaction transaction, int roleid) throws IOException {
		RoleDto roleDto = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_BY_ID);
			prepareStatement.setInt(1, roleid);
			resultSet = prepareStatement.executeQuery();
			if(resultSet.next()) {
				roleDto = makeDto(transaction, resultSet);
			}
			return roleDto;
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

	private RoleDto makeDto(Transaction transaction, ResultSet resultSet) throws SQLException, IOException {
		int id = resultSet.getInt(1);
		String role = resultSet.getString(2);
		RoleDto roleDto = new RoleDto(id, role);
		return roleDto;
	}

	@Override
	public void add(Transaction transaction, RoleDto roleDto) throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setInt(1, roleDto.getId());
			prepareStatement.setString(2, roleDto.getRole());

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
	public void delete(Transaction transaction, int id) throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(DELETE);
			prepareStatement.setInt(1, id);
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

	private static final String GET_ALL = "select id, role from s_role order by id";
	private static final String GET_BY_ID = "select id, role from s_role where id = ?";
	private static final String INSERT = "insert into s_role(id, role) values( ?, ?)";
	private static final String DELETE = "delete from s_role where id = ?";

}
