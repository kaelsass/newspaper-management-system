package npp.impl.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;

import npp.dto.RoleDto;
import npp.spec.dao.LoginDao;
import npp.spec.dao.RoleDao;
import npp.spec.dao.Transaction;
import npp.utils.PasswordHash;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Stateless
public class LoginDaoImpl implements LoginDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private RoleDao roleDao;

	@Inject
	public LoginDaoImpl(RoleDao roleDao)
	{
		this.roleDao = roleDao;
	}
	@Override
	public boolean isValid(Transaction transaction, String username, String password) throws IOException {
		String pwd = "";
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_PASSWORD_BY_NAME);
			prepareStatement.setString(1, username);
			resultSet = prepareStatement.executeQuery();

			if(resultSet.next()) {
				pwd = resultSet.getString(1);
			}
			return pwd.equals(PasswordHash.makePasswordHash(password));
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
	public String getRoleByUserName(Transaction transaction, String username)
			throws IOException {
		String role = "";
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_ROLE_BY_NAME);
			prepareStatement.setString(1, username);
			resultSet = prepareStatement.executeQuery();

			if(resultSet.next()) {
				int roleid = resultSet.getInt(1);
				RoleDto roleDto = roleDao.findByID(transaction, roleid);
				if(roleDto != null)
					role = roleDto.getRole();
			}
			return role;
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

	private static final String GET_PASSWORD_BY_NAME = "select password from s_users where username = ?";
	private static final String GET_ROLE_BY_NAME = "select roleid from s_users where username = ?";

}
