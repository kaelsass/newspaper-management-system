package npp.impl.controler;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import npp.condition.MyRecordCondition;
import npp.dto.PunchDto;
import npp.faces.PunchListModel;
import npp.spec.manager.SessionManager;
import npp.spec.service.PunchService;


@Named
@SessionScoped
public class MyRecordControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6570881403859382396L;


	private List<PunchDto> baseList = null;
	private PunchListModel listModel = null;

	private MyRecordCondition condition;
	private List<PunchDto> selecteds;

	@Inject
	private PunchService punchService;
	@Inject
	private SessionManager sessionManager;


	private String total = "";

	@PostConstruct
	public void init() {
		condition = new MyRecordCondition();
		try {
			condition.setEmployeeID(sessionManager.getLoginUser().getEmployee()
					.getId());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public PunchListModel getListModel() {
		if (baseList == null) {
			try {
				baseList = punchService.getAllList(condition.genQuery());
				listModel = new PunchListModel(baseList);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return baseList == null ? new PunchListModel(new ArrayList<PunchDto>())
				: listModel;
	}


	// public boolean isIn()
	// {
	// if(unFinishedList != null)
	// return in;
	// unFinishedList = punchService.getUnFinishedList();
	// if(unFinishedList.size() == 0)
	// {
	// editDto = new
	// PunchDto(sessionManager.getLoginUser().getEmployee().getId(), new Date(),
	// "", null, "", false);
	// in = true;
	// }
	// else
	// {
	// editDto = new PunchDto(unFinishedList.get(0));
	// editDto.setPunchOutTime(new Date());
	// editDto.setFinished(true);
	// in = false;
	// }
	// return in;
	//
	// }
	// public void apply() throws IOException
	// {
	// if(isIn())
	// {
	// editDto.setPunchInNote(note);
	// punchService.add(editDto);
	// }
	// else
	// {
	// editDto.setPunchOutNote(note);
	// punchService.update(editDto);
	// clear();
	// }
	// }

	public void clear() {
		baseList = null;
	}

	public void calculateTotal(String name) {
		double summary = 0.0d;

		for (PunchDto p : baseList) {
			if (p.getEmployee().getName().equals(name)) {
				try {
					summary += Double.parseDouble(p.getDuration());
				} catch (Exception e) {
				}
			}
		}

		total = String.format("%.2f", summary);
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

	public MyRecordCondition getCondition() {
		return condition;
	}

	public void setCondition(MyRecordCondition condition) {
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

	// public List<StatusDto> getBaseList() throws IOException {
	// if (baseList == null)
	// baseList = statusService.getAllList();
	// return baseList;
	// }


	// public EmployeeListModel getExampleList() {
	// ArrayList<EmployeeDto> exlist = new ArrayList<EmployeeDto>();
	// exlist.add(new EmployeeDto("10001", "Allen", "Jackson", new SexDto(1,
	// "M"), 26, "15100001111", "allen@mail.worksap.com",
	// new DepartmentDto(1, "Technical Division"), new JobTitleDto(1,
	// "In Service")));
	// return new EmployeeListModel(exlist);
	//
	// }
}
