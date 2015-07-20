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
import npp.dto.OccupationDto;
import npp.faces.OccupationListModel;
import npp.spec.manager.SessionManager;
import npp.spec.service.OccupationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Named
@SessionScoped
public class OccupationControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 469282104365892582L;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private List<OccupationDto> baseList = null;
	private OccupationListModel listModel = null;

	private int first;
	private boolean addMode;
	private boolean editMode;
	private boolean deleteMode;

	private List<OccupationDto> selecteds;
	private OccupationDto editDto;

	@Inject
	private OccupationService occupationService;
	@Inject
	private SessionManager sessionManager;

	@PostConstruct
	public void init() {
		addMode = false;
		editMode = false;
		deleteMode = false;
		editDto = new OccupationDto();
	}

	public OccupationListModel getListModel() {
		if (baseList == null) {
			try {
				baseList = occupationService.getAllList(new DynamicQuery());
				listModel = new OccupationListModel(baseList);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return baseList == null ? new OccupationListModel(
				new ArrayList<OccupationDto>()) : listModel;
	}

	public void startAdd()
	{
		editDto = new OccupationDto();
		addMode = true;
	}
	public void startEdit(ActionEvent e)
	{
		OccupationDto selectedStatus = (OccupationDto)e.getComponent().getAttributes().get("occupation");
		editDto = new OccupationDto(selectedStatus);
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
			occupationService.add(editDto);
		else if(editMode)
			occupationService.update(editDto);
		clear();
	}

	public void clear()
	{
		baseList = null;
		editDto = new OccupationDto();
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
			for (OccupationDto status : selecteds) {
				occupationService.delete(status.getSeq());
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

	public List<OccupationDto> getBaseList() {
		if (baseList == null)
		{
			try {
				baseList = occupationService.getAllList(new DynamicQuery());
				listModel = new OccupationListModel(baseList);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return baseList;
	}

	public void setBaseList(List<OccupationDto> baseList) {
		this.baseList = baseList;
	}

	public OccupationDto getEditStatus() {
		return editDto;
	}

	public void setEditStatus(OccupationDto editStatus) {
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

	public boolean isBrowseMode()
	{
		return !addMode && !editMode;
	}

	public List<OccupationDto> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<OccupationDto> selecteds) {
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
		for (OccupationDto dto : getBaseList()) {
			if (dto.getName().equals(name)) {
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_FATAL,
						"Employment Status has existed.", null));
			}
		}
	}

	public OccupationDto getEditDto() {
		return editDto;
	}

	public void setEditDto(OccupationDto editDto) {
		this.editDto = editDto;
	}
}
