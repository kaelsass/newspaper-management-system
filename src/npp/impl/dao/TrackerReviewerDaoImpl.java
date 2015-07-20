package npp.impl.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import npp.dto.TrackerReviewerDto;
import npp.spec.dao.TrackerReviewerDao;
import npp.spec.dao.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Stateless
public class TrackerReviewerDaoImpl implements TrackerReviewerDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private TrackerReviewerDto makeDto(Transaction transaction, ResultSet resultSet) throws SQLException, IOException {
		int trackerSeq = resultSet.getInt(1);
		String employeeID = resultSet.getString(2);
		TrackerReviewerDto dto = new TrackerReviewerDto(trackerSeq, employeeID);
		return dto;
	}


	@Override
	public List<TrackerReviewerDto> findByTrackerSeq(Transaction transaction, int trackerSeq) throws IOException {
		TrackerReviewerDto dto = null;

		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_BY_TRACKER_SEQ);
			prepareStatement.setInt(1, trackerSeq);
			resultSet = prepareStatement.executeQuery();
			ArrayList<TrackerReviewerDto> list = new ArrayList<TrackerReviewerDto>();
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
			TrackerReviewerDto dto) throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setInt(1, dto.getTrackerSeq());
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
	public void delete(Transaction transaction, int trackerSeq)
			throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(DELETE);
			prepareStatement.setInt(1, trackerSeq);
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

	private static final String GET_BY_TRACKER_SEQ = "select tracker_seq, employee_id from S_TRACKER_REVIEWER where tracker_seq = ?";
	private static final String INSERT = "insert into S_TRACKER_REVIEWER (tracker_seq, employee_id) values ( ?, ?)";
	private static final String DELETE = "delete from S_TRACKER_REVIEWER where tracker_seq = ?";

}
