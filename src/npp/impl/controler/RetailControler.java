package npp.impl.controler;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import npp.condition.DynamicQuery;
import npp.condition.IssueCondition;
import npp.condition.Parameter;
import npp.condition.RetailCondition;
import npp.condition.WorkInfoCondition;
import npp.dto.IssueDto;
import npp.dto.RetailDto;
import npp.dto.WorkInfoDto;
import npp.faces.RetailListModel;
import npp.spec.manager.SessionManager;
import npp.spec.service.IssueService;
import npp.spec.service.RetailService;
import npp.spec.service.WorkInfoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Named
@SessionScoped
public class RetailControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6156728038843400463L;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private List<RetailDto> baseList = null;
	private RetailListModel listModel = null;
	private List<RetailDto> allList = null;
	private List<IssueDto> issueList = null;


	private boolean editMode;
	private boolean deleteMode;
	private boolean addDetailMode;



	private List<RetailDto> selecteds;
	private RetailDto editDto;
	private RetailCondition condition;

	@Inject
	private RetailService retailService;
	@Inject
	private SessionManager sessionManager;

	@Inject
	private WorkInfoService workInfoService;
	@Inject
	private IssueService issueService;

	@PostConstruct
	public void init() {
		editMode = false;
		deleteMode = false;
		addDetailMode = false;
		editDto = new RetailDto();
		condition = new RetailCondition();
		try {
			allList = retailService.getAllList(condition.genQuery());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public RetailListModel getListModel() {
		if (baseList == null) {
			try {
				baseList = retailService.getAllList(condition.genQuery());
				listModel = new RetailListModel(baseList);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return baseList == null ? new RetailListModel(
				new ArrayList<RetailDto>()) : listModel;
	}

	public void startAdd()
	{
		editDto = new RetailDto();
	}
	public void startEdit(ActionEvent e)
	{
		RetailDto selected = (RetailDto)e.getComponent().getAttributes().get("retail");
		editDto = new RetailDto(selected);
		editMode = true;
	}
	public void startDelete()
	{
		deleteMode = true;
	}
	public void endDelete()
	{
		deleteMode = false;
	}
	public void apply() throws IOException
	{
		WorkInfoDto employee = workInfoService.findByName(editDto.getEmployee().getName());
		editDto.setEmployee(employee);
		if(editMode)
			retailService.update(editDto);
		else
			retailService.add(editDto);
		clear();
	}

	public void clear()
	{
		baseList = null;
		editDto = new RetailDto();
		selecteds = null;
		editMode = false;
		deleteMode = false;
		addDetailMode = false;
		allList = null;
	}
	public void delete() throws IOException {
		try {
			for (RetailDto status : selecteds) {
				retailService.delete(status.getSeq());
			}
			clear();
		} catch (IOException e) {
			logger.error("Article Date delete error.", e);
			sessionManager.addGlobalMessageFatal("Article Data delete error.",
					null);
			throw e;
		}
	}

	public int getStatusListLength() {
		if (listModel == null)
			return 0;
		return listModel.getRowCount();
	}

	public List<RetailDto> getBaseList() throws IOException {
		if (baseList == null)
		{
			baseList = retailService.getAllList(condition.genQuery());
			listModel = new RetailListModel(baseList);
		}
		return baseList;
	}

	public void setBaseList(List<RetailDto> baseList) {
		this.baseList = baseList;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public List<RetailDto> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<RetailDto> selecteds) {
		this.selecteds = selecteds;
	}

	public boolean isDeleteMode() {
		return deleteMode;
	}

	public void setDeleteMode(boolean deleteMode) {
		this.deleteMode = deleteMode;
	}
	public void search() throws IOException
	{
		clear();

		IssueCondition ic = new IssueCondition();
		ic.setNewspaperSeqs(condition.getNewspaperSeqs());
		ic.setFrom(condition.getDateFrom());
		ic.setTo(condition.getDateTo());
		List<IssueDto> list = issueService.getAllList(ic.genQuery());
		System.out.println("list.size:" + list.size());
		for(IssueDto dto : list)
		{
			condition.getIssues().add(dto);
		}

		WorkInfoCondition wc = new WorkInfoCondition();
		wc.setName(condition.getEmployeeName());
		List<WorkInfoDto> employees = workInfoService.getAllList(wc.genQuery());
		for(WorkInfoDto dto : employees)
		{
			condition.getEmployees().add(dto);
		}
	}

	public RetailCondition getCondition() {
		return condition;
	}

	public void setCondition(RetailCondition condition) {
		this.condition = condition;
	}

	public RetailDto getEditDto() {
		return editDto;
	}

	public void setEditDto(RetailDto editDto) {
		this.editDto = editDto;
	}

	public List<RetailDto> getAllList() throws IOException {
		if(allList == null)
		{
			RetailCondition sc = new RetailCondition();
			allList = retailService.getAllList(sc.genQuery());
		}
		return allList;
	}

	public void setAllList(List<RetailDto> allList) {
		this.allList = allList;
	}

	public List<IssueDto> getIssueList() {
		if(issueList == null)
		{
			DynamicQuery dq = new DynamicQuery();
			dq.addParameter(new Parameter("newspaper_seq", "=", editDto.getIssue().getNewspaper().getSeq()));
			try {
				issueList = issueService.getAllList(dq);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				issueList = null;
			}
		}
		return issueList == null ? new ArrayList<IssueDto>() : issueList;
	}

	public void setIssueList(List<IssueDto> issueList) {
		this.issueList = issueList;
	}
	public void clearIssue()
	{
		issueList = null;
	}
	public void startAddDetail()
	{
		addDetailMode = true;
	}

	public boolean isAddDetailMode() {
		return addDetailMode;
	}

	public void setAddDetailMode(boolean addDetailMode) {
		this.addDetailMode = addDetailMode;
	}
}
