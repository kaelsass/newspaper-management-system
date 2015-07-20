package npp.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import npp.utils.DateUtil;

public class AppraisalDto {
	private int seq;
	private WorkInfoDto employee;
	private Date from;
	private Date to;
	private Date due;
	private String description;
	private StatusDto template;
	private List<WorkInfoDto> evaluators;
	private double competencyWeight;
	private double goalWeight;
	private double questionWeight;
	private List<AppraisalRecord> records;

	private List<AcEmployeeDto> acEmployees;
	private List<AgEmployeeDto> agEmployees;
	private List<AqEmployeeDto> aqEmployees;

	// You must declared default constructor for Framework.
	public AppraisalDto() {
		init(0, new WorkInfoDto(), null, null, null, "", new StatusDto(),
				new ArrayList<WorkInfoDto>(), 50, 50, 0,
				new ArrayList<AppraisalRecord>());
	}

	public AppraisalDto(int seq, WorkInfoDto employee, Date from, Date to,
			Date due, String description, StatusDto template,
			List<WorkInfoDto> evaluators, double cw, double gw, double qw,
			List<AppraisalRecord> records) {
		init(seq, employee, from, to, due, description, template, evaluators,
				cw, gw, qw, records);
	}

	public AppraisalDto(AppraisalDto dto) {
		init(dto.getSeq(), dto.getEmployee(), dto.getFrom(), dto.getTo(),
				dto.getDue(), dto.getDescription(), dto.getTemplate(),
				dto.getEvaluators(), dto.getCompetencyWeight(),
				dto.getGoalWeight(), dto.getQuestionWeight(), dto.getRecords());
	}

	private void init(int seq, WorkInfoDto employee, Date from, Date to,
			Date due, String description, StatusDto template,
			List<WorkInfoDto> evaluators, double cw, double gw, double qw,
			List<AppraisalRecord> records) {
		this.seq = seq;
		this.employee = employee;
		this.from = from;
		this.to = to;
		this.due = due;
		this.description = description;
		this.template = template;
		this.evaluators = evaluators;
		this.competencyWeight = cw;
		this.goalWeight = gw;
		this.questionWeight = qw;
		this.records = records;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public Date getDue() {
		return due;
	}

	public void setDue(Date due) {
		this.due = due;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public StatusDto getTemplate() {
		return template;
	}

	public void setTemplate(StatusDto template) {
		this.template = template;
	}

	public WorkInfoDto getEmployee() {
		return employee;
	}

	public void setEmployee(WorkInfoDto employee) {
		this.employee = employee;
	}

	public List<WorkInfoDto> getEvaluators() {
		return evaluators;
	}

	public void setEvaluators(List<WorkInfoDto> evaluators) {
		this.evaluators = evaluators;
	}

	public String getFormatEvaluators() {
		StringBuffer sb = new StringBuffer();
		for (WorkInfoDto dto : evaluators) {
			sb.append(dto.getName() + ", ");
		}
		if (sb.length() > 0) {
			return sb.substring(0, sb.length() - 2);
		} else
			return sb.toString();
	}

	public String getFormatFrom() {
		return DateUtil.getDateFormatInstance().format(from);
	}

	public String getFormatTo() {
		return DateUtil.getDateFormatInstance().format(to);
	}

	public String getFormatDue() {
		return DateUtil.getDateFormatInstance().format(due);
	}

	public int getRate() {
		// not finished
		return 0;
	}

	public double getCompetencyWeight() {
		return competencyWeight;
	}

	public void setCompetencyWeight(double competencyWeight) {
		this.competencyWeight = competencyWeight;
	}

	public double getGoalWeight() {
		return goalWeight;
	}

	public void setGoalWeight(double goalWeight) {
		this.goalWeight = goalWeight;
	}

	public double getQuestionWeight() {
		return questionWeight;
	}

	public void setQuestionWeight(double questionWeight) {
		this.questionWeight = questionWeight;
	}

	public List<AppraisalRecord> getRecords() {
		return records;
	}

	public void setRecords(List<AppraisalRecord> records) {
		this.records = records;
	}

	public List<AcEmployeeDto> getAcEmployees() {
		if (acEmployees == null) {
			acEmployees = new ArrayList<AcEmployeeDto>();
			if (records.size() == 0)
				return acEmployees;
			List<AcLog> list = records.get(0).getAcLogs();
			for (int i = 0; i < list.size(); i++) {
				AcEmployeeDto ace = new AcEmployeeDto();
				ace.setCompetency(list.get(i).getAc().getCompetency());
				for (int j = 0; j < records.size(); j++) {
					ace.getLogs().add(records.get(j).getAcLogs().get(i));
				}
				acEmployees.add(ace);
			}
		}
		return acEmployees;
	}

	public List<AgEmployeeDto> getAgEmployees() {
		if (agEmployees == null) {
			agEmployees = new ArrayList<AgEmployeeDto>();
			if (records.size() == 0)
				return agEmployees;
			List<AgLog> list = records.get(0).getAgLogs();
			for (int i = 0; i < list.size(); i++) {
				AgEmployeeDto ace = new AgEmployeeDto();
				ace.setGoal(list.get(i).getAg().getGoal());
				for (int j = 0; j < records.size(); j++) {
					ace.getLogs().add(records.get(j).getAgLogs().get(i));
				}
				agEmployees.add(ace);
			}
		}
		return agEmployees;
	}

	public List<AqEmployeeDto> getAqEmployees() {
		if (aqEmployees == null) {
			aqEmployees = new ArrayList<AqEmployeeDto>();
			if (records.size() == 0)
				return aqEmployees;
			List<AqLog> list = records.get(0).getAqLogs();
			for (int i = 0; i < list.size(); i++) {
				AqEmployeeDto ace = new AqEmployeeDto();
				ace.setQuestion(list.get(i).getAq().getQuestion());
				for (int j = 0; j < records.size(); j++) {
					ace.getLogs().add(records.get(j).getAqLogs().get(i));
				}
				aqEmployees.add(ace);
			}
		}
		return aqEmployees;
	}

	public int getRecordsSize() {
		return getRecords().size();
	}

	public double getScore() {
		double sum = 0;
		for (AppraisalRecord record : getRecords()) {
			sum += getOverallScore(record);
		}
		return sum / getRecords().size();
	}

	public double getOverallScore(AppraisalRecord record) {

		return record.getAcScore() * this.getCompetencyWeight() / 100
				+ record.getAgScore() * this.getGoalWeight() / 100 + record.getAqScore()
				* this.getQuestionWeight() / 100;
	}

	public String getFormatScore() {
		return String.format("%.1f", getScore());
	}

}
