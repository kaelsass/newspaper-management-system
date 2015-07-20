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

import npp.dto.SubjectDto;
import npp.faces.SubjectListModel;
import npp.spec.manager.SessionManager;
import npp.spec.service.SubjectService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Named
@SessionScoped
public class SubjectControler implements Serializable {


	/**
	 *
	 */
	private static final long serialVersionUID = -3131619932344230970L;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private List<SubjectDto> baseList = null;
	private SubjectListModel listModel = null;

	private int first;
	private boolean addMode;
	private boolean editMode;
	private boolean deleteMode;

	private List<SubjectDto> selecteds;
	private SubjectDto editDto;

	@Inject
	private SubjectService subjectService;
	@Inject
	private SessionManager sessionManager;

	@PostConstruct
	public void init() {
		addMode = false;
		editMode = false;
		deleteMode = false;
		editDto = new SubjectDto();
	}

	public SubjectListModel getListModel() {
		if (baseList == null) {
			try {
				baseList = subjectService.getAllList();
				listModel = new SubjectListModel(baseList);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return baseList == null ? new SubjectListModel(
				new ArrayList<SubjectDto>()) : listModel;
	}

	public void startAdd()
	{
		editDto = new SubjectDto();
		addMode = true;
	}
	public void startEdit(ActionEvent e)
	{
		SubjectDto dto = (SubjectDto)e.getComponent().getAttributes().get("subject");
		editDto = new SubjectDto(dto);
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
			subjectService.add(editDto);
		else if(editMode)
			subjectService.update(editDto);
		clear();
	}

	public void clear()
	{
		baseList = null;
		editDto = new SubjectDto();
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
			for (SubjectDto status : selecteds) {
				subjectService.delete(status.getSeq());
			}
			clear();
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

	public List<SubjectDto> getBaseList() throws IOException {
		if (baseList == null)
		{
			baseList = subjectService.getAllList();
			listModel = new SubjectListModel(baseList);
		}
		return baseList;
	}

	public void setBaseList(List<SubjectDto> baseList) {
		this.baseList = baseList;
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

	public List<SubjectDto> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<SubjectDto> selecteds) {
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
	}
	public void nameValidate(FacesContext context, UIComponent component, Object value)
	{
		String name = (String) value;
		if(editDto.getName().equals(value))
			return;
		for(SubjectDto dto : baseList)
		{
			if(dto.getName().equals(name))
			{
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Subject has existed.", null));
			}
		}
	}

	public SubjectDto getEditDto() {
		return editDto;
	}

	public void setEditDto(SubjectDto editDto) {
		this.editDto = editDto;
	}

}
