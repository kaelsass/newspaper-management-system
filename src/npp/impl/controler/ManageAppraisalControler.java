package npp.impl.controler;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import npp.condition.DynamicQuery;
import npp.dto.AcDto;
import npp.dto.AgDto;
import npp.dto.AppraisalDto;
import npp.dto.AqDto;
import npp.dto.ArDto;
import npp.dto.EvalGroupDto;
import npp.dto.WorkInfoDto;
import npp.faces.AcListModel;
import npp.faces.AgListModel;
import npp.faces.AqListModel;
import npp.spec.manager.SessionManager;
import npp.spec.service.AppraisalCompetencyService;
import npp.spec.service.AppraisalGoalService;
import npp.spec.service.AppraisalQuestionService;
import npp.spec.service.AppraisalReviewerService;
import npp.spec.service.AppraisalService;
import npp.spec.service.WorkInfoService;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;

@Named
@SessionScoped
public class ManageAppraisalControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 4439364197284485516L;

	@Inject
	private SessionManager sessionManager;

	private List<AcDto> acList = null;
	private AcListModel acListModel = null;

	private List<AgDto> agList = null;
	private AgListModel agListModel = null;

	private List<AqDto> aqList = null;
	private AqListModel aqListModel = null;

	private DualListModel<WorkInfoDto> evaluators;


	@Inject
	private AppraisalService appraisalService;

	@Inject
	private AppraisalReviewerService appraisalReviewerService;

	@Inject
	private AppraisalCompetencyService appraisalCompetencyService;
	@Inject
	private AppraisalGoalService appraisalGoalService;
	@Inject
	private AppraisalQuestionService appraisalQuestionService;

	@Inject
	private WorkInfoService workInfoService;

	private AppraisalDto appraisal;

	private boolean editDetailMode = false;
	private boolean editCompetencyMode = false;
	private boolean editGoalMode = false;
	private boolean editQuestionMode = false;

	private int activeIndex = 1;


	@PostConstruct
	public void init() {
		clearAll();
		activeIndex = 1;
	}

	public List<AcDto> getAcList() {
		if (acList == null) {
			try {
				acList = appraisalCompetencyService.getAllList(appraisal
						.getSeq());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return acList == null ? new ArrayList<AcDto>() : acList;
	}

	public AcListModel getAcListModel() {
		acListModel = new AcListModel(getAcList());
		return acListModel;
	}

	public List<AgDto> getAgList() {
		if (agList == null) {
			try {
				agList = appraisalGoalService.getAllList(appraisal.getSeq());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return agList == null ? new ArrayList<AgDto>() : agList;
	}

	public AgListModel getAgListModel() {
		agListModel = new AgListModel(getAgList());
		return agListModel;
	}

	public List<AqDto> getAqList() {
		if (aqList == null) {
			try {
				aqList = appraisalQuestionService
						.getAllList(appraisal.getSeq());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return aqList == null ? new ArrayList<AqDto>() : aqList;
	}

	public AqListModel getAqListModel() {
		aqListModel = new AqListModel(getAqList());
		return aqListModel;
	}

	public void startEdit(ActionEvent e) {
		appraisal = (AppraisalDto) e.getComponent().getAttributes()
				.get("appraisal");
		clearAll();
	}

	public void startEditDetail() {
		clearMode();
		editDetailMode = true;
	}

	public void startEditCompetencies() {
		clearMode();
		editCompetencyMode = true;
	}

	public void startEditGoals() {
		clearMode();
		editGoalMode = true;
	}

	public void startEditQuestions() {
		clearMode();
		editQuestionMode = true;
	}

	public void applyDetail() throws IOException {
		if ((appraisal.getCompetencyWeight() + appraisal.getGoalWeight() + appraisal
				.getQuestionWeight()) > 100) {
			showValidationErrorDialog();
			clearAppraisal();
		} else {
			appraisalService.update(appraisal);
			sessionManager.addGlobalMessageInfo("Detail has been updated.",
					null);
			clearAll();
		}
	}

	private void clearDialog() {
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("cw_w.hide();");
		context.execute("gw_w.hide();");
		context.execute("qw_w.hide();");
	}

	public void applyCompetencies() throws IOException {
		List<AcDto> selecteds = getAcSelecteds();
		int total = 0;
		for (AcDto dto : selecteds) {
			total += dto.getRatio();
		}
		if (total != 100) {
			showValidationErrorDialog();
			return;
		}

		appraisalCompetencyService.delete(appraisal.getSeq());
		for (AcDto dto : selecteds) {
			appraisalCompetencyService.add(dto);
		}
		sessionManager.addGlobalMessageInfo("Competency Section has been updated.",
				null);
		clearAll();
	}

	public void applyGoals() throws IOException {
		List<AgDto> selecteds = getAgSelecteds();
		int total = 0;
		for (AgDto dto : selecteds) {
			total += dto.getRatio();
		}
		if (total != 100) {
			showValidationErrorDialog();
			return;
		}

		appraisalGoalService.delete(appraisal.getSeq());
		for (AgDto dto : selecteds) {
			appraisalGoalService.add(dto);
		}
		sessionManager.addGlobalMessageInfo("Goal Section has been updated.",
				null);
		clearAll();
	}

	public void applyQuestions() throws IOException {
		List<AqDto> selecteds = getAqSelecteds();
		int total = 0;
		for (AqDto dto : selecteds) {
			total += dto.getRatio();
		}
		if (total != 100) {
			showValidationErrorDialog();
			return;
		}

		appraisalQuestionService.delete(appraisal.getSeq());
		for (AqDto dto : selecteds) {
			appraisalQuestionService.add(dto);
		}
		sessionManager.addGlobalMessageInfo(
				"Question Section has been updated.", null);
		clearAll();
	}

	public void applyEvaluators() throws IOException {
		List<WorkInfoDto> list = evaluators.getTarget();

		appraisalReviewerService.delete(appraisal.getSeq());
		for (WorkInfoDto dto : list) {
			appraisalReviewerService.add(new ArDto(appraisal.getSeq(), dto, new EvalGroupDto()));
		}

		sessionManager.addGlobalMessageInfo(
				"Evaluators have been updated.", null);
		clearAll();
	}

	public void clearMode() {
		editDetailMode = false;
		editCompetencyMode = false;
		editGoalMode = false;
		editQuestionMode = false;
	}

	public void clearAll() {
		clearList();
		clearMode();
		clearDialog();
	}
	public void clearList()
	{
		acList = null;
		agList = null;
		aqList = null;
//		evaluators = null;
	}
	public void clearAppraisal()
	{
		if(appraisal != null && appraisal.getSeq() > 0)
		{
			try {
				appraisal = appraisalService.findBySeq(appraisal.getSeq());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public int getActiveIndex() {
		return activeIndex;
	}

	public void setActiveIndex(int activeIndex) {
		this.activeIndex = activeIndex;
	}

	public AppraisalDto getAppraisal() {
		return appraisal;
	}

	public void setAppraisal(AppraisalDto appraisal) {
		this.appraisal = appraisal;
	}

	public boolean isEditCompetencyMode() {
		return editCompetencyMode;
	}

	public void setEditCompetencyMode(boolean editCompetencyMode) {
		this.editCompetencyMode = editCompetencyMode;
	}

	public boolean isBrowseMode() {
		return !editCompetencyMode && !editGoalMode && !editQuestionMode;
	}

	public boolean isEditDetailMode() {
		return editDetailMode;
	}

	public void setEditDetailMode(boolean editDetailMode) {
		this.editDetailMode = editDetailMode;
	}

	public List<AcDto> getAcSelecteds() {
		List<AcDto> list = new ArrayList<AcDto>();
		for (AcDto dto : getAcList()) {
			if (dto.isSelected())
				list.add(dto);
		}
		return list;
	}

	public List<AgDto> getAgSelecteds() {
		List<AgDto> list = new ArrayList<AgDto>();
		for (AgDto dto : getAgList()) {
			if (dto.isSelected())
				list.add(dto);
		}
		return list;
	}

	public List<AqDto> getAqSelecteds() {
		List<AqDto> list = new ArrayList<AqDto>();
		for (AqDto dto : getAqList()) {
			if (dto.isSelected())
				list.add(dto);
		}
		return list;
	}

	public void clearAcRatio(AcDto ac) {
		System.out.println("clear ratio");
		ac.setRatio(0);
	}

	public void clearAgRatio(AgDto ac) {
		ac.setRatio(0);
	}

	public void clearAqRatio(AqDto ac) {
		ac.setRatio(0);
	}

	public boolean isEditGoalMode() {
		return editGoalMode;
	}

	public void setEditGoalMode(boolean editGoalMode) {
		this.editGoalMode = editGoalMode;
	}

	public boolean isEditQuestionMode() {
		return editQuestionMode;
	}

	public void setEditQuestionMode(boolean editQuestionMode) {
		this.editQuestionMode = editQuestionMode;
	}

	private void showValidationErrorDialog() {
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("weightErrorDialog_w.show();");
	}
	public void changeTab()
	{
		if(activeIndex == 0)
			activeIndex = 1;
		clearMode();
	}

	public DualListModel<WorkInfoDto> getEvaluators() {
		if (evaluators == null) {
			try {
				List<WorkInfoDto> allEmployees = workInfoService
						.getAllList(new DynamicQuery());

				allEmployees.removeAll(appraisal.getEvaluators());
				evaluators = new DualListModel<WorkInfoDto>(allEmployees,
						appraisal.getEvaluators());

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return evaluators == null ? new DualListModel<WorkInfoDto>(new ArrayList<WorkInfoDto>(),
				new ArrayList<WorkInfoDto>()) : evaluators;
	}

	public void setEvaluators(DualListModel<WorkInfoDto> evaluators) {
		this.evaluators = evaluators;
	}

	public void navigate()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		appraisal = context.getApplication().evaluateExpressionGet(
				context, "#{appraisal}", AppraisalDto.class);
	}

}
