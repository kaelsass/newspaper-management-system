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

import npp.dto.ReminderDto;
import npp.faces.ReminderListModel;
import npp.spec.manager.SessionManager;
import npp.spec.service.ReminderService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@SessionScoped
public class ReminderControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 469282104365892582L;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private List<ReminderDto> baseList = null;
	private ReminderListModel listModel = null;

	private int first;
	private boolean addMode;
	private boolean editMode;
	private boolean deleteMode;

	private List<ReminderDto> selecteds;
	private ReminderDto editDto;

	@Inject
	private ReminderService reminderService;
	@Inject
	private SessionManager sessionManager;

	@PostConstruct
	public void init() {
		addMode = false;
		editMode = false;
		deleteMode = false;
		editDto = new ReminderDto();
	}

	public ReminderListModel getListModel() {
		if (baseList == null) {
			try {
				baseList = reminderService.getAllList();
				listModel = new ReminderListModel(baseList);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return baseList == null ? new ReminderListModel(
				new ArrayList<ReminderDto>()) : listModel;
	}

	public void startAdd() {
		editDto = new ReminderDto();
		addMode = true;
	}

	public void startEdit(ActionEvent e) {
		ReminderDto selectedStatus = (ReminderDto) e.getComponent().getAttributes()
				.get("status");
		editDto = new ReminderDto(selectedStatus);
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
			reminderService.add(editDto);
		else if (editMode)
			reminderService.update(editDto);
		clear();
	}

	public void clear() {
		baseList = null;
		editDto = new ReminderDto();
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
			for (ReminderDto status : selecteds) {
				reminderService.delete(status.getSeq());
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

	public List<ReminderDto> getBaseList() {
		if (baseList == null) {
			try {
				baseList = reminderService.getAllList();
				listModel = new ReminderListModel(baseList);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("size:" + baseList.size());
		return baseList;
	}

	public void setBaseList(List<ReminderDto> baseList) {
		this.baseList = baseList;
	}

	public ReminderDto getEditStatus() {
		return editDto;
	}

	public void setEditStatus(ReminderDto editStatus) {
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

	public List<ReminderDto> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<ReminderDto> selecteds) {
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
		for (ReminderDto dto : getBaseList()) {
			if (dto.getName().equals(name)) {
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_FATAL,
						"Employment Status has existed.", null));
			}
		}
	}

	public ReminderDto getEditDto() {
		return editDto;
	}

	public void setEditDto(ReminderDto editDto) {
		this.editDto = editDto;
	}
}
