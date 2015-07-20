package npp.impl.controler;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import npp.dto.DepartmentDto;
import npp.faces.DepartmentListModel;
import npp.spec.manager.SessionManager;
import npp.spec.service.DepartmentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Named
@SessionScoped
public class DepartmentControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1092313167526953164L;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private List<DepartmentDto> baseList = null;
	private DepartmentListModel listModel = null;

	private int first;

	private List<DepartmentDto> selecteds;

	@Inject
	private DepartmentService departmentService;
	@Inject
	private SessionManager sessionManager;

	public DepartmentListModel getAllList() {
		try {
			baseList = departmentService.getAllList();
			listModel = new DepartmentListModel(baseList);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return baseList == null ? new DepartmentListModel(
				new ArrayList<DepartmentDto>()) : listModel;
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public void delete() throws IOException {
		try {
			for (DepartmentDto dto : selecteds) {
				departmentService.delete(dto.getSeq());
			}
			selecteds = null;
		} catch (IOException e) {
			logger.error("Employee Date delete error.", e);
			sessionManager.addGlobalMessageFatal("Employee Data delete error.",
					null);
			throw e;
		}
	}

	public int getStatusListLength() {
		if (listModel == null)
			return 0;
		return listModel.getRowCount();
	}

	public List<DepartmentDto> getBaseList() throws IOException {
		if(baseList == null)
			baseList = departmentService.getAllList();
		return baseList;
	}

	public void setBaseList(List<DepartmentDto> baseList) {
		this.baseList = baseList;
	}


//	public EmployeeListModel getExampleList() {
//		ArrayList<EmployeeDto> exlist = new ArrayList<EmployeeDto>();
//		exlist.add(new EmployeeDto("10001", "Allen", "Jackson", new SexDto(1,
//				"M"), 26, "15100001111", "allen@mail.worksap.com",
//				new DepartmentDto(1, "Technical Division"), new JobTitleDto(1,
//						"In Service")));
//		return new EmployeeListModel(exlist);
//
//	}
}
