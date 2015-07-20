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
import npp.condition.Parameter;
import npp.condition.SubscriberCondition;
import npp.dto.NewspaperDto;
import npp.dto.SubscriberDto;
import npp.faces.SubscriberListModel;
import npp.spec.manager.SessionManager;
import npp.spec.service.NewspaperService;
import npp.spec.service.SubscriberService;
import npp.utils.DialogUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@SessionScoped
public class SubscriberControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6156728038843400463L;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private List<SubscriberDto> baseList = null;
	private SubscriberListModel listModel = null;
	private List<SubscriberDto> allList = null;

	private int first;
	private boolean editMode;
	private boolean deleteMode;
	private boolean addDetailMode;

	private List<SubscriberDto> selecteds;
	private SubscriberDto editDto;
	private SubscriberCondition condition;

	@Inject
	private SubscriberService subscriberService;
	@Inject
	private SessionManager sessionManager;
	@Inject
	private NewspaperService newspaperService;

	private boolean editOrderMode;
	private List<SubscriberDto> orderList = null;
	private SubscriberListModel orderListModel = null;
	private List<SubscriberDto> selectedOrders;

	private int orderIndex;

	@PostConstruct
	public void init() {
		editMode = false;
		deleteMode = false;
		addDetailMode = false;
		editDto = new SubscriberDto();
		condition = new SubscriberCondition();
		try {
			allList = subscriberService.getAllList(condition.genQuery());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		editOrderMode = false;
		selectedOrders = new ArrayList<SubscriberDto>();
	}

	public SubscriberListModel getListModel() {
		if (baseList == null) {
			try {
				baseList = subscriberService.getAllList(condition.genQuery());
				listModel = new SubscriberListModel(baseList);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return baseList == null ? new SubscriberListModel(
				new ArrayList<SubscriberDto>()) : listModel;
	}

	public void startAdd() {
		editDto = new SubscriberDto();
		editMode = false;
		orderList = null;
	}

	public void startEdit(ActionEvent e) {
		SubscriberDto selected = (SubscriberDto) e.getComponent()
				.getAttributes().get("subscriber");
		editDto = new SubscriberDto(selected);
		editMode = true;
		orderList = null;
	}

	public void startDelete() {
		deleteMode = true;
	}

	public void endDelete() {
		deleteMode = false;
	}

	public void apply() throws IOException {
		// if (editMode)
		// subscriberService.update(editDto);
		// else
		// subscriberService.add(editDto);
		for (SubscriberDto dto : orderList) {
			dto.setName(editDto.getName());
			dto.setAddress(editDto.getAddress());
			dto.setZipcode(editDto.getZipcode());
			dto.setAge(editDto.getAge());
			dto.setSex(editDto.getSex());
			dto.setOccupationSeq(editDto.getOccupationSeq());
			dto.setEducationSeq(editDto.getEducationSeq());
			if (dto.getSeq() > 0) {
				subscriberService.update(dto);
			} else {
				subscriberService.add(dto);
			}
		}
		clear();
	}

	public void clear() {
		baseList = null;
		editDto = new SubscriberDto();
		selecteds = null;
		editMode = false;
		deleteMode = false;
		addDetailMode = false;
		allList = null;
	}

	public void startAddDetail() {
		addDetailMode = true;
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public void delete() throws IOException {
		try {
			for (SubscriberDto status : selecteds) {
				subscriberService.delete(status.getSeq());
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

	public List<SubscriberDto> getBaseList() throws IOException {
		if (baseList == null) {
			baseList = subscriberService.getAllList(condition.genQuery());
			listModel = new SubscriberListModel(baseList);
		}
		return baseList;
	}

	public void setBaseList(List<SubscriberDto> baseList) {
		this.baseList = baseList;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public List<SubscriberDto> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<SubscriberDto> selecteds) {
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
	}

	public SubscriberCondition getCondition() {
		return condition;
	}

	public void setCondition(SubscriberCondition condition) {
		this.condition = condition;
	}

	public SubscriberDto getEditDto() {
		return editDto;
	}

	public void setEditDto(SubscriberDto editDto) {
		this.editDto = editDto;
	}

	public List<SubscriberDto> getAllList() throws IOException {
		if (allList == null) {
			SubscriberCondition sc = new SubscriberCondition();
			allList = subscriberService.getAllList(sc.genQuery());
		}
		return allList;
	}

	public void setAllList(List<SubscriberDto> allList) {
		this.allList = allList;
	}

	public List<String> completeName(String query) {
		ArrayList<String> list = new ArrayList<String>();
		for (SubscriberDto dto : allList) {
			if (dto.getName().toLowerCase().contains(query.toLowerCase()))
				list.add(dto.getName());
		}
		return list;
	}

	public List<String> completeAddress(String query) {
		ArrayList<String> list = new ArrayList<String>();
		for (SubscriberDto dto : allList) {
			if (dto.getAddress().toLowerCase().contains(query.toLowerCase()))
				list.add(dto.getAddress());
		}
		return list;
	}

	public void changeNewspaper() throws IOException {
		NewspaperDto newspaperDto = newspaperService.findBySeq(editDto
				.getNewspaper().getSeq());
		if (newspaperDto == null)
			newspaperDto = new NewspaperDto();
		editDto.setNewspaper(newspaperDto);
	}

	public void moneyPayValidate(FacesContext context, UIComponent component,
			Object value) throws IOException {
		double number = (Double) value;
		if (number < editDto.getPayables()) {
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Money Paid is less than Payables.", null));
		}
	}

	public boolean isAddDetailMode() {
		return addDetailMode;
	}

	public void setAddDetailMode(boolean addDetailMode) {
		this.addDetailMode = addDetailMode;
	}

	public void startAddOrder() {
		editDto.setSeq(0);
		editDto.clearOrderInfo();
		// getOrderListModel();
		editOrderMode = false;
	}

	public void startEditOrder(SubscriberDto dto, int index) {
		editDto = new SubscriberDto(dto);
		getOrderListModel();
		orderIndex = index;
		editOrderMode = true;
	}

	public SubscriberListModel getOrderListModel() {
		if (orderList == null) {
			DynamicQuery query = new DynamicQuery();
			if (editMode) {
				query.addParameter(new Parameter("name", "=", editDto.getName()));
				query.addParameter(new Parameter("address", "=", editDto
						.getAddress()));
				query.addParameter(new Parameter("zipcode", "=", editDto
						.getZipcode()));
			} else {
				query.addParameter(new Parameter("seq", "=", 0));
			}
			try {
				orderList = subscriberService.getAllList(query);
				orderListModel = new SubscriberListModel(orderList);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return orderListModel;
	}

	public List<SubscriberDto> getSelectedOrders() {
		return selectedOrders;
	}

	public void setSelectedOrders(List<SubscriberDto> selectedOrders) {
		this.selectedOrders = selectedOrders;
	}

	public void deleteOrders() throws IOException {

		System.out.println("selectedOrders : " + selectedOrders);
		for (SubscriberDto dto : selectedOrders) {
			System.out.println("newspaper name : " + dto.getNewspaper().getName());
			orderList.remove(dto);
			subscriberService.delete(dto.getSeq());
		}
		selectedOrders = new ArrayList<SubscriberDto>();
		orderListModel = new SubscriberListModel(orderList);
	}

	public void applyOrder() throws IOException {
		System.out.println("apply order");
		editDto.setNewspaper(newspaperService.findBySeq(editDto.getNewspaper().getSeq()));

		System.out.println("editOrderMode : " + editOrderMode);
		System.out.println("orderIndex : " + orderIndex);
		if (!editOrderMode) {
			orderList.add(new SubscriberDto(editDto));
		} else {
			updateData(editDto, orderIndex);
		}
		orderListModel = new SubscriberListModel(orderList);
		DialogUtil.hideDialog("addOrder_w");

	}

	private void updateData(SubscriberDto dto, int index) {
		orderList.set(index, new SubscriberDto(dto) );

	}

}
