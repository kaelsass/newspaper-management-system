package npp.impl.controler;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import npp.condition.DynamicQuery;
import npp.dto.TrackerDto;
import npp.dto.WorkInfoDto;
import npp.faces.TrackerListModel;
import npp.spec.manager.SessionManager;
import npp.spec.service.TrackerService;
import npp.spec.service.WorkInfoService;

import org.primefaces.model.DualListModel;

@Named
@SessionScoped
public class TrackerControler implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 6100364088420414584L;
	private List<TrackerDto> baseList = null;
	private TrackerListModel listModel = null;
	private DualListModel<WorkInfoDto> reviewers;

	private int first;
	private boolean addMode;
	private boolean editMode;
	private boolean deleteMode;

	private List<TrackerDto> selecteds;
	private TrackerDto editDto;

	@Inject
	private WorkInfoService workInfoService;
	@Inject
	private TrackerService trackerService;

	@Inject
	private SessionManager sessionManager;

	@PostConstruct
	public void init() {
		addMode = false;
		editMode = false;
		deleteMode = false;
		editDto = new TrackerDto();
	}

	public TrackerListModel getListModel() {
		if (baseList == null) {
			try {
				baseList = trackerService.getAllList();
				listModel = new TrackerListModel(baseList);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return baseList == null ? new TrackerListModel(
				new ArrayList<TrackerDto>()) : listModel;
	}

	public void startAdd() {
		editDto = new TrackerDto();
		editDto.setAddDate(new Date());
		editDto.setModDate(new Date());

		addMode = true;
		editMode = false;
	}

	public void startEdit(ActionEvent e) {
		TrackerDto dto = (TrackerDto) e.getComponent().getAttributes()
				.get("tracker");
		editDto = new TrackerDto(dto);
		editDto.setModDate(new Date());
		updateReviewers();
		addMode = false;
		editMode = true;
	}

	private void updateReviewers() {
		reviewers = null;
	}

	public void startDelete() {
		deleteMode = true;
	}

	public void endDelete() {
		deleteMode = false;
	}

	public void apply() throws IOException {

		editDto.setReviewers(getReviewers().getTarget());

		if (addMode)
			trackerService.add(editDto);
		else if (editMode)
			trackerService.update(editDto);
		clear();
	}

	public void clear() {
		baseList = null;
		reviewers = null;
		editDto = new TrackerDto();
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
			for (TrackerDto dto : selecteds) {
				trackerService.delete(dto.getSeq());
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

	public List<TrackerDto> getBaseList() throws IOException {
		if (baseList == null) {
			baseList = trackerService.getAllList();
			listModel = new TrackerListModel(baseList);
		}
		return baseList;
	}

	public void setBaseList(List<TrackerDto> baseList) {
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

	public List<TrackerDto> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<TrackerDto> selecteds) {
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

	public TrackerDto getEditDto() {
		return editDto;
	}

	public void setEditDto(TrackerDto editDto) {
		this.editDto = editDto;
	}

	public DualListModel<WorkInfoDto> getReviewers() {
		if (reviewers == null) {
			try {
				List<WorkInfoDto> allEmployees = workInfoService
						.getAllList(new DynamicQuery());

				allEmployees.removeAll(editDto.getReviewers());
				reviewers = new DualListModel<WorkInfoDto>(allEmployees,
						editDto.getReviewers());

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return reviewers == null ? new DualListModel<WorkInfoDto>(new ArrayList<WorkInfoDto>(),
				new ArrayList<WorkInfoDto>()) : reviewers;
	}

	public void setReviewers(DualListModel<WorkInfoDto> reviewers) {
		this.reviewers = reviewers;
	}

}
