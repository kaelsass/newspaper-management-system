package npp.impl.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import npp.dto.AcDto;
import npp.dto.CompetencyDto;
import npp.spec.dao.AppraisalCompetencyDao;
import npp.spec.dao.CompetencyDao;
import npp.spec.dao.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Stateless
public class AppraisalCompetencyDaoImpl implements AppraisalCompetencyDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private CompetencyDao competencyDao;

	@Inject
	public AppraisalCompetencyDaoImpl(CompetencyDao competencyDao)
	{
		this.competencyDao = competencyDao;
	}


	@Override
	public List<AcDto> getAllList(Transaction transaction, int appraisalSeq) throws IOException {
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_ALL);
			prepareStatement.setInt(1, appraisalSeq);
			resultSet = prepareStatement.executeQuery();

			List<AcDto> items = new ArrayList<AcDto>();
			while (resultSet.next()) {

				AcDto dto = makeDto(transaction, resultSet);
				items.add(dto);
			}

			List<CompetencyDto> bases = competencyDao.getAllList(transaction);
			List<AcDto> allList = new ArrayList<AcDto>();
			for(CompetencyDto cur : bases)
			{
				AcDto dto = new AcDto(appraisalSeq, cur, 0, false);
				allList.add(dto);
			}
			for(int i = 0; i < allList.size(); i++)
			{
				for(AcDto selected : items)
				{
					if(selected.getCompetency().getSeq() == allList.get(i).getCompetency().getSeq())
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

	private AcDto makeDto(Transaction transaction, ResultSet resultSet) throws SQLException, IOException {
		int appraisalSeq = resultSet.getInt(1);
		int competencySeq = resultSet.getInt(2);
		int ratio = resultSet.getInt(3);

		CompetencyDto competency = competencyDao.findBySeq(transaction, competencySeq);

		AcDto dto = new AcDto(appraisalSeq, competency, ratio, true);
		return dto;
	}

	@Override
	public void add(Transaction transaction,
			AcDto dto) throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setInt(1, dto.getAppraisalSeq());
			prepareStatement.setInt(2, dto.getCompetency().getSeq());
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
	private static final String GET_ALL = "select appraisal_seq, competency_seq, ratio from S_APPRAISAL_COMPETENCY where appraisal_seq = ?";
	private static final String INSERT = "insert into S_APPRAISAL_COMPETENCY (appraisal_seq, competency_seq, ratio) values ( ?, ?, ?)";
	private static final String DELETE = "delete from S_APPRAISAL_COMPETENCY where appraisal_seq = ?";

}
