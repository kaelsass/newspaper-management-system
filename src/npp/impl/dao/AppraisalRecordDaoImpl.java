package npp.impl.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import npp.dto.AcDto;
import npp.dto.AcLog;
import npp.dto.AgDto;
import npp.dto.AgLog;
import npp.dto.AppraisalRecord;
import npp.dto.AqDto;
import npp.dto.AqLog;
import npp.dto.ArDto;
import npp.spec.dao.AcLogDao;
import npp.spec.dao.AgLogDao;
import npp.spec.dao.AppraisalCompetencyDao;
import npp.spec.dao.AppraisalGoalDao;
import npp.spec.dao.AppraisalQuestionDao;
import npp.spec.dao.AppraisalRecordDao;
import npp.spec.dao.AppraisalReviewerDao;
import npp.spec.dao.AqLogDao;
import npp.spec.dao.Transaction;


//@Stateless
public class AppraisalRecordDaoImpl implements AppraisalRecordDao {

	private AppraisalReviewerDao appraisalReviewerDao;
	private AppraisalCompetencyDao appraisalCompetencyDao;
	private AppraisalGoalDao appraisalGoalDao;
	private AppraisalQuestionDao appraisalQuestionDao;

	private AcLogDao acLogDao;
	private AgLogDao agLogDao;
	private AqLogDao aqLogDao;

	@Inject
	public AppraisalRecordDaoImpl(AppraisalReviewerDao appraisalReviewerDao, AppraisalCompetencyDao appraisalCompetencyDao,
			AppraisalGoalDao appraisalGoalDao, AppraisalQuestionDao appraisalQuestionDao, AcLogDao acLogDao, AgLogDao agLogDao,
			AqLogDao aqLogDao)
	{
		this.appraisalReviewerDao = appraisalReviewerDao;
		this.appraisalCompetencyDao = appraisalCompetencyDao;
		this.appraisalGoalDao = appraisalGoalDao;
		this.appraisalQuestionDao = appraisalQuestionDao;

		this.acLogDao = acLogDao;
		this.agLogDao = agLogDao;
		this.aqLogDao = aqLogDao;


	}
	@Override
	public List<AppraisalRecord> getAllList(Transaction transaction, int appraisalSeq) throws IOException {
		List<AppraisalRecord> ret = new ArrayList<AppraisalRecord>();
		List<ArDto> arDtos = appraisalReviewerDao.getAllList(transaction, appraisalSeq);
		List<AcDto> acDtos = appraisalCompetencyDao.getAllList(transaction, appraisalSeq);
		List<AgDto> agDtos = appraisalGoalDao.getAllList(transaction, appraisalSeq);
		List<AqDto> aqDtos = appraisalQuestionDao.getAllList(transaction, appraisalSeq);
		for(ArDto ardto : arDtos)
		{
			AppraisalRecord record = new AppraisalRecord();
			record.setEmployee(ardto.getEmployee());
			for(AcDto dto : acDtos)
			{
				if(!dto.isSelected())
					continue;
				AcLog log = acLogDao.find(transaction, dto, ardto.getEmployee().getId());
				if(log == null)
				{
					log = new AcLog(dto, ardto.getEmployee().getId(), 0, "");
					acLogDao.add(transaction, log);
				}
				record.getAcLogs().add(log);
			}
			for(AgDto dto : agDtos)
			{
				if(!dto.isSelected())
					continue;
				AgLog log = agLogDao.find(transaction, dto, ardto.getEmployee().getId());
				if(log == null)
				{
					log = new AgLog(dto, ardto.getEmployee().getId(), 0, "");
					agLogDao.add(transaction, log);
				}
				record.getAgLogs().add(log);
			}
			for(AqDto dto : aqDtos)
			{
				if(!dto.isSelected())
					continue;
				AqLog log = aqLogDao.find(transaction, dto, ardto.getEmployee().getId());
				if(log == null)
				{
					log = new AqLog(dto, ardto.getEmployee().getId(), 0, "");
					aqLogDao.add(transaction, log);
				}
				record.getAqLogs().add(log);
			}
			ret.add(record);
		}
		return ret;
	}

}
