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

import npp.dto.PayTypeDto;
import npp.faces.PayTypeListModel;
import npp.spec.manager.SessionManager;
import npp.spec.service.PayTypeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Named
@SessionScoped
public class PayTypeControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 469282104365892582L;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private List<PayTypeDto> baseList = null;
	private PayTypeListModel listModel = null;

	private int first;
	private boolean addMode;
	private boolean editMode;
	private boolean deleteMode;

	private List<PayTypeDto> selecteds;
	private PayTypeDto editDto;

	@Inject
	private PayTypeService payTypeService;
	@Inject
	private SessionManager sessionManager;

	@PostConstruct
	public void init() {
		addMode = false;
		editMode = false;
		deleteMode = false;
		editDto = new PayTypeDto();
	}

	public PayTypeListModel getListModel() {
		if (baseList == null) {
			try {
				baseList = payTypeService.getAllList();
				listModel = new PayTypeListModel(baseList);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return baseList == null ? new PayTypeListModel(
				new ArrayList<PayTypeDto>()) : listModel;
	}

	public void startAdd()
	{
		editDto = new PayTypeDto();
		addMode = true;
	}
	public void startEdit(ActionEvent e)
	{
		PayTypeDto dto = (PayTypeDto)e.getComponent().getAttributes().get("payType");
		editDto = new PayTypeDto(dto);
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
			payTypeService.add(editDto);
		else if(editMode)
			payTypeService.update(editDto);
		clear();
	}

	public void clear()
	{
		baseList = null;
		editDto = new PayTypeDto();
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
			for (PayTypeDto status : selecteds) {
				payTypeService.delete(status.getSeq());
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

	public List<PayTypeDto> getBaseList() throws IOException {
		if (baseList == null)
		{
			baseList = payTypeService.getAllList();
			listModel = new PayTypeListModel(baseList);
		}
		return baseList;
	}

	public void setBaseList(List<PayTypeDto> baseList) {
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

	public List<PayTypeDto> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<PayTypeDto> selecteds) {
		this.selecteds = selecteds;
	}

	public boolean isDeleteMode() {
		return deleteMode;
	}

	public void setDeleteMode(boolean deleteMode) {
		this.deleteMode = deleteMode;
	}

}
