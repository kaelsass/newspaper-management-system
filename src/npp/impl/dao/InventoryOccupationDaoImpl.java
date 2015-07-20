package npp.impl.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import npp.condition.DynamicQuery;
import npp.condition.InventoryOccupationCondition;
import npp.condition.Parameter;
import npp.dto.AdDto;
import npp.dto.InventoryDto;
import npp.dto.IssueDto;
import npp.dto.NonSubscriberDto;
import npp.dto.RetailDto;
import npp.dto.ReturnDto;
import npp.dto.SubscriberDto;
import npp.spec.dao.AdDao;
import npp.spec.dao.InventoryOccupationDao;
import npp.spec.dao.IssueDao;
import npp.spec.dao.NonSubscriberDao;
import npp.spec.dao.RetailDao;
import npp.spec.dao.ReturnDao;
import npp.spec.dao.SubscriberDao;
import npp.spec.dao.Transaction;
import npp.utils.DateUtil;


//@Stateless
public class InventoryOccupationDaoImpl implements InventoryOccupationDao {

	private IssueDao IssueDao;
	private SubscriberDao subscriberDao;
	private NonSubscriberDao nonSubscriberDao;
	private RetailDao retailDao;
	private ReturnDao returnDao;
	private AdDao adDao;

	@Inject
	public InventoryOccupationDaoImpl(IssueDao issueDao, SubscriberDao subscriberDao, NonSubscriberDao nonSubscriberDao, RetailDao retailDao,
			ReturnDao returnDao, AdDao adDao) {
		this.IssueDao = issueDao;
		this.subscriberDao = subscriberDao;
		this.nonSubscriberDao = nonSubscriberDao;
		this.retailDao = retailDao;
		this.returnDao = returnDao;
		this.adDao = adDao;
	}

	@Override
	public List<InventoryDto> getAllList(Transaction transaction,
			InventoryOccupationCondition condition) throws IOException {
		ArrayList<InventoryDto> list = new ArrayList<InventoryDto>();
		List<IssueDto> issueList = getIssues(transaction, condition);

		for(IssueDto dto : issueList)
		{
			List<SubscriberDto> subscribers = getSubscribers(transaction, condition, dto);
			List<NonSubscriberDto> nonSubscribers = getNonSubscribers(transaction, condition, dto);
			List<RetailDto> retails = getRetails(transaction, condition, dto);
			List<ReturnDto> returns = getReturns(transaction, condition, dto);
			List<AdDto> ads = getAds(transaction, condition, dto);
			InventoryDto inDto = new InventoryDto(dto, subscribers, nonSubscribers, retails, returns, ads);
			list.add(inDto);
		}
		return list;
	}

	private List<IssueDto> getIssues(Transaction transaction,
			InventoryOccupationCondition condition) throws IOException {
		DynamicQuery dq = new DynamicQuery();

		if(condition.getFrom() != null)
		{
			dq.addParameter(new Parameter("time", ">=", DateUtil.getDateWithTimeFormatInstance().format(condition.getFrom())));
		}
		if(condition.getTo() != null)
		{
			dq.addParameter(new Parameter("time", "<=", DateUtil.getDateWithTimeFormatInstance().format(condition.getTo())));
		}
		if(condition.getNewspaperSeqs().length > 0)
		{
			int[] seqs = condition.getNewspaperSeqs();
			for(int seq : seqs)
			{
				Parameter para = new Parameter("newspaper_seq", "=", seq);
				para.setType(Parameter.OR);
				dq.addParameter(para);
			}
		}

		List<IssueDto> issueList = IssueDao.getAllList(transaction, dq);
		return issueList;
	}

	private List<SubscriberDto> getSubscribers(Transaction transaction,
			InventoryOccupationCondition condition, IssueDto dto) throws IOException {
		DynamicQuery dq = initQuery(condition);

		if(dto.getNewspaper().getSeq() > 0)
		{
			dq.addParameter(new Parameter("newspaper_seq", "=", dto.getNewspaper().getSeq()));
		}
		if(dto.getTime() != null)
		{
			dq.addParameter(new Parameter("date_from", "<=", DateUtil.getDateWithTimeFormatInstance().format(dto.getTime())));
			dq.addParameter(new Parameter("date_to", ">=", DateUtil.getDateWithTimeFormatInstance().format(dto.getTime())));
		}
		return subscriberDao.getAllList(transaction, dq);
	}

	private DynamicQuery initQuery(InventoryOccupationCondition condition) {
		DynamicQuery dq = new DynamicQuery();

		dq.addParameter(new Parameter("occupation_seq", "=", condition.getOccupationSeq()));


		return dq;
	}

	private List<NonSubscriberDto> getNonSubscribers(
			Transaction transaction, InventoryOccupationCondition condition, IssueDto dto) throws IOException {
		DynamicQuery dq = initQuery(condition);
		if(dto.getSeq() > 0)
		{
			dq.addParameter(new Parameter("issue_seq", "=", dto.getSeq()));
		}

		return nonSubscriberDao.getAllList(transaction, dq);
	}

	private List<RetailDto> getRetails(Transaction transaction,
			InventoryOccupationCondition condition, IssueDto dto) throws IOException {
		DynamicQuery dq = initQuery(condition);

		if(dto.getSeq() > 0)
		{
			dq.addParameter(new Parameter("issue_seq", "=", dto.getSeq()));
		}

		return retailDao.getAllList(transaction, dq);
	}

	private List<ReturnDto> getReturns(Transaction transaction,
			InventoryOccupationCondition condition, IssueDto dto) throws IOException {
		DynamicQuery dq = new DynamicQuery();

		if(dto.getSeq() > 0)
		{
			dq.addParameter(new Parameter("issue_seq", "=", dto.getSeq()));
		}

		return returnDao.getAllList(transaction, dq);
	}

	private List<AdDto> getAds(Transaction transaction, InventoryOccupationCondition condition, IssueDto dto) throws IOException {
		DynamicQuery dq = new DynamicQuery();

		if(dto.getNewspaper().getSeq() > 0)
		{
			dq.addParameter(new Parameter("newspaper_seq", "=", dto.getNewspaper().getSeq()));
		}
		if(dto.getTime() != null)
		{
			dq.addParameter(new Parameter("date_from", "<=", DateUtil.getDateWithTimeFormatInstance().format(dto.getTime())));
			dq.addParameter(new Parameter("date_to", ">=", DateUtil.getDateWithTimeFormatInstance().format(dto.getTime())));
		}
		return adDao.getAllList(transaction, dq);
	}
}
