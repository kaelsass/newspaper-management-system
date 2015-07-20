package npp.impl.controler;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import npp.condition.QuestionCondition;
import npp.dto.QuestionDto;
import npp.dto.QuestionItemDto;
import npp.faces.QuestionListModel;
import npp.spec.manager.SessionManager;
import npp.spec.service.NquestionService;
import npp.spec.service.QuestionItemService;
import npp.utils.DialogUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@SessionScoped
public class NquestionControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -3131619932344230970L;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private List<QuestionDto> baseList = null;
	private QuestionListModel listModel = null;
	private List<QuestionDto> allList = null;


	private List<String> items;

	private boolean addMode;
	private boolean editMode;

	private List<QuestionDto> selecteds;
	private List<String> selectedItems;
	private QuestionDto editDto;
	private String editItem;

	private QuestionCondition condition;

	@Inject
	private NquestionService nquestionService;
	@Inject
	private QuestionItemService questionItemService;
	@Inject
	private SessionManager sessionManager;

	@PostConstruct
	public void init(){
		addMode = false;
		editMode = false;
		editDto = new QuestionDto();
		condition = new QuestionCondition();
		editItem = "";
	}

	public QuestionListModel getListModel() {
		if (baseList == null) {
			try {
				baseList = nquestionService.getAllList(condition.genQuery());
				listModel = new QuestionListModel(baseList);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return baseList == null ? new QuestionListModel(
				new ArrayList<QuestionDto>()) : listModel;
	}

	public void startAdd() {
		editDto = new QuestionDto();
		addMode = true;
		editMode = false;
	}

	public void startEdit(ActionEvent e) {
		QuestionDto dto = (QuestionDto) e.getComponent().getAttributes()
				.get("question");
		editDto = new QuestionDto(dto);
		editDto.setModDate(new Date());
		editMode = true;
		addMode = false;
	}


	public void apply() throws IOException {
		if (addMode)
			nquestionService.add(editDto);
		else if (editMode)
			nquestionService.update(editDto);
		DialogUtil.hideDialog("editDialog_w");
		clear();
	}

	public void applyItem() throws IOException {

		editDto.getItems().add(editItem);
		editItem = "";
		DialogUtil.hideDialog("addItem_w");
	}

	public void clear() {
		baseList = null;
		allList = null;
		selecteds = null;
		selectedItems = null;
		addMode = false;
		editMode = false;
	}

	public void delete() throws IOException {
		try {
			for (QuestionDto status : selecteds) {
				nquestionService.delete(status.getSeq());
			}
			clear();
		} catch (IOException e) {
			logger.error("Issue Data delete error.", e);
			sessionManager.addGlobalMessageFatal("Issue Data delete error.",
					null);
			throw e;
		}
	}

	public void deleteItem() throws IOException {
		editDto.getItems().removeAll(selectedItems);
		questionItemService.delete(editDto.getSeq());
		for (String item : editDto.getItems()) {
			questionItemService
					.add(new QuestionItemDto(editDto.getSeq(), item));
		}
		DialogUtil.hideDialog("deleteItemConfirm_w");
		clear();
	}

	public int getStatusListLength() {
		if (listModel == null)
			return 0;
		return listModel.getRowCount();
	}

	public List<QuestionDto> getBaseList() throws IOException {
		if (baseList == null) {
			baseList = nquestionService.getAllList(condition.genQuery());
			listModel = new QuestionListModel(baseList);
		}
		return baseList;
	}

	public void setBaseList(List<QuestionDto> baseList) {
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

	public boolean isBrowseMode() {
		return !addMode && !editMode;
	}

	public List<QuestionDto> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<QuestionDto> selecteds) {
		this.selecteds = selecteds;
	}
	public void search() throws IOException {
		clear();
	}

	public void nameValidate(FacesContext context, UIComponent component,
			Object value) {
		String name = (String) value;
		if (editDto.getName().equals(value))
			return;
		for (QuestionDto dto : baseList) {
			if (dto.getName().equals(name)) {
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Subject has existed.",
						null));
			}
		}
	}

	public QuestionDto getEditDto() {
		return editDto;
	}

	public void setEditDto(QuestionDto editDto) {
		this.editDto = editDto;
	}

	public List<String> getItems() {
		return items;
	}

	public void setItems(List<String> items) {
		this.items = items;
	}

	public String getEditItem() {
		return editItem;
	}

	public void setEditItem(String editItem) {
		this.editItem = editItem;
	}

	public List<String> getSelectedItems() {
		return selectedItems;
	}

	public void setSelectedItems(List<String> selectedItems) {
		this.selectedItems = selectedItems;
	}

	public QuestionCondition getCondition() {
		return condition;
	}

	public void setCondition(QuestionCondition condition) {
		this.condition = condition;
	}

	public List<String> completeName(String query)
	{
		List<String> list = new ArrayList<String>();
		for(QuestionDto dto : getAllList())
		{
			if(dto.getName().toLowerCase().contains(query.toLowerCase()))
				list.add(dto.getName());
		}
		return list;
	}

	public List<QuestionDto> getAllList() {
		if(allList == null)
		{
			try {
				allList = nquestionService.getAllList(new DynamicQuery());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return allList == null ? new ArrayList<QuestionDto>() : allList;
	}

	public void setAllList(List<QuestionDto> allList) {
		this.allList = allList;
	}

	public void itemValidate(FacesContext context, UIComponent component, Object value)
	{
		String name = (String) value;
		if(editDto.getItems().contains(name))
		{
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "This option has existed.", null));
		}
	}

}
