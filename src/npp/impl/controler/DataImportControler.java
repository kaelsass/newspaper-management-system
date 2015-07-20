package npp.impl.controler;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import npp.condition.DynamicQuery;
import npp.dto.DepartmentDto;
import npp.dto.JobCategoryDto;
import npp.dto.JobTitleDto;
import npp.dto.StatusDto;
import npp.dto.SupervisorDto;
import npp.dto.WorkInfoDto;
import npp.faces.WorkInfoListModel;
import npp.spec.manager.SessionManager;
import npp.spec.service.DepartmentService;
import npp.spec.service.JobCategoryService;
import npp.spec.service.JobTitleService;
import npp.spec.service.StatusService;
import npp.spec.service.WorkInfoService;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.primefaces.model.UploadedFile;

@Named
@SessionScoped
public class DataImportControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1118553953058466355L;

	@Inject
	private WorkInfoService workInfoService;

	private List<WorkInfoDto> allList = null;

	@Inject
	private JobTitleService jobTitleService;
	@Inject
	private DepartmentService departmentService;
	@Inject
	private StatusService statusService;
	@Inject
	private JobCategoryService jobCategoryService;
	@Inject
	private SessionManager sessionManager;

	private UploadedFile uploadFile;

	//this controler has no functions, just a media test.
	@PostConstruct
	public void init() {
		try {
			allList = workInfoService.getAllList(new DynamicQuery());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void upload() throws IOException {
		if (uploadFile != null) {
			ArrayList<WorkInfoDto> data = readExcelFromInputstream(uploadFile
					.getInputstream());
			addEmployees(data);
			// FacesMessage message = new FacesMessage("Succesful",
			// uploadFile.getFileName() + " is uploaded.");
			// FacesContext.getCurrentInstance().addMessage(null, message);
		} else {
			System.out.println("no file");
			sessionManager.addGlobalMessageFatal(
					"Please choose data file to upload.", null);

		}
	}

	private void addEmployees(ArrayList<WorkInfoDto> list) throws IOException {
		for (WorkInfoDto e : list) {
			workInfoService.add(e);
		}
	}

	private ArrayList<WorkInfoDto> readExcelFromInputstream(
			InputStream inputstream) {

		// HSSFWorkbook wb = new HSSFWorkbook(new
		// FileInputStream("e:\\workbook.xls"));
		// HSSFSheet sheet = wb.getSheetAt(0);
		//
		// for (Iterator<Row> iter = (Iterator<Row>) sheet.rowIterator();
		// iter.hasNext();) {
		// Row row = iter.next();
		// for (Iterator<Cell> iter2 = (Iterator<Cell>) row.cellIterator();
		// iter2.hasNext();) {
		// Cell cell = iter2.next();
		// String content = cell.getStringCellValue();// 除非是sring类型，否则这样迭代读取会有错误
		// System.out.println(content);
		boolean success = true;
		HSSFWorkbook rwb = null;
		try {

			rwb = new HSSFWorkbook(inputstream);
			HSSFSheet st = rwb.getSheetAt(0);
			if (st != null) {
				ArrayList<WorkInfoDto> list = new ArrayList<WorkInfoDto>();
				ArrayList<String> lables = new ArrayList<String>();
				boolean first = true;
				for (Iterator<Row> iter = st.rowIterator(); iter.hasNext();) {
					Row row = iter.next();
					if (first) {
						for (Iterator<Cell> iter2 = row.cellIterator(); iter2
								.hasNext();) {
							Cell cell = iter2.next();
							lables.add(cell.getStringCellValue());
						}
						first = false;
					}

					else {
						int j = 0;
						WorkInfoDto e = new WorkInfoDto();
						boolean complete = true;
						for (Iterator<Cell> iter2 = row.cellIterator(); iter2
								.hasNext();) {
							Cell cell = iter2.next();
							String val = "";
							if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN)
								val = String
										.valueOf(cell.getBooleanCellValue());
							else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
								val = String.valueOf((int) cell
										.getNumericCellValue());
							else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
								val = cell.getStringCellValue();
							if (lables.get(j).toLowerCase().equals("id")
									&& exist(val)) {
								complete = false;
								success = false;
							}
							setEmployeeValue(lables.get(j++), val, e);
						}
						if (complete)
							list.add(e);
						else {
							sessionManager
									.addGlobalMessageWarn(
											"ID: "
													+ e.getId()
													+ " has existed. This employee is not added into system.",
											null);
							// FacesMessage message = new FacesMessage("ID: " +
							// e.getId() +
							// " has existed. This employee is not added into system.",null);
						}
					}
				}
				if (success)
					sessionManager.addGlobalMessageInfo(
							"The data file is successfully uploaded.", null);
				else
					sessionManager
							.addGlobalMessageWarn(
									"There are employees that not be added to the system.",
									null);
				return list;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				inputstream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private boolean exist(String id) {
		return allList.contains(id);
	}

	private void setEmployeeValue(String lable, String content, WorkInfoDto e)
			throws IOException {
		lable = lable.toLowerCase();
		if (lable.equals("id")) {
			e.setId(content);
		} else if (lable.equals("name")) {
			e.setName(content);
		} else if (lable.equals("phone")) {
			e.setPhone(content);
		} else if (lable.equals("email")) {
			e.setEmail(content);
		} else if (lable.equals("job title")) {
			e.setJobTitle(jobTitleService.findByName(content));
		} else if (lable.equals("sub unit")) {
			e.setDepartment(departmentService.findByName(content));
		} else if (lable.equals("employment status")) {
			e.setStatus(statusService.findByName(content));
		} else if (lable.equals("job category")) {
			e.setJobCategory(jobCategoryService.findByName(content));
		}
	}


	public WorkInfoListModel getExampleList() {
		ArrayList<WorkInfoDto> exlist = new ArrayList<WorkInfoDto>();
		exlist.add(new WorkInfoDto("10001", "Allen Jackson", new JobTitleDto(1,
				"Accountant", "", ""), new StatusDto(1, "Full time Contract"),
				new JobCategoryDto(1, "Craft Workers"), new DepartmentDto(1,
						"HR Department", "", 0), new SupervisorDto(),
				"15100001111", "allen@mail.worksap.com", ""));
		return new WorkInfoListModel(exlist);

	}

	public UploadedFile getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(UploadedFile uploadFile) {
		this.uploadFile = uploadFile;
	}
}
