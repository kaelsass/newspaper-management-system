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

import npp.dto.PerformanceDto;
import npp.dto.TrackerDto;
import npp.dto.TrackerLogDto;
import npp.faces.TrackerLogListModel;
import npp.spec.manager.SessionManager;
import npp.spec.service.PerformanceService;
import npp.spec.service.TrackerLogService;

@Named
@SessionScoped
public class TrackerLogControler implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 6100364088420414584L;
	private List<TrackerLogDto> baseList = null;
	private TrackerLogListModel listModel = null;
	private List<PerformanceDto> performanceList = null;

	private int first;
	private boolean addMode;
	private boolean editMode;
	private boolean deleteMode;

	private List<TrackerLogDto> selecteds;
	private TrackerDto tracker;
	private TrackerLogDto editDto;

	@Inject
	private TrackerLogService trackerLogService;

	@Inject
	private PerformanceService performanceService;

	@Inject
	private SessionManager sessionManager;

	@PostConstruct
	public void init() {
		addMode = false;
		editMode = false;
		deleteMode = false;
		tracker = new TrackerDto();
		editDto = new TrackerLogDto();
		try {
			performanceList = performanceService.getAllList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public TrackerLogListModel getListModel() {
		if (baseList == null) {
			try {
				baseList = trackerLogService.findByTrackerSeq(tracker.getSeq());
				listModel = new TrackerLogListModel(baseList);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return baseList == null ? new TrackerLogListModel(
				new ArrayList<TrackerLogDto>()) : listModel;
	}

	public void startAdd() throws IOException {
		editDto = new TrackerLogDto();
		editDto.setTracker(tracker);
		editDto.setReviewer(sessionManager.getLoginUser().getEmployee());
		editDto.setAddDate(new Date());
		addMode = true;
		editMode = false;
	}
	public void startEditTracker(ActionEvent e) {
		tracker = (TrackerDto) e.getComponent().getAttributes()
				.get("tracker");
		clear();
	}

	public void startEdit(ActionEvent e) throws IOException {
		TrackerLogDto dto = (TrackerLogDto) e.getComponent().getAttributes()
				.get("trackerLog");
		editDto = new TrackerLogDto(dto);
		editDto.setReviewer(sessionManager.getLoginUser().getEmployee());
		editDto.setTracker(tracker);
		editDto.setModDate(new Date());
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
		if (addMode)
			trackerLogService.add(editDto);
		else if (editMode)
			trackerLogService.update(editDto);
		clear();
	}

	public void clear() {
		baseList = null;
		editDto = new TrackerLogDto();
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
			for (TrackerLogDto dto : selecteds) {
				trackerLogService.delete(dto.getSeq());
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

	public List<TrackerLogDto> getBaseList() throws IOException {
		if (baseList == null) {
			baseList = trackerLogService.findByTrackerSeq(tracker.getSeq());
			listModel = new TrackerLogListModel(baseList);
		}
		return baseList;
	}

	public void setBaseList(List<TrackerLogDto> baseList) {
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

	public List<TrackerLogDto> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<TrackerLogDto> selecteds) {
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

	public TrackerLogDto getEditDto() {
		return editDto;
	}

	public void setEditDto(TrackerLogDto editDto) {
		this.editDto = editDto;
	}

	public TrackerDto getTracker() {
		return tracker;
	}

	public void setTracker(TrackerDto tracker) {
		this.tracker = tracker;
	}

	public List<PerformanceDto> getPerformanceList() {
		return performanceList;
	}

	public void setPerformanceList(List<PerformanceDto> performanceList) {
		this.performanceList = performanceList;
	}
}
