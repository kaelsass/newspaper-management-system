package npp.impl.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import npp.dto.AppraisalDto;
import npp.dto.AppraisalRecord;
import npp.dto.ArDto;
import npp.dto.StatusDto;
import npp.dto.WorkInfoDto;
import npp.spec.dao.AppraisalDao;
import npp.spec.dao.AppraisalRecordDao;
import npp.spec.dao.AppraisalReviewerDao;
import npp.spec.dao.TemplateDao;
import npp.spec.dao.Transaction;
import npp.spec.dao.WorkInfoDao;
import npp.utils.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Stateless
public class AppraisalDaoImpl implements AppraisalDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private WorkInfoDao workInfoDao;
	private TemplateDao templateDao;
	private AppraisalRecordDao appraisalRecordDao;
	private AppraisalReviewerDao appraisalReviewerDao;

	// private AcLogDao acLogDao;
	// private AgLogDao agLogDao;
	// private AqLogDao aqLogDao;

	@Inject
	public AppraisalDaoImpl(WorkInfoDao workInfoDao, TemplateDao templateDao,
			AppraisalRecordDao appraisalRecordDao,
			// AcLogDao acLogDao, AgLogDao agLogDao, AqLogDao aqLogDao,
			AppraisalReviewerDao appraisalReviewerDao) {
		this.workInfoDao = workInfoDao;
		this.templateDao = templateDao;
		this.appraisalRecordDao = appraisalRecordDao;
		// this.acLogDao = acLogDao;
		// this.agLogDao = agLogDao;
		// this.aqLogDao = aqLogDao;
		this.appraisalReviewerDao = appraisalReviewerDao;
	}

	@Override
	public List<AppraisalDto> getAllList(Transaction transaction)
			throws IOException {
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_ALL);
			resultSet = prepareStatement.executeQuery();

			List<AppraisalDto> items = new ArrayList<AppraisalDto>();
			while (resultSet.next()) {

				AppraisalDto dto = makeDto(transaction, resultSet);
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

	private AppraisalDto makeDto(Transaction transaction, ResultSet resultSet)
			throws SQLException, IOException {
		// seq, employee_id, date_from, date_to, due, description, template_seq
		int seq = resultSet.getInt(1);
		String employee_id = resultSet.getString(2);
		String fromStr = resultSet.getString(3);
		String toStr = resultSet.getString(4);
		String dueStr = resultSet.getString(5);
		String description = resultSet.getString(6);
		int templateSeq = resultSet.getInt(7);
		double cw = resultSet.getDouble(8);
		double gw = resultSet.getDouble(9);
		double qw = resultSet.getDouble(10);

		WorkInfoDto employee = workInfoDao.findByID(transaction, employee_id);
		Date from = null;
		Date to = null;
		Date due = null;
		try {
			from = DateUtil.getDateFormatInstance().parse(fromStr);
			to = DateUtil.getDateFormatInstance().parse(toStr);
			due = DateUtil.getDateFormatInstance().parse(dueStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StatusDto template = templateDao.findBySeq(transaction, templateSeq);

		List<AppraisalRecord> list = appraisalRecordDao.getAllList(transaction,
				seq);

		List<ArDto> ars = appraisalReviewerDao.getAllList(transaction, seq);

		List<WorkInfoDto> evaluators = new ArrayList<WorkInfoDto>();
		for (ArDto dto : ars) {
			evaluators.add(dto.getEmployee());
		}
		for (WorkInfoDto dto : evaluators) {
			System.out.println("evaluator: " + dto.getId()
					+ " + " + dto.getName());
		}

		AppraisalDto dto = new AppraisalDto(seq, employee, from, to, due,
				description, template, evaluators, cw, gw, qw, list);
		return dto;
	}

	@Override
	public AppraisalDto findBySeq(Transaction transaction, int seq)
			throws IOException {
		AppraisalDto dto = null;

		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(GET_BY_SEQ);
			prepareStatement.setInt(1, seq);
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
	public void add(Transaction transaction, AppraisalDto dto)
			throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(INSERT);
			prepareStatement.setString(1, dto.getEmployee().getId());
			prepareStatement.setString(2, DateUtil.getDateFormatInstance()
					.format(dto.getFrom()));
			prepareStatement.setString(3, DateUtil.getDateFormatInstance()
					.format(dto.getTo()));
			prepareStatement.setString(4, DateUtil.getDateFormatInstance()
					.format(dto.getDue()));
			prepareStatement.setString(5, dto.getDescription());
			prepareStatement.setInt(6, dto.getTemplate().getSeq());
			prepareStatement.setDouble(7, dto.getCompetencyWeight());
			prepareStatement.setDouble(8, dto.getGoalWeight());
			prepareStatement.setDouble(9, dto.getQuestionWeight());
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
	public int getNewInsertedSeq(Transaction transaction) throws IOException {
		int seq = 0;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection
					.prepareStatement(GET_NEW_INSERTED_SEQ);
			resultSet = prepareStatement.executeQuery();

			if (resultSet.next()) {
				seq = resultSet.getInt(1);
			}
			return seq;
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
	public void update(Transaction transaction, AppraisalDto dto)
			throws IOException {
		PreparedStatement prepareStatement = null;
		// employee_id, date_from, date_to, due, description, template_seq
		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(UPDATE);
			prepareStatement.setString(1, dto.getEmployee().getId());
			prepareStatement.setString(2, DateUtil.getDateFormatInstance()
					.format(dto.getFrom()));
			prepareStatement.setString(3, DateUtil.getDateFormatInstance()
					.format(dto.getTo()));
			prepareStatement.setString(4, DateUtil.getDateFormatInstance()
					.format(dto.getDue()));
			prepareStatement.setString(5, dto.getDescription());
			prepareStatement.setInt(6, dto.getTemplate().getSeq());
			prepareStatement.setDouble(7, dto.getCompetencyWeight());
			prepareStatement.setDouble(8, dto.getGoalWeight());
			prepareStatement.setDouble(9, dto.getQuestionWeight());
			prepareStatement.setInt(10, dto.getSeq());

			prepareStatement.executeUpdate();

			// List<AppraisalRecord> list = dto.getRecords();
			// for(AppraisalRecord record : list)
			// {
			// for(AcLog log : record.getAcLogs())
			// {
			// acLogDao.update(transaction, log);
			// }
			// for(AgLog log : record.getAgLogs())
			// {
			// agLogDao.update(transaction, log);
			// }
			// for(AqLog log : record.getAqLogs())
			// {
			// aqLogDao.update(transaction, log);
			// }
			//
			// }
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
	public void delete(Transaction transaction, int seq) throws IOException {
		PreparedStatement prepareStatement = null;

		try {
			Connection connection = transaction.getResource(Connection.class);
			prepareStatement = connection.prepareStatement(DELETE);
			prepareStatement.setInt(1, seq);
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

	private static final String GET_NEW_INSERTED_SEQ = "select max(seq) from s_appraisal";
	private static final String GET_ALL = "select seq, employee_id, date_from, date_to, due, description, template_seq, cweight, gweight, qweight from s_appraisal order by seq";
	private static final String GET_BY_SEQ = "select seq, employee_id, date_from, date_to, due, description, template_seq, cweight, gweight, qweight from s_appraisal where seq = ?";
	private static final String INSERT = "insert into s_appraisal (employee_id, date_from, date_to, due, description, template_seq, cweight, gweight, qweight) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "update s_appraisal set employee_id = ?, date_from = ?, date_to = ?, due = ?, description = ?, template_seq = ?, cweight = ?, gweight = ?, qweight = ? where seq = ?";
	private static final String DELETE = "delete from s_appraisal where seq = ?";

}
