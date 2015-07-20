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

import npp.dto.StatusDto;
import npp.faces.StatusListModel;
import npp.spec.manager.SessionManager;
import npp.spec.service.NStatusService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Named
@SessionScoped
public class NStatusControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 469282104365892582L;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private List<StatusDto> baseList = null;
	private StatusListModel listModel = null;

	private int first;
	private boolean addMode;
	private boolean editMode;
	private boolean deleteMode;

	private List<StatusDto> selecteds;
	private StatusDto editStatus;

	@Inject
	private NStatusService statusService;
	@Inject
	private SessionManager sessionManager;

	@PostConstruct
	public void init() {
		addMode = false;
		editMode = false;
		deleteMode = false;
		editStatus = new StatusDto();
	}

	public StatusListModel getListModel() {
		if (baseList == null) {
			try {
				baseList = statusService.getAllList();
				listModel = new StatusListModel(baseList);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return baseList == null ? new StatusListModel(
				new ArrayList<StatusDto>()) : listModel;
	}

	public void startAdd()
	{
		editStatus = new StatusDto();
		addMode = true;
	}
	public void startEdit(ActionEvent e)
	{
		StatusDto selectedStatus = (StatusDto)e.getComponent().getAttributes().get("status");
		editStatus = new StatusDto(selectedStatus);
		addMode = false;
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
		if(addMode)
			statusService.add(editStatus);
		else if(editMode)
			statusService.update(editStatus);
		clear();
	}

	public void clear()
	{
		baseList = null;
		editStatus = new StatusDto();
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
			for (StatusDto status : selecteds) {
				statusService.delete(status.getSeq());
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

	public List<StatusDto> getBaseList() throws IOException {
		if (baseList == null)
		{
			baseList = statusService.getAllList();
			listModel = new StatusListModel(baseList);
		}
		return baseList;
	}

	public void setBaseList(List<StatusDto> baseList) {
		this.baseList = baseList;
	}

	public StatusDto getEditStatus() {
		return editStatus;
	}

	public void setEditStatus(StatusDto editStatus) {
		this.editStatus = editStatus;
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

	public boolean isBrowseMode()
	{
		return !addMode && !editMode;
	}

	public List<StatusDto> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<StatusDto> selecteds) {
		this.selecteds = selecteds;
	}

	public boolean isDeleteMode() {
		return deleteMode;
	}

	public void setDeleteMode(boolean deleteMode) {
		this.deleteMode = deleteMode;
	}

	public void nameValidate(FacesContext context,
			UIComponent component, Object value) {
		String name = (String) value;
		if (baseList == null) {
			try {
				baseList = statusService.getAllList();
				listModel = new StatusListModel(baseList);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		for (StatusDto dto : baseList) {
			if (dto.getName().equals(name)) {
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_FATAL,
						"Employment Status has existed.", null));
			}
		}
	}
}
