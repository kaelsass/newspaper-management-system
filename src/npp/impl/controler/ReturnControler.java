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
import npp.condition.ReturnCondition;
import npp.condition.WorkInfoCondition;
import npp.dto.IssueDto;
import npp.dto.ReturnDto;
import npp.dto.WorkInfoDto;
import npp.faces.ReturnListModel;
import npp.spec.manager.SessionManager;
import npp.spec.service.IssueService;
import npp.spec.service.ReturnService;
import npp.spec.service.WorkInfoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Named
@SessionScoped
public class ReturnControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6156728038843400463L;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private List<ReturnDto> baseList = null;
	private ReturnListModel listModel = null;
	private List<ReturnDto> allList = null;
	private List<IssueDto> issueList = null;


	private boolean editMode;
	private boolean deleteMode;


	private List<ReturnDto> selecteds;
	private ReturnDto editDto;
	private ReturnCondition condition;

	@Inject
	private ReturnService returnService;
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
		editDto = new ReturnDto();
		condition = new ReturnCondition();
		try {
			allList = returnService.getAllList(condition.genQuery());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ReturnListModel getListModel() {
		if (baseList == null) {
			try {
				baseList = returnService.getAllList(condition.genQuery());
				listModel = new ReturnListModel(baseList);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return baseList == null ? new ReturnListModel(
				new ArrayList<ReturnDto>()) : listModel;
	}

	public void startAdd()
	{
		editDto = new ReturnDto();
	}
	public void startEdit(ActionEvent e)
	{
		ReturnDto selected = (ReturnDto)e.getComponent().getAttributes().get("return");
		editDto = new ReturnDto(selected);
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
			returnService.update(editDto);
		else
			returnService.add(editDto);
		clear();
	}

	public void clear()
	{
		baseList = null;
		editDto = new ReturnDto();
		selecteds = null;
		editMode = false;
		deleteMode = false;
		allList = null;
	}
	public void delete() throws IOException {
		try {
			for (ReturnDto status : selecteds) {
				returnService.delete(status.getSeq());
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

	public List<ReturnDto> getBaseList() throws IOException {
		if (baseList == null)
		{
			baseList = returnService.getAllList(condition.genQuery());
			listModel = new ReturnListModel(baseList);
		}
		return baseList;
	}

	public void setBaseList(List<ReturnDto> baseList) {
		this.baseList = baseList;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public List<ReturnDto> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<ReturnDto> selecteds) {
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

	public ReturnCondition getCondition() {
		return condition;
	}

	public void setCondition(ReturnCondition condition) {
		this.condition = condition;
	}

	public ReturnDto getEditDto() {
		return editDto;
	}

	public void setEditDto(ReturnDto editDto) {
		this.editDto = editDto;
	}

	public List<ReturnDto> getAllList() throws IOException {
		if(allList == null)
		{
			ReturnCondition sc = new ReturnCondition();
			allList = returnService.getAllList(sc.genQuery());
		}
		return allList;
	}

	public void setAllList(List<ReturnDto> allList) {
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
}
