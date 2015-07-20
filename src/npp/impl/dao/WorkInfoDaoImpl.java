package npp.impl.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import npp.condition.DynamicQuery;
import npp.dto.DepartmentDto;
import npp.dto.JobCategoryDto;
import npp.dto.JobTitleDto;
import npp.dto.StatusDto;
import npp.dto.SupervisorDto;
import npp.dto.WorkInfoDto;
import npp.spec.dao.DepartmentDao;
import npp.spec.dao.JobCategoryDao;
import npp.spec.dao.JobTitleDao;
import npp.spec.dao.StatusDao;
import npp.spec.dao.SupervisorDao;
import npp.spec.dao.Transaction;
import npp.spec.dao.WorkInfoDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Stateless
public class WorkInfoDaoImpl implements WorkInfoDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private JobTitleDao jobTitleDao;

	private StatusDao statusDao;

	private DepartmentDao departmentDao;

	private SupervisorDao supervisorDao;

	private JobCategoryDao jobCategoryDao;

	@Inject
	public WorkInfoDaoImpl(JobTitleDao jobTitleDao, StatusDao statusDao, JobCategoryDao jobCategoryDao, DepartmentDao departmentDao, SupervisorDao supervisorDao){
		this.jobTitleDao = jobTitleDao;
		this.statusDao = statusDao;
		this.jobCategoryDao = jobCategoryDao;
		this.departmentDao = departmentDao;
		this.supervisorDao = supervisorDao;
	}

	@Override
	public List<WorkInfoDto> getAllList(Transaction transaction, DynamicQuery query) throws IOException {
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		try {
			Connection connection = transaction.getResource(Connection.class);

			query.setBaseSql(GET_ALL);
			String sql= query.generateSql();
			prepareStatement = connection.prepareStatement(sql);
			query.fillPreparedStatement(prepareStatement);
			resultSet = prepareStatement.executeQuery();

			List<WorkInfoDto> items = new ArrayList<WorkInfoDto>();
			while (resultSet.next()) {
				WorkInfoDto dto = makeDto(transaction, resultSet);
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

	private WorkInfoDto makeDto(Transaction transaction, ResultSet resultSet) throws SQLException, IOException {
		String id = resultSet.getString(1);
		String name = resultSet.getString(2);
		int jobTitleSeq = resultSet.getInt(3);
		int statusSeq = resultSet.getInt(4);
		int jobCategorySeq = resultSet.getInt(5);
		int departmentSeq = resultSet.getInt(6);
		String supervisorID = resultSet.getString(7);
		String phone = resultSet.getString(8);
		String email = resultSet.getString(9);
		String photoName = resultSet.getString(10);

		JobTitleDto jobTitleDto = jobTitleDao.findBySeq(transaction, jobTitleSeq);
		StatusDto statusDto = statusDao.findBySeq(transaction, statusSeq);
		JobCategoryDto jobCategoryDto = jobCategoryDao.findBySeq(transaction, jobCategorySeq);
		DepartmentDto departmentDto  = departmentDao.findBySeq(transaction, departmentSeq);
		SupervisorDto supervisor = supervisorDao.findByID(transaction, supervisorID);

		WorkInfoDto workInfoDto = new WorkInfoDto(id, name, jobTitleDto, statusDto, jobCategoryDto, departmentDto, supervisor, phone, email, photoName);
		return workInfoDto;
	}

//	private String findNameByID(Transaction transaction, int id) throws IOException {
//		String name = null;
//		PreparedStatement prepareStatement = null;
//		ResultSet resultSet = null;
//
//		try {
//			Connection connection = transaction.getResource(Connection.class);
//			prepareStatement = connection.prepareStatement(GET_NAME_BY_ID);
//			prepareStatement.setInt(1, id);
//			resultSet = prepareStatement.executeQuery();
//			if(resultSet.next()) {
//				name = resultSet.getString(1);
//			}
//			return name;
//		} catch (SQLException e) {
//			throw new IOException(e);
//		} finally {
//			if (resultSet != null) {
//				try {
//					resultSet.close();
//				} catch (SQLException e) {
//					logger.warn(e.getMessage(), e);
//				}
//			}
//
//			if (prepareStatement != null) {
//				try {
//					prepareStatement.close();
//				} catch (SQLException e) {
//					logger.warn(e.getMessage(), e);
//				}
//			}
//		}
//	}

	@Override
	public WorkInfoDto findByID(Transaction transaction, String id) throws IOException {
		WorkInfoDto employeeDto = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_BY_ID);
			prepareStatement.setString(1, id);
			resultSet = prepareStatement.executeQuery();
			if(resultSet.next()) {
				employeeDto = makeDto(transaction, resultSet);
			}
			return employeeDto;
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
	public WorkInfoDto findByName(Transaction transaction, String name) throws IOException {
		WorkInfoDto employeeDto = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_BY_NAME);
			prepareStatement.setString(1, name);
			resultSet = prepareStatement.executeQuery();
			if(resultSet.next()) {
				employeeDto = makeDto(transaction, resultSet);
			}
			return employeeDto;
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
	public void add(Transaction transaction, WorkInfoDto dto) throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setString(1, dto.getId());
			prepareStatement.setString(2, dto.getName());
			prepareStatement.setInt(3, dto.getJobTitle().getSeq());
			prepareStatement.setInt(4, dto.getStatus().getSeq());
			prepareStatement.setInt(5, dto.getJobCategory().getSeq());
			prepareStatement.setInt(6, dto.getDepartment().getSeq());
			prepareStatement.setString(7, dto.getSupervisor().getId());
			prepareStatement.setString(8, dto.getPhone());
			prepareStatement.setString(9, dto.getEmail());
			prepareStatement.setString(10, dto.getPhotoName());
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
	public void update(Transaction transaction, WorkInfoDto dto) throws IOException {//need to do
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(UPDATE);
			prepareStatement.setString(1, dto.getName());
			prepareStatement.setInt(2, dto.getJobTitle().getSeq());
			prepareStatement.setInt(3, dto.getStatus().getSeq());
			prepareStatement.setInt(4, dto.getJobCategory().getSeq());
			prepareStatement.setInt(5, dto.getDepartment().getSeq());
			prepareStatement.setString(6, dto.getSupervisor().getId());
			prepareStatement.setString(7, dto.getPhone());
			prepareStatement.setString(8, dto.getEmail());
			prepareStatement.setString(9, dto.getPhotoName());
			prepareStatement.setString(10, dto.getId());
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

	private static final String GET_ALL = "select id, name, jobtitle_seq, status_seq, jobcategory_seq, department_seq, supervisor_id, phone, email, photoname from s_employees where 1 = 1 ";
	private static final String GET_BY_ID = "select id, name, jobtitle_seq, status_seq, jobcategory_seq,department_seq, supervisor_id, phone, email, photoname from s_employees where id = ?";
	private static final String GET_BY_NAME = "select id, name, jobtitle_seq, status_seq, jobcategory_seq, department_seq, supervisor_id, phone, email, photoname from s_employees where name = ?";
	private static final String INSERT = "insert into s_employees (id, name, jobtitle_seq, status_seq, jobcategory_seq, department_seq, supervisor_id, phone, email, photoname ) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "update s_employees set name = ?, jobtitle_seq=?, status_seq=?, jobcategory_seq = ?, department_seq=?, supervisor_id=?, phone = ?, email = ?, photoname = ? where id = ?";
	private static final String DELETE = "delete from s_employees where id = ?";


}
