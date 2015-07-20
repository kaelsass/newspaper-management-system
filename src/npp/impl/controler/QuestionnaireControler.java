package npp.impl.controler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import npp.condition.DynamicQuery;
import npp.condition.QuestionnaireCondition;
import npp.condition.RecordCondition;
import npp.dto.QqDto;
import npp.dto.QuestionDto;
import npp.dto.QuestionSummaryDto;
import npp.dto.QuestionnaireDto;
import npp.dto.RecordDto;
import npp.dto.RecordSummaryDto;
import npp.faces.QuestionListModel;
import npp.faces.QuestionnaireListModel;
import npp.faces.RecordListModel;
import npp.spec.manager.SessionManager;
import npp.spec.service.NquestionService;
import npp.spec.service.QuestionnaireQuestionService;
import npp.spec.service.QuestionnaireService;
import npp.spec.service.RecordService;
import npp.utils.DialogUtil;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@SessionScoped
public class QuestionnaireControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -3131619932344230970L;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private List<QuestionnaireDto> allList = null;
	private List<QuestionnaireDto> baseList = null;
	private QuestionnaireListModel listModel = null;

	private List<QuestionSummaryDto> summaryList = null;

	private List<RecordDto> recordList = null;
	private RecordListModel recordListModel = null;

	private List<QuestionDto> availableQuestionList = null;
	private QuestionListModel availableQuestionListModel = null;

	private List<QuestionDto> selectedQuestions = null;
	private List<QuestionDto> addedQuestions = null;
	private List<QuestionDto> filteredQuestions = null;

	private boolean addMode;
	private boolean viewMode;
	private boolean deleteMode;

	private List<QuestionnaireDto> selecteds;
	private List<RecordDto> selectedRecords;
	private QuestionnaireDto editDto;
	private RecordDto editRecord;
	private QuestionDto editQuestion;
	private QuestionnaireCondition condition;
	private RecordCondition recordCondition;

	@Inject
	private QuestionnaireService questionnaireService;
	@Inject
	private QuestionnaireQuestionService qqService;
	@Inject
	private NquestionService questionService;

	@Inject
	private RecordService recordService;
	@Inject
	private SessionManager sessionManager;

	private String outcome;
	private int activeIndex;

	private Date start;
	private Date end;

	private int questionnaireSeq;
	private String shareLink;

	@PostConstruct
	public void init() {
		addMode = false;
		viewMode = false;
		deleteMode = false;
		editDto = new QuestionnaireDto();
		editRecord = new RecordDto();
		condition = new QuestionnaireCondition();
		recordCondition = new RecordCondition();
		outcome = "";
		activeIndex = 0;
	}
	public void loadSeq() throws IOException
	{
		editDto = questionnaireService.findBySeq(questionnaireSeq);
		startAddRecord();
	}

	public QuestionnaireListModel getListModel() {
		if (baseList == null) {
			try {
				listModel = new QuestionnaireListModel(getBaseList());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return listModel;
	}

	public void startAdd() {
		editDto = new QuestionnaireDto();
		addMode = true;
	}

	public void startAddRecord() {
		editRecord = new RecordDto();
		editRecord.setQuestionnaireSeq(editDto.getSeq());
		editRecord.setDate(new Date());
		clearEditDtoQuestions();
		editRecord.setQuestions(editDto.getQuestions());
		start = new Date();
	}

	public void startEdit(ActionEvent e) {
		QuestionnaireDto dto = (QuestionnaireDto) e.getComponent()
				.getAttributes().get("subject");
		editDto = new QuestionnaireDto(dto);
		addMode = false;
	}

	public void startDelete() {
		deleteMode = true;
	}

	public void endDelete() {
		deleteMode = false;
	}

	public void apply() throws IOException {
		if (addMode)
			questionnaireService.add(editDto);
		else
			questionnaireService.update(editDto);
		sessionManager.addGlobalMessageInfo(
				"Questionnaire Info updated successfully.", null);
		clear();
	}

	public void clear() {
		baseList = null;
		allList = null;
		selecteds = null;
		addMode = false;
		viewMode = false;
		deleteMode = false;
	}

	public void delete() throws IOException {
		try {
			for (QuestionnaireDto status : selecteds) {
				questionnaireService.delete(status.getSeq());
			}
			clear();
		} catch (IOException e) {
			logger.error("Issue Data delete error.", e);
			sessionManager.addGlobalMessageFatal("Issue Data delete error.",
					null);
			throw e;
		}
	}

	public void deleteRecord() throws IOException {
		System.out.println("deleteRecord : " + selectedRecords);
		try {
			for (RecordDto dto : selectedRecords) {
				recordService.delete(dto.getSeq());
			}
			clearRecords();
		} catch (IOException e) {
			logger.error("Issue Data delete error.", e);
			sessionManager.addGlobalMessageFatal("Issue Data delete error.",
					null);
			throw e;
		}
	}

	public int getStatusListLength() {
		if (listModel == null)
			return 0;
		return listModel.getRowCount();
	}

	public List<QuestionnaireDto> getBaseList() throws IOException {
		if (baseList == null) {
			baseList = questionnaireService.getAllList(condition.genQuery());
			listModel = new QuestionnaireListModel(baseList);
		}
		return baseList;
	}

	public void setBaseList(List<QuestionnaireDto> baseList) {
		this.baseList = baseList;
	}

	public boolean isAddMode() {
		return addMode;
	}

	public void setAddMode(boolean addMode) {
		this.addMode = addMode;
	}

	public List<QuestionnaireDto> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<QuestionnaireDto> selecteds) {
		this.selecteds = selecteds;
	}

	public boolean isDeleteMode() {
		return deleteMode;
	}

	public void setDeleteMode(boolean deleteMode) {
		this.deleteMode = deleteMode;
	}

	public void search() throws IOException {
		clear();
	}

	public void searchRecord() {
		recordCondition.setQuestionnaireSeq(editDto.getSeq());
		clearRecords();
	}

	public QuestionnaireDto getEditDto() {
		return editDto;
	}

	public void setEditDto(QuestionnaireDto editDto) {
		this.editDto = editDto;
	}

	public void navigate() {
		if (outcome == null || outcome.equals(""))
			return;
		FacesContext context = FacesContext.getCurrentInstance();
		editDto = context.getApplication().evaluateExpressionGet(context,
				"#{questionnaire}", QuestionnaireDto.class);
		if (outcome.endsWith("addQRecord.jsf"))
			startAddRecord();
		else if (outcome.endsWith("questionnaireSummary.jsf"))
			initSummaryList();
		NavigationHandler navigationHandler = context.getApplication()
				.getNavigationHandler();
		navigationHandler.handleNavigation(context, null, outcome
				+ "?faces-redirect=true");
		outcome = "";
		activeIndex = 0;
		clearRecords();
	}

	private void clearRecords() {
		summaryList = null;
		recordList = null;
		selectedRecords = null;
		viewMode = false;
	}

	public QuestionnaireCondition getCondition() {
		return condition;
	}

	public void setCondition(QuestionnaireCondition condition) {
		this.condition = condition;
	}

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	public void addQuestions() throws IOException {
		System.out.println("addedQuestions: " + addedQuestions);
		if (addedQuestions != null) {
			for (QuestionDto dto : addedQuestions) {
				editDto.getQuestions().add(new QuestionDto(dto));
			}
			applyQuestions();

		}
		clearQuestions();
		sessionManager.addGlobalMessageInfo("Questions added successfully.",
				null);
	}

	private void applyQuestions() throws IOException {
		qqService.delete(editDto.getSeq());
		List<QuestionDto> questions = editDto.getQuestions();
		for(int i = 0; i < questions.size(); i++)
		{
			QuestionDto dto = questions.get(i);
			dto.setIndex(i);
			qqService.add(new QqDto(editDto.getSeq(), dto));
		}
	}

	public void clearEditDtoQuestions() {
		if (editDto == null)
			return;
		for (QuestionDto dto : editDto.getQuestions()) {
			dto.clear();
		}
	}

	public void clearQuestions() {
		System.out.println("clearQuestions");
		availableQuestionList = null;
		addedQuestions = null;
		selectedQuestions = null;
	}

	public void deleteQuestions() throws IOException {
		System.out.println("selectedQuestions: " + selectedQuestions);
		editDto.getQuestions().removeAll(selectedQuestions);
		System.out.println("orig: " + editDto.getQuestions());
		applyQuestions();
		clearQuestions();
		sessionManager.addGlobalMessageInfo("Questions deleted successfully.",
				null);
	}

	public QuestionListModel getAvailableQuestionListModel() {
		if (availableQuestionList == null) {
			availableQuestionListModel = new QuestionListModel(
					getAvailableQuestionList());
			System.out.println("avail size: "
					+ getAvailableQuestionList().size());
			System.out.println("row Count : "
					+ availableQuestionListModel.getRowCount());
		}
		return availableQuestionListModel;
	}

	public List<QuestionDto> getAvailableQuestionList() {
		if (availableQuestionList == null) {
			try {
				availableQuestionList = questionService
						.getAllList(new DynamicQuery());
				for (QuestionDto dto : editDto.getQuestions()) {
					availableQuestionList.remove(dto);

				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("avail: " + availableQuestionList);
		System.out.println("editDto: " + editDto.getQuestions());
		return availableQuestionList == null ? new ArrayList<QuestionDto>()
				: availableQuestionList;
	}

	public void setAvailableQuestionList(List<QuestionDto> availableQuestionList) {
		this.availableQuestionList = availableQuestionList;
	}

	public List<QuestionDto> getAddedQuestions() {
		return addedQuestions;
	}

	public void setAddedQuestions(List<QuestionDto> addedQuestions) {
		this.addedQuestions = addedQuestions;
	}

	public List<QuestionDto> getSelectedQuestions() {
		return selectedQuestions;
	}

	public void setSelectedQuestions(List<QuestionDto> selectedQuestions) {
		this.selectedQuestions = selectedQuestions;
	}

	public List<QuestionDto> getFilteredQuestions() {
		return filteredQuestions;
	}

	public void setFilteredQuestions(List<QuestionDto> filteredQuestions) {
		this.filteredQuestions = filteredQuestions;
	}

	public QuestionDto getEditQuestion() {
		return editQuestion;
	}

	public void setEditQuestion(QuestionDto editQuestion) {
		this.editQuestion = editQuestion;
	}

	public void reorder() throws IOException {
		qqService.delete(editDto.getSeq());
		List<QuestionDto> questions = editDto.getQuestions();
		for (int i = 0; i < questions.size(); i++) {
			questions.get(i).setIndex(i);
			qqService.add(new QqDto(editDto.getSeq(), questions.get(i)));
		}
		editDto.setQuestionListModel(null);

	}

	public int getActiveIndex() {
		return activeIndex;
	}

	public void setActiveIndex(int activeIndex) {
		this.activeIndex = activeIndex;
	}

	public void applyRecord() throws IOException {
		end = new Date();
		int time = (int) ((end.getTime() - start.getTime()) / 1000);
		editRecord.setTime(time);
		recordService.add(editRecord);
		sessionManager.addGlobalMessageInfo(
				"Questionnaire submitted successfully.", null);
		clearRecords();
	}

	public RecordCondition getRecordCondition() {
		return recordCondition;
	}

	public void setRecordCondition(RecordCondition recordCondition) {
		this.recordCondition = recordCondition;
	}

	public RecordListModel getRecordListModel() {
		if (recordList == null) {
			recordCondition.setQuestionnaireSeq(editDto.getSeq());
			try {
				recordList = recordService.getAllList(recordCondition
						.genQuery());
				recordListModel = new RecordListModel(recordList);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return recordListModel;
	}

	public List<RecordDto> getSelectedRecords() {
		return selectedRecords;
	}

	public void setSelectedRecords(List<RecordDto> selectedRecords) {
		this.selectedRecords = selectedRecords;
	}

	public void startViewRecord(RecordDto dto) {
		editRecord = new RecordDto(dto);
		viewMode = true;
	}

	public void endViewRecord() {
		viewMode = false;
	}

	public boolean isViewMode() {
		return viewMode;
	}

	public void setViewMode(boolean viewMode) {
		this.viewMode = viewMode;
	}

	public RecordDto getEditRecord() {
		return editRecord;
	}

	public void setEditRecord(RecordDto editRecord) {
		this.editRecord = editRecord;
	}

	public List<QuestionSummaryDto> getSummaryList() {
		if (summaryList == null)
			initSummaryList();
		return summaryList;
	}

	private void initSummaryList() {
		getRecordListModel();
		summaryList = new ArrayList<QuestionSummaryDto>();
		for (QuestionDto question : editDto.getQuestions()) {
			QuestionSummaryDto dto = new QuestionSummaryDto();
			dto.setQuestion(question);
			List<RecordSummaryDto> records = new ArrayList<RecordSummaryDto>();
			for (RecordDto record : recordList) {
				RecordSummaryDto curRecord = new RecordSummaryDto();
				for (QuestionDto curQuestion : record.getQuestions()) {
					if (curQuestion.getSeq() == question.getSeq()) {
						List<String> temp = new ArrayList<String>();
						if (curQuestion.getSelectedItem() != null
								&& !curQuestion.getSelectedItem().equals(""))
							temp.add(curQuestion.getSelectedItem());
						temp.addAll(curQuestion.getSelectedItems());
						curRecord.setItems(temp);
						curRecord.setAnswer(curQuestion.getAnswer());
						break;
					}
				}
				records.add(curRecord);
			}
			dto.setRecords(records);
			summaryList.add(dto);
		}
	}

	public void applyAddQuestionnaire() throws IOException {
		questionnaireService.add(editDto);
		clear();
		DialogUtil.hideDialog("addQuestionnaire_w");
	}

	public void applyPublished(QuestionnaireDto dto) throws IOException {
		questionnaireService.update(dto);
		clear();
	}

	public StreamedContent exportFile() {
		File f = new File("test.txt");
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(f), "utf-8"));
			bw.append("\t").append("\t").append(editDto.getName())
					.append("\r\n");
			bw.append("\t").append("\t").append(editDto.getDescription())
					.append("\r\n").append("\r\n");
			for (QuestionDto dto : editDto.getQuestions()) {
				bw.append((dto.getIndex() + 1) + ". " + dto.getName()).append(
						"\r\n");
				bw.append("   ");
				if (dto.getType().getSeq() == QuestionDto.SHORT_ANSWER) {
					bw.append("Answer:").append("\r\n").append("\r\n")
							.append("\r\n");
				} else {
					for (String item : dto.getItems()) {
						bw.append("□").append(item).append("   ");
					}
				}
				bw.append("\r\n");
				bw.append("\r\n");
				bw.append("-------------------------------------------");
				bw.append("\r\n");
				bw.append("\r\n");

			}
			bw.append("Thank you for your partication!");
			bw.flush();
			bw.close();
			InputStream stream = new FileInputStream(f);
			String contentType = FacesContext.getCurrentInstance()
					.getExternalContext().getMimeType(f.getAbsolutePath());
			return new DefaultStreamedContent(stream, contentType,
					editDto.getName() + ".txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public List<String> completeName(String query)
	{
		List<String> list = new ArrayList<String>();
		for(QuestionnaireDto dto : getAllList())
		{
			if(dto.getName().toLowerCase().contains(query.toLowerCase()))
				list.add(dto.getName());
		}
		return list;
	}

	public List<QuestionnaireDto> getAllList() {
		if(allList == null)
		{
			try {
				allList = questionnaireService.getAllList(new DynamicQuery());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return allList;
	}

	public int getQuestionnaireSeq() {
		return questionnaireSeq;
	}

	public void setQuestionnaireSeq(int questionnaireSeq) {
		this.questionnaireSeq = questionnaireSeq;
	}
	public String getShareLink() {
		return shareLink;
	}
	public void setShareLink(String shareLink) {
		this.shareLink = shareLink;
	}
	public String share()
	{
		//shareLink = "addQRecord.jsf";
		System.out.println("shareLink: " + shareLink);
//		FacesContext context = FacesContext.getCurrentInstance();
//		NavigationHandler navigationHandler = context.getApplication()
//				.getNavigationHandler();
//		navigationHandler.handleNavigation(context, null, shareLink
//				+ "?faces-redirect=true");
//		shareLink = "";
		return shareLink;

	}

}
