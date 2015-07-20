package npp.impl.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import npp.dto.PromotionNewspaperDto;
import npp.spec.dao.PromotionNewspaperDao;
import npp.spec.dao.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Stateless
public class PromotionNewspaperDaoImpl implements PromotionNewspaperDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private PromotionNewspaperDto makeDto(Transaction transaction, ResultSet resultSet) throws SQLException, IOException {
		int promotionSeq = resultSet.getInt(1);
		int newspaperSeq = resultSet.getInt(2);
		PromotionNewspaperDto dto = new PromotionNewspaperDto(promotionSeq, newspaperSeq);
		return dto;
	}


	@Override
	public List<PromotionNewspaperDto> findByPromotionSeq(Transaction transaction, int promotionSeq) throws IOException {
		PromotionNewspaperDto dto = null;

		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_BY_PROMOTION_SEQ);
			prepareStatement.setInt(1, promotionSeq);
			resultSet = prepareStatement.executeQuery();
			ArrayList<PromotionNewspaperDto> list = new ArrayList<PromotionNewspaperDto>();
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
	public List<PromotionNewspaperDto> findByNewspaperSeq(Transaction transaction, int newspaperSeq) throws IOException {
		PromotionNewspaperDto dto = null;

		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_BY_NEWSPAPER_SEQ);
			prepareStatement.setInt(1, newspaperSeq);
			resultSet = prepareStatement.executeQuery();
			ArrayList<PromotionNewspaperDto> list = new ArrayList<PromotionNewspaperDto>();
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
			PromotionNewspaperDto dto) throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setInt(1, dto.getPromotionSeq());
			prepareStatement.setInt(2, dto.getNewspaperSeq());
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
	private static final String GET_BY_NEWSPAPER_SEQ = "select promotion_seq, newspaper_seq from N_PROMOTION_NEWSPAPER where newspaper_seq = ?";
	private static final String GET_BY_PROMOTION_SEQ = "select promotion_seq, newspaper_seq from N_PROMOTION_NEWSPAPER where promotion_seq = ?";
	private static final String INSERT = "insert into N_PROMOTION_NEWSPAPER (promotion_seq, newspaper_seq) values ( ?, ?)";
	private static final String DELETE = "delete from N_PROMOTION_NEWSPAPER where promotion_seq = ?";

}
