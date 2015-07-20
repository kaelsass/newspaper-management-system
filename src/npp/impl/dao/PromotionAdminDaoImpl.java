package npp.impl.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import npp.dto.PromotionAdminDto;
import npp.spec.dao.PromotionAdminDao;
import npp.spec.dao.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Stateless
public class PromotionAdminDaoImpl implements PromotionAdminDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private PromotionAdminDto makeDto(Transaction transaction, ResultSet resultSet) throws SQLException, IOException {
		int promotionSeq = resultSet.getInt(1);
		String employeeID = resultSet.getString(2);
		PromotionAdminDto dto = new PromotionAdminDto(promotionSeq, employeeID);
		return dto;
	}


	@Override
	public List<PromotionAdminDto> findByPromotionSeq(Transaction transaction, int promotionSeq) throws IOException {
		PromotionAdminDto dto = null;

		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_BY_PROMOTION_SEQ);
			prepareStatement.setInt(1, promotionSeq);
			resultSet = prepareStatement.executeQuery();
			ArrayList<PromotionAdminDto> list = new ArrayList<PromotionAdminDto>();
			while(resultSet.next()) {
				dto = makeDto(transaction, resultSet);
				list.add(dto);
			}
			return list;
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
	public List<PromotionAdminDto> findByEmployeeID(Transaction transaction, String id) throws IOException {
		PromotionAdminDto dto = null;

		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_BY_EMPLOYEE_ID);
			prepareStatement.setString(1, id);
			resultSet = prepareStatement.executeQuery();
			ArrayList<PromotionAdminDto> list = new ArrayList<PromotionAdminDto>();
			while(resultSet.next()) {
				dto = makeDto(transaction, resultSet);
				list.add(dto);
			}
			return list;
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
			PromotionAdminDto dto) throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setInt(1, dto.getPromotionSeq());
			prepareStatement.setString(2, dto.getEmployeeID());
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
	public void delete(Transaction transaction, int promotionSeq)
			throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(DELETE);
			prepareStatement.setInt(1, promotionSeq);
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
	private static final String GET_BY_EMPLOYEE_ID = "select promotion_seq, employee_id from N_PROMOTION_ADMIN where employee_id = ?";
	private static final String GET_BY_PROMOTION_SEQ = "select promotion_seq, employee_id from N_PROMOTION_ADMIN where promotion_seq = ?";
	private static final String INSERT = "insert into N_PROMOTION_ADMIN (promotion_seq, employee_id) values ( ?, ?)";
	private static final String DELETE = "delete from N_PROMOTION_ADMIN where promotion_seq = ?";

}
