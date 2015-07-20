package npp.impl.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import npp.dto.DepartmentDto;
import npp.spec.dao.DepartmentDao;
import npp.spec.dao.Transaction;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Stateless
public class DepartmentDaoImpl implements DepartmentDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public TreeNode getTree(Transaction transaction) throws IOException {
		TreeNode root = null;
		DepartmentDto dto = getRootDepartment(transaction);
		if(dto != null)
		{
			root = new DefaultTreeNode(dto, null);
			createSubTree(transaction, root);
		}
		return root;
	}

	private void createSubTree(Transaction transaction, TreeNode root) throws IOException {
		List<DepartmentDto> children = getChildren(transaction, ((DepartmentDto)root.getData()).getSeq());
		for(DepartmentDto child : children)
		{
			TreeNode childNode = new DefaultTreeNode(child, root);
			createSubTree(transaction, childNode);
		}
	}

	private List<DepartmentDto> getChildren(Transaction transaction, int seq) throws IOException {
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_CHILDREN);
			prepareStatement.setInt(1, seq);
			resultSet = prepareStatement.executeQuery();

			List<DepartmentDto> items = new ArrayList<DepartmentDto>();
			while (resultSet.next()) {
				DepartmentDto departmentDto = makeDto(transaction, resultSet);
				items.add(departmentDto);
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

	private DepartmentDto getRootDepartment(Transaction transaction) throws IOException {
		DepartmentDto dto = null;

		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_ROOT);
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

	@Override
	public List<DepartmentDto> getAllList(Transaction transaction) throws IOException {
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_ALL);
			resultSet = prepareStatement.executeQuery();

			List<DepartmentDto> items = new ArrayList<DepartmentDto>();
			while (resultSet.next()) {
				DepartmentDto departmentDto = makeDto(transaction, resultSet);
				items.add(departmentDto);
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

	private DepartmentDto makeDto(Transaction transaction, ResultSet resultSet) throws SQLException, IOException {
		int seq = resultSet.getInt(1);
		String name = resultSet.getString(2);
		String descpt = resultSet.getString(3);
		int parent_seq = resultSet.getInt(4);


		DepartmentDto departmentDto = new DepartmentDto(seq, name, descpt,  parent_seq);
		return departmentDto;
	}


	@Override
	public DepartmentDto findBySeq(Transaction transaction, int departmentSeq) throws IOException {
		DepartmentDto departmentDto = null;

		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_BY_SEQ);
			prepareStatement.setInt(1, departmentSeq);
			resultSet = prepareStatement.executeQuery();

			if(resultSet.next()) {
				departmentDto = makeDto(transaction, resultSet);
			}
			return departmentDto;
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
			DepartmentDto dto) throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT_DEPARTMENT);
			prepareStatement.setInt(1, dto.getSeq());
			prepareStatement.setString(2, dto.getName());
			prepareStatement.setString(3, dto.getDescription());
			prepareStatement.setInt(4, dto.getParentSeq());
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
	public void updateDepartment(Transaction transaction,
			DepartmentDto dto) throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(UPDATE_DEPARTMENT);
			prepareStatement.setString(1, dto.getName());
			prepareStatement.setString(2, dto.getDescription());
			prepareStatement.setInt(3, dto.getParentSeq());
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
			prepareStatement = connection.prepareStatement(DELETE_DEPARTMENT);
			prepareStatement.setInt(1, seq);
			prepareStatement.executeUpdate();
			deleteChildren(transaction, seq);
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


	private void deleteChildren(Transaction transaction, int seq) throws IOException {
		List<DepartmentDto> list = getChildren(transaction, seq);
		for(DepartmentDto dto : list)
		{
			delete(transaction, dto.getSeq());
			deleteChildren(transaction, dto.getSeq());
		}
	}

	@Override
	public DepartmentDto findDepartmentByName(Transaction transaction,
			String name) throws IOException {
		DepartmentDto departmentDto = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_BY_NAME);
			prepareStatement.setString(1, name);
			resultSet = prepareStatement.executeQuery();
			if(resultSet.next()) {
				departmentDto = makeDto(transaction, resultSet);
			}
			return departmentDto;
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

	private static final String GET_ALL = "select seq, name, description, parent_seq from s_department order by seq";
	private static final String GET_ROOT = "select seq, name, description, parent_seq from s_department where parent_seq = 0";
	private static final String GET_CHILDREN = "select seq, name, description, parent_seq from s_department where parent_seq = ?";
	private static final String GET_BY_SEQ = "select seq, name, description, parent_seq from s_department where seq = ?";
	private static final String GET_BY_NAME = "select seq, name, description, parent_seq from s_department where name = ?";
	private static final String INSERT_DEPARTMENT = "insert into s_department (seq, name,description,  parent_seq) values (?, ?, ?, ?)";
	private static final String UPDATE_DEPARTMENT = "update s_department set name = ?, description = ?, parent_seq = ? where seq = ?";
	private static final String DELETE_DEPARTMENT = "delete from s_department where seq = ?";



}
