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
import npp.condition.NewspaperCondition;
import npp.condition.PdateCondition;
import npp.dto.NewspaperDto;
import npp.dto.PublicationDateDto;
import npp.faces.NewspaperListModel;
import npp.spec.manager.SessionManager;
import npp.spec.service.NewspaperService;
import npp.spec.service.PdateService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Named
@SessionScoped
public class NewspaperControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -7002349436543401334L;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private List<NewspaperDto> baseList = null;
	private NewspaperListModel listModel = null;
	private List<NewspaperDto> allList = null;

	private List<PublicationDateDto> pdateList;

	private int first;
	private boolean editMode;
	private boolean deleteMode;

	private List<NewspaperDto> selecteds;
	private NewspaperDto editDto;
	private NewspaperCondition condition;

	@Inject
	private NewspaperService newspaperService;
	@Inject
	private SessionManager sessionManager;
	@Inject
	private PdateService pdateService;

	@PostConstruct
	public void init() {
		editMode = false;
		deleteMode = false;
		editDto = new NewspaperDto();
		condition = new NewspaperCondition();
		try {
			allList = newspaperService.getAllList(condition.genQuery());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public NewspaperListModel getListModel() {
		if (baseList == null) {
			try {
				baseList = newspaperService.getAllList(condition.genQuery());
				listModel = new NewspaperListModel(baseList);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return baseList == null ? new NewspaperListModel(
				new ArrayList<NewspaperDto>()) : listModel;
	}

	public void startAdd()
	{
		editDto = new NewspaperDto();
		editMode = false;
	}
	public void startEdit(ActionEvent e)
	{
		NewspaperDto selected = (NewspaperDto)e.getComponent().getAttributes().get("newspaper");
		editDto = new NewspaperDto(selected);
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
		if(editDto.getPdate().getType() == 0 || editDto.getPdate().getDay() == 0)
		{
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Publication Date is required", null));

		}
		if(editMode)
			newspaperService.update(editDto);
		else
			newspaperService.add(editDto);
		clear();
	}

	public void clear()
	{
		baseList = null;
		editDto = new NewspaperDto();
		selecteds = null;
		editMode = false;
		deleteMode = false;
		allList = null;
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public void delete() throws IOException {
		try {
			for (NewspaperDto status : selecteds) {
				newspaperService.delete(status.getSeq());
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

	public List<NewspaperDto> getBaseList() throws IOException {
		if (baseList == null)
		{
			baseList = newspaperService.getAllList(condition.genQuery());
			listModel = new NewspaperListModel(baseList);
		}
		return baseList;
	}

	public void setBaseList(List<NewspaperDto> baseList) {
		this.baseList = baseList;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}
	public List<NewspaperDto> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<NewspaperDto> selecteds) {
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

	public NewspaperCondition getCondition() {
		return condition;
	}

	public void setCondition(NewspaperCondition condition) {
		this.condition = condition;
	}

	public List<String> completeName(String query)
	{
		ArrayList<String> list = new ArrayList<String>();
		for(NewspaperDto dto : getAllList())
		{
			if(dto.getName().toLowerCase().contains(query.toLowerCase()))
				list.add(dto.getName());
		}
		return list;
	}
	public List<String> completeISSN(String query)
	{
		ArrayList<String> list = new ArrayList<String>();
		for(NewspaperDto dto : getAllList())
		{
			if(dto.getIssn().toLowerCase().contains(query.toLowerCase()))
				list.add(dto.getIssn());
		}
		return list;
	}

	public List<NewspaperDto> getAllList() {
		if(allList == null)
		{
			try {
				allList = newspaperService.getAllList(new DynamicQuery());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return allList == null ? new ArrayList<NewspaperDto>() : allList;
	}

	public void setAllList(List<NewspaperDto> allList) {
		this.allList = allList;
	}

	public List<PublicationDateDto> getPdateList() throws IOException {
		int type = editDto.getPdate().getType();
		System.out.println("type : " + type);
		if(type == 0 || type == 3)
		{
			pdateList = null;
			return new ArrayList<PublicationDateDto>();
		}
		PdateCondition pc = new PdateCondition();
		pc.setType(type);
		pdateList = pdateService.getAllList(pc.genQuery());
		return pdateList;
	}

	public void setPdateList(List<PublicationDateDto> pdateList) {
		this.pdateList = pdateList;
	}

	public NewspaperDto getEditDto() {
		return editDto;
	}

	public void setEditDto(NewspaperDto editDto) {
		this.editDto = editDto;
	}
	public void nameValidate(FacesContext context, UIComponent component,
			Object value) {
		boolean find = false;
		String name = (String) value;
		for (NewspaperDto dto : getAllList()) {
			if (dto.getName().equals(name)) {
				find = true;
			}
		}
		if (!find) {
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Newspaper doesn't exist.", null));
		}
	}
	public void seqValidate(FacesContext context, UIComponent component,
			Object value) {
		int seq = (Integer) value;

		if (seq <= 0) {
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Newspaper is required", null));
		}
	}
	public void ptypeValidate(FacesContext context, UIComponent component,
			Object value) {
		int seq = (Integer) value;

		if (seq <= 0) {
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Publication Cycle is required", null));
		}
	}
//	public void changePtype()
//	{
//		if(editDto.getPdate().getType() == 0)
//		{
//
//		}
//	}
}
