package npp.impl.controler;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import npp.condition.EmployeeRecordCondition;
import npp.condition.WorkInfoCondition;
import npp.dto.PunchDto;
import npp.dto.WorkInfoDto;
import npp.faces.PunchListModel;
import npp.spec.service.PunchService;
import npp.spec.service.WorkInfoService;


@Named
@SessionScoped
public class EmployeeRecordControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6570881403859382396L;

	private List<PunchDto> baseList = null;
	private PunchListModel listModel = null;

	private EmployeeRecordCondition condition;
	private WorkInfoCondition workInfoCondition;

	private List<PunchDto> selecteds;
	private String total = "";

	@Inject
	private PunchService punchService;
	@Inject
	private WorkInfoService workInfoService;

	@PostConstruct
	public void init(){
		condition = new EmployeeRecordCondition();
		workInfoCondition = new WorkInfoCondition();
	}

	public PunchListModel getListModel() {
		if (baseList == null) {
			try {
				baseList = punchService.getAllList(condition
						.genQuery());
				fillEmptyRecords(baseList);
				listModel = new PunchListModel(baseList);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return baseList == null ? new PunchListModel(new ArrayList<PunchDto>())
				: listModel;
	}

	private void fillEmptyRecords(List<PunchDto> punchList) {
		for (WorkInfoDto dto : condition.getEmployees()) {
			if (!hasRecordForPerson(dto, punchList)) {
				PunchDto newDto = new PunchDto();
				newDto.setEmployee(dto);
				punchList.add(newDto);
			}
		}
	}

	private boolean hasRecordForPerson(WorkInfoDto employee,
			List<PunchDto> punchList) {
		for (PunchDto dto : punchList) {
			if (dto.getEmployee().getId().equals(employee.getId()))
				return true;
		}
		return false;
	}

	public void search() throws IOException {
		clear();
		workInfoCondition.setName(condition.getEmployeeName());
		List<WorkInfoDto> list = workInfoService.getAllList(workInfoCondition
				.genQuery());
		for (WorkInfoDto dto : list) {
			condition.addEmployee(dto);
		}
	}

	public void clear() {
		baseList = null;
		selecteds = null;
		condition.getEmployees().clear();
	}

	// public void delete() throws IOException {
	// try {
	// for (StatusDto status : selecteds) {
	// statusService.delete(status.getSeq());
	// }
	// clear();
	// } catch (IOException e) {
	// logger.error("Employee Date delete error.", e);
	// sessionManager.addGlobalMessageFatal("Employee Data delete error.",
	// null);
	// throw e;
	// }
	// }

	public int getStatusListLength() {
		if (listModel == null)
			return 0;
		return listModel.getRowCount();
	}

	public EmployeeRecordCondition getCondition() {
		return condition;
	}

	public void setCondition(EmployeeRecordCondition condition) {
		this.condition = condition;
	}

	public List<PunchDto> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<PunchDto> selecteds) {
		this.selecteds = selecteds;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public void calculateTotal(WorkInfoDto dto) {
		double summary = 0.0d;

		for (PunchDto p : baseList) {
			if (p.getEmployee().getId().equals(dto.getId())) {
				try {
					summary += Double.parseDouble(p.getDuration());
				} catch (Exception e) {
				}
			}
		}

		total = String.format("%.2f", summary);
	}

	public void setListModel(PunchListModel listModel) {
		this.listModel = listModel;
	}

	public List<PunchDto> getBaseList() {
		return baseList;
	}

	public void setBaseList(List<PunchDto> baseList) {
		this.baseList = baseList;
	}
}
