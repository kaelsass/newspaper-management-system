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

import npp.dto.PurposeDto;
import npp.faces.PurposeListModel;
import npp.spec.manager.SessionManager;
import npp.spec.service.PurposeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Named
@SessionScoped
public class PurposeControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 469282104365892582L;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private List<PurposeDto> baseList = null;
	private PurposeListModel listModel = null;

	private int first;
	private boolean addMode;
	private boolean editMode;
	private boolean deleteMode;

	private List<PurposeDto> selecteds;
	private PurposeDto editDto;

	@Inject
	private PurposeService purposeService;
	@Inject
	private SessionManager sessionManager;

	@PostConstruct
	public void init() {
		addMode = false;
		editMode = false;
		deleteMode = false;
		editDto = new PurposeDto();
	}

	public PurposeListModel getListModel() {
		if (baseList == null) {
			try {
				baseList = purposeService.getAllList();
				listModel = new PurposeListModel(baseList);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return baseList == null ? new PurposeListModel(
				new ArrayList<PurposeDto>()) : listModel;
	}

	public void startAdd()
	{
		editDto = new PurposeDto();
		addMode = true;
	}
	public void startEdit(ActionEvent e)
	{
		PurposeDto dto = (PurposeDto)e.getComponent().getAttributes().get("payType");
		editDto = new PurposeDto(dto);
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
			purposeService.add(editDto);
		else if(editMode)
			purposeService.update(editDto);
		clear();
	}

	public void clear()
	{
		baseList = null;
		editDto = new PurposeDto();
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
			for (PurposeDto status : selecteds) {
				purposeService.delete(status.getSeq());
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

	public List<PurposeDto> getBaseList() throws IOException {
		if (baseList == null)
		{
			baseList = purposeService.getAllList();
			listModel = new PurposeListModel(baseList);
		}
		return baseList;
	}

	public void setBaseList(List<PurposeDto> baseList) {
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

	public List<PurposeDto> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<PurposeDto> selecteds) {
		this.selecteds = selecteds;
	}

	public boolean isDeleteMode() {
		return deleteMode;
	}

	public void setDeleteMode(boolean deleteMode) {
		this.deleteMode = deleteMode;
	}

}
