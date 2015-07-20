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
import npp.dto.AgDto;
import npp.dto.GoalDto;
import npp.spec.dao.AppraisalGoalDao;
import npp.spec.dao.GoalDao;
import npp.spec.dao.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Stateless
public class AppraisalGoalDaoImpl implements AppraisalGoalDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private GoalDao goalDao;

	@Inject
	public AppraisalGoalDaoImpl(GoalDao goalDao)
	{
		this.goalDao = goalDao;
	}
	@Override
	public List<AgDto> getAllList(Transaction transaction, int appraisalSeq) throws IOException {
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_ALL);
			prepareStatement.setInt(1, appraisalSeq);
			resultSet = prepareStatement.executeQuery();

			List<AgDto> items = new ArrayList<AgDto>();
			while (resultSet.next()) {

				AgDto dto = makeDto(transaction, resultSet);
				items.add(dto);
			}

			List<GoalDto> bases = goalDao.getAllList(transaction, new DynamicQuery());
			List<AgDto> allList = new ArrayList<AgDto>();
			for(GoalDto cur : bases)
			{
				AgDto dto = new AgDto(appraisalSeq, cur, 0, false);
				allList.add(dto);
			}
			for(int i = 0; i < allList.size(); i++)
			{
				for(AgDto selected : items)
				{
					if(selected.getGoal().getSeq() == allList.get(i).getGoal().getSeq())
					{
						allList.set(i, selected);
					}
				}
			}

			return allList;
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

	private AgDto makeDto(Transaction transaction, ResultSet resultSet) throws SQLException, IOException {
		int appraisalSeq = resultSet.getInt(1);
		int goalSeq = resultSet.getInt(2);
		int ratio = resultSet.getInt(3);

		GoalDto goal = goalDao.findBySeq(transaction, goalSeq);

		AgDto dto = new AgDto(appraisalSeq, goal, ratio, true);
		return dto;
	}

	@Override
	public void add(Transaction transaction,
			AgDto dto) throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setInt(1, dto.getAppraisalSeq());
			prepareStatement.setInt(2, dto.getGoal().getSeq());
			prepareStatement.setInt(3, dto.getRatio());
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
	public void delete(Transaction transaction, int appraisalSeq)
			throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(DELETE);
			prepareStatement.setInt(1, appraisalSeq);
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

	private static final String GET_ALL = "select appraisal_seq, goal_seq, ratio from S_APPRAISAL_GOAL where appraisal_seq = ?";
	private static final String INSERT = "insert into S_APPRAISAL_GOAL (appraisal_seq, goal_seq, ratio) values ( ?, ?, ?)";
	private static final String DELETE = "delete from S_APPRAISAL_GOAL where appraisal_seq = ?";

}
