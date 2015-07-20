package npp.impl.controler;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import npp.condition.AppraisalCondition;
import npp.condition.DynamicQuery;
import npp.condition.Parameter;
import npp.dto.AcLog;
import npp.dto.AgLog;
import npp.dto.AppraisalDto;
import npp.dto.AppraisalRecord;
import npp.dto.AqLog;
import npp.dto.ArDto;
import npp.dto.EvalGroupDto;
import npp.dto.WorkInfoDto;
import npp.faces.AppraisalListModel;
import npp.spec.manager.SessionManager;
import npp.spec.service.AcLogService;
import npp.spec.service.AgLogService;
import npp.spec.service.AppraisalRecordService;
import npp.spec.service.AppraisalReviewerService;
import npp.spec.service.AppraisalService;
import npp.spec.service.AqLogService;
import npp.spec.service.WorkInfoService;

import org.primefaces.model.DualListModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@SessionScoped
public class AppraisalControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 469282104365892582L;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private List<AppraisalDto> baseList = null;
	private AppraisalListModel listModel = null;

	private AppraisalDto appraisal;
	private WorkInfoDto employee;
	private AppraisalRecord record;

	private DualListModel<WorkInfoDto> evaluators;

	// private List<AppraisalRecord> records;
	//
	// private List<AcEmployeeDto> acEmployees;
	// private List<AgEmployeeDto> agEmployees;
	// private List<AqEmployeeDto> aqEmployees;
	//
	// @Inject
	// private AppraisalRecordService appraisalRecordService;

	// private int first;
	// private boolean addMode;
	// private boolean editMode;
	private boolean deleteMode;

	private AppraisalCondition condition;

	private List<AppraisalDto> selecteds;
	private AppraisalDto editDto;

	@Inject
	private AppraisalService appraisalService;
	@Inject
	private WorkInfoService workInfoService;
	@Inject
	private AppraisalReviewerService appraisalReviewerService;
	@Inject
	private AppraisalRecordService appraisalRecordService;

	@Inject
	private AcLogService acLogService;
	@Inject
	private AgLogService agLogService;
	@Inject
	private AqLogService aqLogService;

	@Inject
	private SessionManager sessionManager;

	@PostConstruct
	public void init() {

		deleteMode = false;
		editDto = new AppraisalDto();
		condition = new AppraisalCondition();
	}

	public AppraisalListModel getListModel() {
		if (baseList == null) {
			try {
				baseList = appraisalService.getAllList();
				listModel = new AppraisalListModel(baseList);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return baseList == null ? new AppraisalListModel(
				new ArrayList<AppraisalDto>()) : listModel;
	}

	public void startAdd() {
		editDto = new AppraisalDto();
	}

	public void startDelete() {
		deleteMode = true;
	}

	public void endDelete() {
		deleteMode = false;
	}

	public void addAppraisal() throws IOException {
		editDto.setEmployee(workInfoService.findByName(editDto.getEmployee()
				.getName()));

		appraisalService.add(editDto);

		int seq = appraisalService.getNewInsertedSeq();
		List<WorkInfoDto> list = evaluators.getTarget();
		for (WorkInfoDto dto : list) {
			appraisalReviewerService
					.add(new ArDto(seq, dto, new EvalGroupDto()));
		}
		clear();
	}

	public void applyRecords() throws IOException {
		List<AppraisalRecord> list = appraisal.getRecords();
		for (AppraisalRecord record : list) {
			for (AcLog log : record.getAcLogs()) {
				acLogService.update(log);
			}
			for (AgLog log : record.getAgLogs()) {
				agLogService.update(log);
			}
			for (AqLog log : record.getAqLogs()) {
				aqLogService.update(log);
			}

		}
	}

	public void clear() {
		System.out.println("clear");
		baseList = null;
		editDto = new AppraisalDto();
		selecteds = null;
		deleteMode = false;
	}

	public void search() throws IOException {
		if (condition.getEmployeeName() != null
				&& !condition.getEmployeeName().equals("")) {
			DynamicQuery query = new DynamicQuery();
			query.addParameter(new Parameter("name", "=", condition
					.getEmployeeName()));
			List<WorkInfoDto> list = workInfoService.getAllList(query);
			if (list.size() > 0)
				condition.setEmployeeID(list.get(0).getId());
		}
		if (condition.getEvaluatorName() != null
				&& !condition.getEvaluatorName().equals("")) {
			DynamicQuery query = new DynamicQuery();
			query.addParameter(new Parameter("name", "=", condition
					.getEmployeeName()));
			List<WorkInfoDto> list = workInfoService.getAllList(query);
			if (list.size() > 0) {
				WorkInfoDto evaluator = list.get(0);
				List<ArDto> ars = appraisalReviewerService
						.findByEmployeeID(evaluator.getId());
				for (ArDto dto : ars) {
					condition.getSeqs().add(dto.getAppraisalSeq());
				}
			}
		}

		clear();
	}

	public void delete() throws IOException {
		try {
			for (AppraisalDto status : selecteds) {
				appraisalService.delete(status.getSeq());
			}
			clear();
		} catch (IOException e) {
			logger.error("Employee Date delete error.", e);
			sessionManager.addGlobalMessageFatal("Employee Data delete error.",
					null);
			throw e;
		}
	}

	public int getStatusListLength() {
		if (listModel == null)
			return 0;
		return listModel.getRowCount();
	}

	public List<AppraisalDto> getBaseList() {
		if (baseList == null) {
			try {
				baseList = appraisalService.getAllList();
				listModel = new AppraisalListModel(baseList);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return baseList;
	}

	public void setBaseList(List<AppraisalDto> baseList) {
		this.baseList = baseList;
	}

	public AppraisalDto getEditStatus() {
		return editDto;
	}

	public void setEditStatus(AppraisalDto editStatus) {
		this.editDto = editStatus;
	}

	public List<AppraisalDto> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<AppraisalDto> selecteds) {
		this.selecteds = selecteds;
	}

	public boolean isDeleteMode() {
		return deleteMode;
	}

	public void setDeleteMode(boolean deleteMode) {
		this.deleteMode = deleteMode;
	}

	public AppraisalDto getEditDto() {
		return editDto;
	}

	public void setEditDto(AppraisalDto editDto) {
		this.editDto = editDto;
	}

	public AppraisalCondition getCondition() {
		return condition;
	}

	public void setCondition(AppraisalCondition condition) {
		this.condition = condition;
	}

	public void startEdit(ActionEvent e) {
		appraisal = (AppraisalDto) e.getComponent().getAttributes()
				.get("appraisal");
	}

	public AppraisalDto getAppraisal() {
		return appraisal;
	}

	public void setAppraisal(AppraisalDto appraisal) {
		this.appraisal = appraisal;
	}

	// public List<AppraisalRecord> getRecords() {
	// if (records == null) {
	// try {
	// records = appraisalRecordService.getAllList(appraisal.getSeq());
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// return records;
	// }
	//
	// public void setRecords(List<AppraisalRecord> records) {
	// this.records = records;
	// }

	// public List<AcEmployeeDto> getAcEmployees() {
	// if (acEmployees == null) {
	// acEmployees = new ArrayList<AcEmployeeDto>();
	// getRecords();
	// if (records.size() == 0)
	// return acEmployees;
	// List<AcLog> list = records.get(0).getAcLogs();
	// for (int i = 0; i < list.size(); i++) {
	// AcEmployeeDto ace = new AcEmployeeDto();
	// ace.setCompetency(list.get(i).getAc().getCompetency());
	// for (int j = 0; j < records.size(); j++) {
	// ace.getLogs().add(records.get(j).getAcLogs().get(i));
	// }
	// acEmployees.add(ace);
	// }
	// }
	// return acEmployees;
	// }
	//
	// public List<AgEmployeeDto> getAgEmployees() {
	// if (agEmployees == null) {
	// agEmployees = new ArrayList<AgEmployeeDto>();
	// getRecords();
	// if (records.size() == 0)
	// return agEmployees;
	// List<AgLog> list = records.get(0).getAgLogs();
	// for (int i = 0; i < list.size(); i++) {
	// AgEmployeeDto ace = new AgEmployeeDto();
	// ace.setGoal(list.get(i).getAg().getGoal());
	// for (int j = 0; j < records.size(); j++) {
	// ace.getLogs().add(records.get(j).getAgLogs().get(i));
	// }
	// agEmployees.add(ace);
	// }
	// }
	// return agEmployees;
	// }
	//
	// public List<AqEmployeeDto> getAqEmployees() {
	// if (aqEmployees == null) {
	// aqEmployees = new ArrayList<AqEmployeeDto>();
	// getRecords();
	// if (records.size() == 0)
	// return aqEmployees;
	// List<AqLog> list = records.get(0).getAqLogs();
	// for (int i = 0; i < list.size(); i++) {
	// AqEmployeeDto ace = new AqEmployeeDto();
	// ace.setQuestion(list.get(i).getAq().getQuestion());
	// for (int j = 0; j < records.size(); j++) {
	// ace.getLogs().add(records.get(j).getAqLogs().get(i));
	// }
	// aqEmployees.add(ace);
	// }
	// }
	// return aqEmployees;
	// }
	//
	// public int getRecordsSize() {
	// return getRecords().size();
	// }

	public WorkInfoDto getEmployee() {
		if (employee == null) {
			List<WorkInfoDto> list = appraisal.getEvaluators();
			if (list.size() > 0)
				employee = new WorkInfoDto(list.get(0));
		}
		return employee == null ? new WorkInfoDto() : employee;
	}

	public void setEmployee(WorkInfoDto employee) {
		this.employee = employee;
	}

	public AppraisalRecord getRecord() {
		if (record == null) {
			for (AppraisalRecord cur : appraisal.getRecords()) {
				if (cur.getEmployee().getId().equals(getEmployee().getId())) {
					record = cur;
					break;
				}
			}
		}
		return record;
	}

	public void setRecord(AppraisalRecord record) {
		this.record = record;
	}

	// public double getRate()
	// {
	// double sum = 0;
	// for(AppraisalRecord record : getRecords())
	// {
	// sum += record.getOverallScore(appraisal);
	// }
	// return sum/getRecords().size();
	// }
	// public String getFormatRate()
	// {
	// return String.format("%.2f", getRate());
	// }

	public DualListModel<WorkInfoDto> getEvaluators() {
		if (evaluators == null) {
			try {
				List<WorkInfoDto> allEmployees = workInfoService
						.getAllList(new DynamicQuery());

				evaluators = new DualListModel<WorkInfoDto>(allEmployees,
						new ArrayList<WorkInfoDto>());

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return evaluators == null ? new DualListModel<WorkInfoDto>(
				new ArrayList<WorkInfoDto>(), new ArrayList<WorkInfoDto>())
				: evaluators;
	}

	public void setEvaluators(DualListModel<WorkInfoDto> evaluators) {
		this.evaluators = evaluators;
	}

	public void dateValidate(FacesContext context, UIComponent component,
			Object value) {
		Date due = (Date) value;
		if (editDto.getFrom() == null || editDto.getTo() == null)
			return;
		if (editDto.getFrom().compareTo(editDto.getTo()) > 0) {
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"To Date should be after From Date", null));
		}
		if (due.compareTo(editDto.getTo()) < 0) {
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Due Date should be after To Date", null));
		}
	}

	public void clearRecords() throws IOException {
		appraisal.setRecords(appraisalRecordService.getAllList(appraisal
				.getSeq()));
	}

	public void changeEvaluator() {

		for (AppraisalRecord cur : appraisal.getRecords()) {
			if (cur.getEmployee().getId().equals(employee.getId())) {
				employee = new WorkInfoDto(cur.getEmployee());
				record = new AppraisalRecord(cur);
				//break;
			}
		}
	}

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	private String outcome;

	public void navigate() {
		FacesContext context = FacesContext.getCurrentInstance();
		appraisal = context.getApplication().evaluateExpressionGet(
				context, "#{appraisal}", AppraisalDto.class);
		NavigationHandler navigationHandler = context.getApplication()
				.getNavigationHandler();
		navigationHandler.handleNavigation(context, null, outcome
				+ "?faces-redirect=true");
		outcome = "";
	}
	public String getCurrentFormatScore()
	{
		if(appraisal == null || record == null)
			return "0.0";
		double score = appraisal.getOverallScore(record);
		return String.format("%.1f", score);
	}
}
