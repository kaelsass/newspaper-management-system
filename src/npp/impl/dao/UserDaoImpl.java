package npp.impl.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;

import npp.condition.DynamicQuery;
import npp.dto.RoleDto;
import npp.dto.UserDto;
import npp.dto.WorkInfoDto;
import npp.spec.dao.RoleDao;
import npp.spec.dao.Transaction;
import npp.spec.dao.UserDao;
import npp.spec.dao.WorkInfoDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Stateless
public class UserDaoImpl implements UserDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private RoleDao roleDao;
	private WorkInfoDao employeeDao;

	@Inject
	public UserDaoImpl(RoleDao roleDao, WorkInfoDao employeeDao) {
		this.roleDao = roleDao;
		this.employeeDao = employeeDao;
	}

	@Override
	public UserDto findBySeq(Transaction transaction, String id)
			throws IOException {
		UserDto userDto = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_BY_ID);
			prepareStatement.setString(1, id);
			resultSet = prepareStatement.executeQuery();

			if (resultSet.next()) {
				userDto = makeDto(transaction, resultSet);
			}
			return userDto;
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
	public List<UserDto> getAllList(
			Transaction transaction, DynamicQuery query) throws IOException {
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			query.setBaseSql(GET_ALL);
			prepareStatement = connection.prepareStatement(query.generateSql());
			query.fillPreparedStatement(prepareStatement);

			resultSet = prepareStatement.executeQuery();

			List<UserDto> items = new ArrayList<UserDto>();
			while (resultSet.next()) {
				UserDto userDto = makeDto(transaction,
						resultSet);
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

	private UserDto makeDto(Transaction transaction,
			ResultSet resultSet) throws SQLException, IOException {
		String password = resultSet.getString(1);
		String userName = resultSet.getString(2);
		int roleid = resultSet.getInt(3);
		String employeeid = resultSet.getString(4);

		RoleDto roleDto = roleDao.findByID(transaction, roleid);
		WorkInfoDto employee = employeeDao.findByID(transaction, employeeid);

		UserDto userDto = new UserDto(password, userName, roleDto, employee);
		return userDto;
	}

	@Override
	public void add(Transaction transaction, UserDto userDto)
			throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setString(1, userDto.getPassword());
			prepareStatement.setString(2, userDto.getUserName());
			prepareStatement.setInt(3, userDto.getRole().getId());
			prepareStatement.setString(4, userDto.getEmployee().getId());

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
	@RolesAllowed("admin")
	public void update(Transaction transaction, UserDto userDto)
			throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(UPDATE);
			prepareStatement.setString(1, userDto.getPassword());
			prepareStatement.setInt(2, userDto.getRole().getId());
			prepareStatement.setString(3, userDto.getEmployee().getId());
			prepareStatement.setString(4, userDto.getUserName());
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
	public void delete(Transaction transaction, String username)
			throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(DELETE);
			prepareStatement.setString(1, username);
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

	private static final String GET_ALL = "select password, username, roleid, employeeid from s_users where 1 = 1 ";
	private static final String GET_BY_ID = "select password, username, roleid, employeeid from s_users where username = ?";
	private static final String INSERT = "insert into s_users (password, username, roleid, employeeid) values (?, ?, ?, ?)";
	private static final String UPDATE = "update s_users set password = ?, roleid = ? , employeeid = ? where username = ?";
	private static final String DELETE = "delete from s_users where username = ?";

}
