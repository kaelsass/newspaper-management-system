package npp.impl.controler;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import npp.dto.OfficeDto;
import npp.faces.OfficeListModel;
import npp.spec.manager.SessionManager;
import npp.spec.service.OfficeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Named
@SessionScoped
public class OfficeControler implements Serializable {


	/**
	 *
	 */
	private static final long serialVersionUID = -8855213582588827150L;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private List<OfficeDto> baseList = null;
	private OfficeListModel listModel = null;

	private int first;

	private List<OfficeDto> selecteds;

	@Inject
	private OfficeService officeService;
	@Inject
	private SessionManager sessionManager;

	public OfficeListModel getAllList() {
		try {
			baseList = officeService.getAllList();
			listModel = new OfficeListModel(baseList);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return baseList == null ? new OfficeListModel(
				new ArrayList<OfficeDto>()) : listModel;
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public void delete() throws IOException {
		try {
			for (OfficeDto dto : selecteds) {
				officeService.delete(dto.getSeq());
			}
			selecteds = null;
		} catch (IOException e) {
			logger.error("Office Date delete error.", e);
			sessionManager.addGlobalMessageFatal("Office Data delete error.",
					null);
			throw e;
		}
	}

	public int getStatusListLength() {
		if (listModel == null)
			return 0;
		return listModel.getRowCount();
	}

	public List<OfficeDto> getBaseList() throws IOException {
		if(baseList == null)
			baseList = officeService.getAllList();
		return baseList;
	}

	public void setBaseList(List<OfficeDto> baseList) {
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
