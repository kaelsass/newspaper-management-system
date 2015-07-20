package npp.impl.dao;

import java.io.IOException;
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

import npp.dto.PersonInfoDto;
import npp.dto.SexDto;
import npp.spec.dao.PersonInfoDao;
import npp.spec.dao.SexDao;
import npp.spec.dao.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Stateless
public class PersonInfoDaoImpl implements PersonInfoDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private SexDao sexDao;

	@Inject
	public PersonInfoDaoImpl(SexDao sexDao) {
		this.sexDao = sexDao;
	}

	@Override
	public List<PersonInfoDto> getAllList(Transaction transaction)
			throws IOException {
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_ALL);
			resultSet = prepareStatement.executeQuery();

			List<PersonInfoDto> items = new ArrayList<PersonInfoDto>();
			while (resultSet.next()) {
				PersonInfoDto dto = makeDto(transaction, resultSet);
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

	private PersonInfoDto makeDto(Transaction transaction, ResultSet resultSet)
			throws SQLException, IOException {
		String id = resultSet.getString(1);
		String name = resultSet.getString(2);
		String sex = resultSet.getString(3);
		String birthStr = resultSet.getString(4);
		String phone = resultSet.getString(5);
		String email = resultSet.getString(6);
		String photoName = resultSet.getString(7);

		SexDto sexDto = sexDao.findSex(transaction, sex);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date birthDate = new Date();
		if (birthStr != null && !birthStr.equals(""))
		{
			try {
				birthDate = df.parse(birthStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		PersonInfoDto dto = new PersonInfoDto(id, name, sexDto, birthDate,
				phone, email, photoName);
		return dto;
	}

	// private String findNameByID(Transaction transaction, int id) throws
	// IOException {
	// String name = null;
	// PreparedStatement prepareStatement = null;
	// ResultSet resultSet = null;
	//
	// try {
	// Connection connection = transaction.getResource(Connection.class);
	// prepareStatement = connection.prepareStatement(GET_NAME_BY_ID);
	// prepareStatement.setInt(1, id);
	// resultSet = prepareStatement.executeQuery();
	// if(resultSet.next()) {
	// name = resultSet.getString(1);
	// }
	// return name;
	// } catch (SQLException e) {
	// throw new IOException(e);
	// } finally {
	// if (resultSet != null) {
	// try {
	// resultSet.close();
	// } catch (SQLException e) {
	// logger.warn(e.getMessage(), e);
	// }
	// }
	//
	// if (prepareStatement != null) {
	// try {
	// prepareStatement.close();
	// } catch (SQLException e) {
	// logger.warn(e.getMessage(), e);
	// }
	// }
	// }
	// }

	@Override
	public PersonInfoDto findByID(Transaction transaction, String id)
			throws IOException {
		PersonInfoDto dto = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_BY_ID);
			prepareStatement.setString(1, id);
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
	public void add(Transaction transaction, PersonInfoDto dto)
			throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setString(1, dto.getId());
			prepareStatement.setString(2, dto.getName());
			prepareStatement.setString(3, dto.getSex().getName());
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			prepareStatement.setString(4, df.format(dto.getBirth()));
			prepareStatement.setString(5, dto.getPhone());
			prepareStatement.setString(6, dto.getEmail());
			prepareStatement.setString(7, dto.getPhotoName());
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
	public void update(Transaction transaction, PersonInfoDto dto)
			throws IOException {// need to do
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(UPDATE);
			prepareStatement.setString(1, dto.getName());
			prepareStatement.setString(2, dto.getSex().getName());
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			prepareStatement.setString(3, df.format(dto.getBirth()));
			prepareStatement.setString(4, dto.getPhone());
			prepareStatement.setString(5, dto.getEmail());
			prepareStatement.setString(6, dto.getPhotoName());
			prepareStatement.setString(7, dto.getId());
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
	public void delete(Transaction transaction, String id) throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(DELETE);
			prepareStatement.setString(1, id);
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

	private static final String GET_ALL = "select id, name, sex, birth, phone, email, photoname from s_employees where 1 = 1 ";
	private static final String GET_BY_ID = "select id, name, sex, birth, phone, email, photoname from s_employees where id = ?";
	private static final String INSERT = "insert into s_employees (id, name, sex, birth, phone, email, photoname) values (?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "update s_employees set name = ?, sex=?, birth=?, phone = ?, email=?, photoname = ? where id = ?";
	private static final String DELETE = "delete from s_employees where id = ?";

}
