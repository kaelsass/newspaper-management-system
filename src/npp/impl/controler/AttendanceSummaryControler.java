package npp.impl.controler;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import npp.condition.AttendanceSummaryCondition;
import npp.condition.WorkInfoCondition;
import npp.dto.DayRecord;
import npp.dto.PersonRecordDto;
import npp.dto.PunchDto;
import npp.dto.WorkInfoDto;
import npp.faces.PersonRecordListModel;
import npp.spec.manager.SessionManager;
import npp.spec.service.DepartmentService;
import npp.spec.service.JobTitleService;
import npp.spec.service.PunchService;
import npp.spec.service.StatusService;
import npp.spec.service.WorkInfoService;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;


@Named
@SessionScoped
public class AttendanceSummaryControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6570881403859382396L;

	private List<PersonRecordDto> baseList = null;
	private PersonRecordListModel listModel = null;

	private CartesianChartModel lineModel = null;

	private AttendanceSummaryCondition condition;
	private WorkInfoCondition workInfoCondition;

	private List<PersonRecordDto> selecteds;
	private String total = "";
	private int viewMode = 0;

	@Inject
	private PunchService punchService;

	@Inject
	private WorkInfoService workInfoService;
	@Inject
	private JobTitleService jobTitleService;
	@Inject
	private StatusService statusService;
	@Inject
	private DepartmentService departmentService;

	@Inject
	private SessionManager sessionManager;

	@PostConstruct
	public void init() {
		condition = new AttendanceSummaryCondition();
		workInfoCondition = new WorkInfoCondition();
	}

	public PersonRecordListModel getListModel() {
		if (baseList == null) {
			try {
				List<PunchDto> punchList = punchService.getAllList(condition.genQuery());
				baseList = makeList(punchList);
				listModel = new PersonRecordListModel(baseList);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return baseList == null ? new PersonRecordListModel(new ArrayList<PersonRecordDto>())
				: listModel;
	}

	private List<PersonRecordDto> makeList(List<PunchDto> punchList){
		ArrayList<DayRecord> dayRecords = aggregateByDate(punchList);
		ArrayList<PersonRecordDto> personRecords = aggregateByPerson(dayRecords);
		fillEmptyRecords(personRecords);
		sort(personRecords);
		return personRecords;
	}

	private void sort(ArrayList<PersonRecordDto> personRecords) {
		for(PersonRecordDto pr : personRecords)
		{
			sortForDate(pr.getList());
		}
	}

	private void sortForDate(ArrayList<DayRecord> list) {
		Collections.sort(list, new DateComparator());
	}

	private static class DateComparator implements Comparator<DayRecord>
	{
		@Override
		public int compare(DayRecord o1, DayRecord o2) {
			return o1.getDate().compareTo(o2.getDate());
		}

	}

	private void fillEmptyRecords(ArrayList<PersonRecordDto> records) {
		for(WorkInfoDto dto : condition.getEmployees())
		{
			Date from = condition.getFrom();
			Date to = condition.getTo();
			PersonRecordDto pr = getRecordsForPerson(dto, records);
			if(pr != null)
			{
				for(Date cur = new Date(from.getTime()); cur.compareTo(to) <= 0; cur = new Date(cur.getTime() + 24*3600*1000))
				{
					DayRecord dr = getRecordsForDate(cur, pr.getList());
					if(dr == null)
					{
						pr.getList().add(makeRecordForDate(cur));
					}
				}
			}
			else
			{
				PersonRecordDto newPerson = new PersonRecordDto();
				newPerson.setEmployee(dto);
				newPerson.setList(makeRecordsForDate(from, to));
				records.add(newPerson);
			}
		}
	}

	private ArrayList<DayRecord> makeRecordsForDate(Date from, Date to) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		ArrayList<DayRecord> drs = new ArrayList<DayRecord>();
		for(Date cur = new Date(from.getTime()); cur.compareTo(to) <= 0; cur = new Date(cur.getTime() + 24*3600*1000))
		{
			DayRecord dr = new DayRecord();
			dr.setDate(df.format(cur));
			drs.add(dr);
		}
		return drs;
	}

	private DayRecord makeRecordForDate(Date cur) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		DayRecord dr = new DayRecord();
		dr.setDate(df.format(cur));
		return dr;
	}

	private DayRecord getRecordsForDate(Date date, ArrayList<DayRecord> list) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		for(DayRecord record : list)
		{
			if(record.getDate().equals(df.format(date)))
				return record;
		}
		return null;
	}

	private PersonRecordDto getRecordsForPerson(WorkInfoDto dto,
			ArrayList<PersonRecordDto> records) {
		for(PersonRecordDto record : records)
		{
			if(record.getEmployee().getId().equals(dto.getId()))
			{
				return record;
			}
		}
		return null;
	}

	private ArrayList<PersonRecordDto> aggregateByPerson(
			ArrayList<DayRecord> dayRecords) {
		ArrayList<PersonRecordDto> records = new ArrayList<PersonRecordDto>();
		ArrayList<DayRecord> curList = new ArrayList<DayRecord>();
		DayRecord pre = null;
		for(int i = 0; i < dayRecords.size(); i++)
		{
			DayRecord cur = (DayRecord) dayRecords.get(i);
			if(!isSamePerson(pre, cur))
			{
				PersonRecordDto record = new PersonRecordDto();
				record.setEmployee(pre.getList().get(0).getEmployee());
				record.setList(curList);
				records.add(record);
				curList = new ArrayList<DayRecord>();
			}
			curList.add(cur);
			pre = cur;
		}
		if(pre != null)
		{
			PersonRecordDto record = new PersonRecordDto();
			record.setEmployee(pre.getList().get(0).getEmployee());
			record.setList(curList);
			records.add(record);
		}
		return records;
	}

	private boolean isSamePerson(DayRecord d1, DayRecord d2) {
		if(d1 == null || d2 == null)
			return true;
		return d1.getList().get(0).getEmployee().getId().equals(d2.getList().get(0).getEmployee().getId());
	}

	private ArrayList<DayRecord> aggregateByDate(
			List<PunchDto> punchList) {
		ArrayList<DayRecord> records = new ArrayList<DayRecord>();
		ArrayList<PunchDto> curList = new ArrayList<PunchDto>();
		PunchDto pre = null;
		for(int i = 0; i < punchList.size(); i++)
		{
			PunchDto cur = (PunchDto) punchList.get(i);
			if(!isSameDate(pre, cur))
			{
				DayRecord record = new DayRecord();
				record.setDate(getDate(pre.getPunchInTime()));
				record.setList(curList);
				records.add(record);
				curList = new ArrayList<PunchDto>();
			}
			curList.add(cur);
			pre = cur;
		}
		if(pre != null)
		{
			DayRecord record = new DayRecord();
			record.setDate(getDate(pre.getPunchInTime()));
			record.setList(curList);
			records.add(record);
		}
		return records;
	}

	private boolean isSameDate(PunchDto p1, PunchDto p2) {
		if(p1 == null || p2 == null)
			return true;
		return (p1.getEmployee().getId().equals(p2.getEmployee().getId())) && (getDate(p1.getPunchInTime()).equals(getDate(p2.getPunchInTime())));
	}

	private String getDate(String punchTime) {

		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = df.parse(punchTime);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(date);
		} catch (ParseException e) {
			return "";
		}

	}

	public void search() throws IOException {
		clear();
		if(condition.getFrom().compareTo(condition.getTo()) > 0)
		{
			sessionManager.addGlobalMessageFatal("'From' date cannot be later than 'To' date.", null);
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_FATAL,
					"'From' date cannot be later than 'To' date.",
					null));
		}
		workInfoCondition.setName(condition.getEmployeeName());
		workInfoCondition.setJobTitle(condition.getJobTitle().getSeq());
		workInfoCondition.setStatus(condition.getStatus().getSeq());
		workInfoCondition.setDepartment(condition.getDepartment().getSeq());
		condition.setJobTitle(jobTitleService.findBySeq(condition.getJobTitle().getSeq()));
		condition.setStatus(statusService.findBySeq(condition.getStatus().getSeq()));
		condition.setDepartment(departmentService.findBySeq(condition.getDepartment().getSeq()));
		List<WorkInfoDto> list = workInfoService.getAllList(workInfoCondition
				.genQuery());
		for (WorkInfoDto dto : list) {
			condition.addEmployee(dto);
		}
	}

	public void clear() {
		baseList = null;
		selecteds = null;
		condition.getEmployees().clear();
	}

	public int getStatusListLength() {
		if (listModel == null)
			return 0;
		return listModel.getRowCount();
	}

	public AttendanceSummaryCondition getCondition() {
		return condition;
	}

	public void setCondition(AttendanceSummaryCondition condition) {
		this.condition = condition;
	}

	public String getTotal() {
		double summary = 0.0d;

		for (PersonRecordDto p : baseList) {
			summary += Double.parseDouble(p.getTotal());
		}
		total = String.format("%.2f", summary);
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public List<PersonRecordDto> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<PersonRecordDto> selecteds) {
		this.selecteds = selecteds;
	}

	public CartesianChartModel getLineModel() {
		initCategoryModel();
		return lineModel;
	}

	public void setLineModel(CartesianChartModel lineModel) {
		this.lineModel = lineModel;
	}
	private void initCategoryModel() {
		lineModel = new CartesianChartModel();
        for(PersonRecordDto pr : baseList)
        {
            ChartSeries person = new ChartSeries();
            person.setLabel(pr.getEmployee().getName());
            for(DayRecord dr : pr.getList())
            {
            	person.set(dr.getDate(), Double.parseDouble(dr.getTotal()));
            }
            lineModel.addSeries(person);
        }
    }

	public WorkInfoCondition getWorkInfoCondition() {
		return workInfoCondition;
	}

	public void setWorkInfoCondition(WorkInfoCondition workInfoCondition) {
		this.workInfoCondition = workInfoCondition;
	}

	public int getViewMode() {
		return viewMode;
	}

	public void setViewMode(int viewMode) {
		this.viewMode = viewMode;
	}
}
