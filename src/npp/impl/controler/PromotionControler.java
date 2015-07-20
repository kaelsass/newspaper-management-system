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
import npp.condition.PromotionCondition;
import npp.dto.NewspaperDto;
import npp.dto.PromotionAdminDto;
import npp.dto.PromotionDto;
import npp.dto.PromotionNewspaperDto;
import npp.dto.WorkInfoDto;
import npp.faces.PromotionListModel;
import npp.spec.manager.SessionManager;
import npp.spec.service.NewspaperService;
import npp.spec.service.PromotionAdminService;
import npp.spec.service.PromotionNewspaperService;
import npp.spec.service.PromotionService;
import npp.spec.service.WorkInfoService;

import org.primefaces.model.DualListModel;

@Named
@SessionScoped
public class PromotionControler implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 6100364088420414584L;
	private List<PromotionDto> baseList = null;
	private PromotionListModel listModel = null;

	private List<PromotionDto> allList = null;
	private DualListModel<NewspaperDto> newspapers;
	private DualListModel<WorkInfoDto> admins;

	private boolean addMode;
	private boolean editMode;
	private boolean deleteMode;

	private PromotionCondition condition;

	private List<PromotionDto> selecteds;

	private PromotionDto editDto;

	@Inject
	private WorkInfoService workInfoService;
	@Inject
	private NewspaperService newspaperService;
	@Inject
	private PromotionService promotionService;
	@Inject
	private PromotionAdminService promotionAdminService;
	@Inject
	private PromotionNewspaperService promotionNewspaperService;

	@Inject
	private SessionManager sessionManager;

	@PostConstruct
	public void init(){
		addMode = false;
		editMode = false;
		deleteMode = false;
		editDto = new PromotionDto();
		condition = new PromotionCondition();
	}

	public PromotionListModel getListModel() {
		if (baseList == null) {
			try {
				baseList = promotionService.getAllEditList(condition.genQuery());
				listModel = new PromotionListModel(baseList);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return baseList == null ? new PromotionListModel(new ArrayList<PromotionDto>())
				: listModel;
	}

	public void startAdd() {
		editDto = new PromotionDto();
		addMode = true;
		editMode = false;
	}

	public void startEdit(ActionEvent e) {
		PromotionDto dto = (PromotionDto) e.getComponent().getAttributes().get("promotion");
		editDto = new PromotionDto(dto);
		admins = null;
		newspapers = null;
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

		editDto.setAdmins(getAdmins().getTarget());
		editDto.setNewspapers(getNewspapers().getTarget());

		if (editMode)
			promotionService.update(editDto);
		else
		{
			promotionService.add(editDto);
			editDto.setSeq(promotionService.getNewInsertedSeq());
		}

		clear();
	}

	public void clear() {
		baseList = null;
		allList = null;
		admins = null;
		newspapers = null;
		selecteds = null;
		addMode = false;
		editMode = false;
		deleteMode = false;

	}

	public void delete() throws IOException {
		try {
			for (PromotionDto dto : selecteds) {
				promotionService.delete(dto.getSeq());
			}
			clear();
		} catch (IOException e) {
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

	public List<PromotionDto> getBaseList() throws IOException {
		if (baseList == null) {
			baseList = promotionService.getAllEditList(condition.genQuery());
			listModel = new PromotionListModel(baseList);
		}
		return baseList;
	}

	public void setBaseList(List<PromotionDto> baseList) {
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

	public List<PromotionDto> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<PromotionDto> selecteds) {
		this.selecteds = selecteds;
	}

	public boolean isDeleteMode() {
		return deleteMode;
	}

	public void setDeleteMode(boolean deleteMode) {
		this.deleteMode = deleteMode;
	}

	public void search() throws IOException {
		clear();
		for (String id : condition.getEmployeeIDs()) {
			List<PromotionAdminDto> list = promotionAdminService.findByEmployeeID(id);
			for (PromotionAdminDto dto : list) {
				if (!condition.getPromotionSeqs().contains(dto.getPromotionSeq()))
					condition.getPromotionSeqs().add(dto.getPromotionSeq());
			}
		}
		for (int seq : condition.getNewspaperSeqs()) {
			List<PromotionNewspaperDto> list = promotionNewspaperService.findByNewspaperSeq(seq);
			System.out.println("newspaper size:" + list.size());
			for (PromotionNewspaperDto dto : list) {
				if (!condition.getPromotionSeqs().contains(dto.getNewspaperSeq()))
					condition.getPromotionSeqs().add(dto.getNewspaperSeq());
			}
		}
	}

	public PromotionDto getEditDto() {
		return editDto;
	}

	public void setEditDto(PromotionDto editDto) {
		this.editDto = editDto;
	}

	public DualListModel<WorkInfoDto> getAdmins() {
		if (admins == null) {
			try {
				List<WorkInfoDto> allEmployees = workInfoService
						.getAllList(new DynamicQuery());

				allEmployees.removeAll(editDto.getAdmins());
				admins = new DualListModel<WorkInfoDto>(allEmployees,
						editDto.getAdmins());

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return admins == null ? new DualListModel<WorkInfoDto>(
				new ArrayList<WorkInfoDto>(), new ArrayList<WorkInfoDto>())
				: admins;
	}

	public void setAdmins(DualListModel<WorkInfoDto> admins) {
		this.admins = admins;
	}

	public PromotionCondition getCondition() {
		return condition;
	}

	public void setCondition(PromotionCondition condition) {
		this.condition = condition;
	}

	public List<String> completeName(String query) {
		ArrayList<String> list = new ArrayList<String>();
		for (PromotionDto dto : getAllList()) {
			if (dto.getName().toLowerCase().contains(query.toLowerCase()))
				list.add(dto.getName());
		}
		return list;
	}

	public List<String> completePlace(String query) {
		ArrayList<String> list = new ArrayList<String>();
		for (PromotionDto dto : getAllList()) {
			if (dto.getPlace().toLowerCase().contains(query.toLowerCase()))
				list.add(dto.getPlace());
		}
		return list;
	}

	public List<PromotionDto> getAllList() {
		if (allList == null) {
			try {
				allList = promotionService.getAllList(new DynamicQuery());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return allList == null ? new ArrayList<PromotionDto>() : allList;
	}

	public void setAllList(List<PromotionDto> allList) {
		this.allList = allList;
	}

	public void seqValidate(FacesContext context, UIComponent component, Object value)
	{
		int seq = (Integer)value;
		if(seq == 0)
		{
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Information Source is required.", null));
		}
	}

	public DualListModel<NewspaperDto> getNewspapers() {
		if (newspapers == null) {
			try {
				List<NewspaperDto> allList = newspaperService
						.getAllList(new DynamicQuery());

				System.out.println("newspaper size: " + allList.size());

				System.out.println("editDto newspaper size: " + editDto.getNewspapers().size());
				allList.removeAll(editDto.getNewspapers());
				newspapers = new DualListModel<NewspaperDto>(allList,
						editDto.getNewspapers());

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return newspapers == null ? new DualListModel<NewspaperDto>(
				new ArrayList<NewspaperDto>(), new ArrayList<NewspaperDto>())
				: newspapers;
	}

	public void setNewspapers(DualListModel<NewspaperDto> newspapers) {
		this.newspapers = newspapers;
	}

}
