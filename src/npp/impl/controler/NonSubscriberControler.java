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

import npp.condition.DynamicQuery;
import npp.condition.IssueCondition;
import npp.condition.NonSubscriberCondition;
import npp.condition.Parameter;
import npp.dto.IssueDto;
import npp.dto.NonSubscriberDto;
import npp.dto.WorkInfoDto;
import npp.faces.NonSubscriberListModel;
import npp.spec.manager.SessionManager;
import npp.spec.service.IssueService;
import npp.spec.service.NonSubscriberService;
import npp.spec.service.PurposeService;
import npp.spec.service.WorkInfoService;
import npp.utils.DialogUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@SessionScoped
public class NonSubscriberControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6156728038843400463L;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private List<NonSubscriberDto> baseList = null;
	private NonSubscriberListModel listModel = null;
	private List<NonSubscriberDto> allList = null;
	private List<IssueDto> issueList = null;

	private int first;
	private boolean editMode;
	private boolean deleteMode;
	private boolean addDetailMode;

	private List<NonSubscriberDto> selecteds;
	private NonSubscriberDto editDto;
	private NonSubscriberCondition condition;

	@Inject
	private NonSubscriberService nonSubscriberService;
	@Inject
	private WorkInfoService workInfoService;
	@Inject
	private SessionManager sessionManager;

	@Inject
	private IssueService issueService;
	@Inject
	private PurposeService purposeService;

	private boolean editOrderMode;
	private int orderIndex;
	private List<NonSubscriberDto> orderList = null;
	private NonSubscriberListModel orderListModel = null;
	private List<NonSubscriberDto> selectedOrders;

	@PostConstruct
	public void init(){
		editMode = false;
		deleteMode = false;
		addDetailMode = false;
		editDto = new NonSubscriberDto();
		condition = new NonSubscriberCondition();
		try {
			allList = nonSubscriberService.getAllList(condition.genQuery());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		editOrderMode = false;
		selectedOrders = new ArrayList<NonSubscriberDto>();
	}

	public NonSubscriberListModel getListModel() {
		if (baseList == null) {
			try {
				baseList = nonSubscriberService
						.getAllList(condition.genQuery());
				listModel = new NonSubscriberListModel(baseList);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return baseList == null ? new NonSubscriberListModel(
				new ArrayList<NonSubscriberDto>()) : listModel;
	}

	public void startAdd() {
		editDto = new NonSubscriberDto();
		editMode = false;
		orderList = null;
	}

	public void startEdit(ActionEvent e) {
		NonSubscriberDto selected = (NonSubscriberDto) e.getComponent()
				.getAttributes().get("nonSubscriber");
		editDto = new NonSubscriberDto(selected);
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
		// WorkInfoDto employee =
		// workInfoService.findByName(editDto.getEmployee()
		// .getName());
		// editDto.setEmployee(employee);
		// if(editMode)
		// nonSubscriberService.update(editDto);
		// else
		// nonSubscriberService.add(editDto);
		for (NonSubscriberDto dto : orderList) {
			dto.setName(editDto.getName());
			dto.setAddress(editDto.getAddress());
			dto.setZipcode(editDto.getZipcode());
			dto.setWorkunit(editDto.getWorkunit());

			dto.setAge(editDto.getAge());
			dto.setSex(editDto.getSex());
			dto.setOccupationSeq(editDto.getOccupationSeq());
			dto.setEducationSeq(editDto.getEducationSeq());
			if (dto.getSeq() > 0) {
				nonSubscriberService.update(dto);
			} else {
				nonSubscriberService.add(dto);
			}
		}
		clear();
	}

	public void clear() {
		baseList = null;
		editDto = new NonSubscriberDto();
		selecteds = null;
		editMode = false;
		deleteMode = false;
		addDetailMode = false;
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
			for (NonSubscriberDto status : selecteds) {
				nonSubscriberService.delete(status.getSeq());
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

	public List<NonSubscriberDto> getBaseList() throws IOException {
		if (baseList == null) {
			baseList = nonSubscriberService.getAllList(condition.genQuery());
			listModel = new NonSubscriberListModel(baseList);
		}
		return baseList;
	}

	public void setBaseList(List<NonSubscriberDto> baseList) {
		this.baseList = baseList;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public List<NonSubscriberDto> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<NonSubscriberDto> selecteds) {
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
		IssueCondition ic = new IssueCondition();
		ic.setNewspaperSeqs(condition.getNewspaperSeqs());
		ic.setFrom(condition.getDateFrom());
		ic.setTo(condition.getDateTo());
		List<IssueDto> issues = issueService.getAllList(ic.genQuery());
		for (IssueDto dto : issues) {
			condition.getIssues().add(dto);
		}
	}

	public NonSubscriberCondition getCondition() {
		return condition;
	}

	public void setCondition(NonSubscriberCondition condition) {
		this.condition = condition;
	}

	public NonSubscriberDto getEditDto() {
		return editDto;
	}

	public void setEditDto(NonSubscriberDto editDto) {
		this.editDto = editDto;
	}

	public List<NonSubscriberDto> getAllList() throws IOException {
		if (allList == null) {
			NonSubscriberCondition sc = new NonSubscriberCondition();
			allList = nonSubscriberService.getAllList(sc.genQuery());
		}
		return allList;
	}

	public void setAllList(List<NonSubscriberDto> allList) {
		this.allList = allList;
	}

	public List<String> completeName(String query) throws IOException {
		ArrayList<String> list = new ArrayList<String>();
		for (NonSubscriberDto dto : getAllList()) {
			if (dto.getName().toLowerCase().contains(query.toLowerCase()))
				list.add(dto.getName());
		}
		return list;
	}

	public List<String> completeAddress(String query) throws IOException {
		ArrayList<String> list = new ArrayList<String>();
		for (NonSubscriberDto dto : getAllList()) {
			if (dto.getAddress().toLowerCase().contains(query.toLowerCase()))
				list.add(dto.getAddress());
		}
		return list;
	}

	public List<String> completeWorkunit(String query) throws IOException {
		ArrayList<String> list = new ArrayList<String>();
		for (NonSubscriberDto dto : getAllList()) {
			if (dto.getWorkunit().toLowerCase().contains(query.toLowerCase()))
				list.add(dto.getWorkunit());
		}
		return list;
	}

	public List<IssueDto> getIssueList() {
		if (issueList == null) {
			DynamicQuery dq = new DynamicQuery();
			dq.addParameter(new Parameter("newspaper_seq", "=", editDto
					.getIssue().getNewspaper().getSeq()));
			try {
				issueList = issueService.getAllList(dq);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				issueList = null;
			}
		}
		return issueList == null ? new ArrayList<IssueDto>() : issueList;
	}

	public void setIssueList(List<IssueDto> issueList) {
		this.issueList = issueList;
	}

	public void clearIssue() {
		issueList = null;
	}

	public void startAddDetail() {
		addDetailMode = true;
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

	public void startEditOrder(NonSubscriberDto dto, int index) {
		editDto = new NonSubscriberDto(dto);
		getOrderListModel();
		orderIndex = index;
		editOrderMode = true;
	}

	public NonSubscriberListModel getOrderListModel() {
		if (orderList == null) {
			DynamicQuery query = new DynamicQuery();
			if (editMode) {
				query.addParameter(new Parameter("name", "=", editDto.getName()));
				query.addParameter(new Parameter("address", "=", editDto
						.getAddress()));
				query.addParameter(new Parameter("zipcode", "=", editDto
						.getZipcode()));
				query.addParameter(new Parameter("workunit", "=", editDto
						.getWorkunit()));
			} else {
				query.addParameter(new Parameter("seq", "=", 0));
			}
			try {
				orderList = nonSubscriberService.getAllList(query);
				orderListModel = new NonSubscriberListModel(orderList);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return orderListModel;
	}

	public List<NonSubscriberDto> getSelectedOrders() {
		return selectedOrders;
	}

	public void setSelectedOrders(List<NonSubscriberDto> selectedOrders) {
		this.selectedOrders = selectedOrders;
	}

	public void deleteOrders() throws IOException {
		for (NonSubscriberDto dto : selectedOrders) {
			orderList.remove(dto);
			nonSubscriberService.delete(dto.getSeq());
		}
		orderListModel = new NonSubscriberListModel(orderList);
	}

	public void applyOrder() throws IOException {
		editDto.setIssue(issueService.findBySeq(editDto.getIssue().getSeq()));
		WorkInfoDto employee = workInfoService.findByName(editDto.getEmployee()
				.getName());
		editDto.setEmployee(employee);
		editDto.setPurpose(purposeService.findBySeq(editDto.getPurpose().getSeq()));
		if (!editOrderMode) {
			orderList.add(new NonSubscriberDto(editDto));
		} else {
			updateData(editDto, orderIndex);
		}
		orderListModel = new NonSubscriberListModel(orderList);
		DialogUtil.hideDialog("addOrder_w");
	}

	private void updateData(NonSubscriberDto dto, int index) {
		orderList.set(index, new NonSubscriberDto(dto));
	}

}
