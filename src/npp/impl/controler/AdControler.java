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

import npp.condition.AdCondition;
import npp.condition.WorkInfoCondition;
import npp.dto.AdDto;
import npp.dto.WorkInfoDto;
import npp.faces.AdListModel;
import npp.spec.manager.SessionManager;
import npp.spec.service.AdService;
import npp.spec.service.WorkInfoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Named
@SessionScoped
public class AdControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6156728038843400463L;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private List<AdDto> baseList = null;
	private AdListModel listModel = null;
	private List<AdDto> allList = null;

	private boolean editMode;
	private boolean deleteMode;


	private List<AdDto> selecteds;
	private AdDto editDto;
	private AdCondition condition;

	@Inject
	private AdService adService;
	@Inject
	private SessionManager sessionManager;

	@Inject
	private WorkInfoService workInfoService;

	@PostConstruct
	public void init(){
		editMode = false;
		deleteMode = false;
		editDto = new AdDto();
		condition = new AdCondition();
		try {
			allList = adService.getAllList(condition.genQuery());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public AdListModel getListModel() {
		if (baseList == null) {
			try {
				baseList = adService.getAllList(condition.genQuery());
				listModel = new AdListModel(baseList);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return baseList == null ? new AdListModel(
				new ArrayList<AdDto>()) : listModel;
	}

	public void startAdd()
	{
		editDto = new AdDto();
	}
	public void startEdit(ActionEvent e)
	{
		AdDto selected = (AdDto)e.getComponent().getAttributes().get("ad");
		editDto = new AdDto(selected);
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
			adService.update(editDto);
		else
			adService.add(editDto);
		clear();
	}

	public void clear()
	{
		baseList = null;
		editDto = new AdDto();
		selecteds = null;
		editMode = false;
		deleteMode = false;
		allList = null;
	}
	public void delete() throws IOException {
		try {
			for (AdDto dto : selecteds) {
				adService.delete(dto.getID());
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

	public List<AdDto> getBaseList() throws IOException {
		if (baseList == null)
		{
			baseList = adService.getAllList(condition.genQuery());
			listModel = new AdListModel(baseList);
		}
		return baseList;
	}

	public void setBaseList(List<AdDto> baseList) {
		this.baseList = baseList;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public List<AdDto> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<AdDto> selecteds) {
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

		WorkInfoCondition wc = new WorkInfoCondition();
		wc.setName(condition.getEmployeeName());
		List<WorkInfoDto> employees = workInfoService.getAllList(wc.genQuery());
		for(WorkInfoDto dto : employees)
		{
			condition.getEmployees().add(dto);
		}
	}

	public AdCondition getCondition() {
		return condition;
	}

	public void setCondition(AdCondition condition) {
		this.condition = condition;
	}

	public AdDto getEditDto() {
		return editDto;
	}

	public void setEditDto(AdDto editDto) {
		this.editDto = editDto;
	}

	public List<AdDto> getAllList() throws IOException {
		if(allList == null)
		{
			AdCondition sc = new AdCondition();
			allList = adService.getAllList(sc.genQuery());
		}
		return allList;
	}

	public void setAllList(List<AdDto> allList) {
		this.allList = allList;
	}

	public List<String> completeID(String query) throws IOException
	{
		ArrayList<String> list = new ArrayList<String>();
		for(AdDto dto : getAllList())
		{
			if(dto.getID().toLowerCase().contains(query.toLowerCase()))
				list.add(dto.getID());
		}
		return list;
	}

	public List<String> completeWorkUnit(String query) throws IOException
	{
		ArrayList<String> list = new ArrayList<String>();
		for(AdDto dto : getAllList())
		{
			if(dto.getWorkUnit().toLowerCase().contains(query.toLowerCase()))
				list.add(dto.getWorkUnit());
		}
		return list;
	}
	public List<String> completeAddress(String query) throws IOException
	{
		ArrayList<String> list = new ArrayList<String>();
		for(AdDto dto : getAllList())
		{
			if(dto.getAddress().toLowerCase().contains(query.toLowerCase()))
				list.add(dto.getAddress());
		}
		return list;
	}
}
