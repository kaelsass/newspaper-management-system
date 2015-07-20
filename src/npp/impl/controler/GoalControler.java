package npp.impl.controler;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import npp.condition.DynamicQuery;
import npp.condition.GoalCondition;
import npp.condition.Parameter;
import npp.dto.GoalDto;
import npp.dto.WorkInfoDto;
import npp.faces.GoalListModel;
import npp.spec.manager.SessionManager;
import npp.spec.service.GoalService;
import npp.spec.service.WorkInfoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@SessionScoped
public class GoalControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 469282104365892582L;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private List<GoalDto> baseList = null;
	private GoalListModel listModel = null;

	private int first;
	private boolean addMode;
	private boolean editMode;
	private boolean deleteMode;

	private List<GoalDto> selecteds;
	private GoalDto editDto;
	private GoalCondition condition;

	@Inject
	private GoalService goalService;
	@Inject
	private WorkInfoService workInfoService;
	@Inject
	private SessionManager sessionManager;

	@PostConstruct
	public void init() {
		addMode = false;
		editMode = false;
		deleteMode = false;
		editDto = new GoalDto();
		condition = new GoalCondition();
	}

	public GoalListModel getListModel() {
		listModel = new GoalListModel(getBaseList());
		return listModel;
	}

	public void startAdd() {
		editDto = new GoalDto();
		addMode = true;
	}

	public void startEdit(ActionEvent e) {
		GoalDto selectedStatus = (GoalDto) e.getComponent().getAttributes()
				.get("goal");
		editDto = new GoalDto(selectedStatus);
		addMode = false;
		editMode = true;
	}

	public void startDelete() {
		deleteMode = true;
	}

	public void endDelete() {
		deleteMode = false;
	}

	public void apply() throws IOException {
		if (addMode)
			goalService.add(editDto);
		else if (editMode)
			goalService.update(editDto);
		clear();
	}
	public void search() throws IOException
	{
		condition.getEmployeeIDs().clear();
		String ename = condition.getEmployeeName();
		if(ename != null && !ename.equals(""))
		{
			DynamicQuery query = new DynamicQuery();
			query.addParameter(new Parameter("lower(name)", "like", "%" + ename + "%"));
			List<WorkInfoDto> list = workInfoService.getAllList(query);
			for(WorkInfoDto dto : list)
			{
				condition.getEmployeeIDs().add(dto.getId());
			}
		}
		clear();
	}

	public void clear() {
		baseList = null;
		editDto = new GoalDto();
		selecteds = null;
		addMode = false;
		editMode = false;
		deleteMode = false;
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public void delete() throws IOException {
		try {
			for (GoalDto status : selecteds) {
				goalService.delete(status.getSeq());
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

	public List<GoalDto> getBaseList() {
		if (baseList == null) {
			try {
				baseList = goalService.getAllList(condition.genQuery());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return baseList == null ? new ArrayList<GoalDto>() : baseList;
	}

	public void setBaseList(List<GoalDto> baseList) {
		this.baseList = baseList;
	}

	public GoalDto getEditStatus() {
		return editDto;
	}

	public void setEditStatus(GoalDto editStatus) {
		this.editDto = editStatus;
	}

	public boolean isAddMode() {
		return addMode;
	}

	public void setAddMode(boolean addMode) {
		this.addMode = addMode;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public boolean isBrowseMode() {
		return !addMode && !editMode;
	}

	public List<GoalDto> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<GoalDto> selecteds) {
		this.selecteds = selecteds;
	}

	public boolean isDeleteMode() {
		return deleteMode;
	}

	public void setDeleteMode(boolean deleteMode) {
		this.deleteMode = deleteMode;
	}

	public void nameValidate(FacesContext context, UIComponent component,
			Object value) {
		String name = (String) value;
		for (GoalDto dto : getBaseList()) {
			if (dto.getName().equals(name)) {
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_FATAL,
						"Employment Status has existed.", null));
			}
		}
	}

	public GoalDto getEditDto() {
		return editDto;
	}

	public void setEditDto(GoalDto editDto) {
		this.editDto = editDto;
	}

	public GoalCondition getCondition() {
		return condition;
	}

	public void setCondition(GoalCondition condition) {
		this.condition = condition;
	}
}
