package npp.impl.controler;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import npp.condition.DynamicQuery;
import npp.condition.WorkInfoCondition;
import npp.dto.DepartmentDto;
import npp.dto.JobCategoryDto;
import npp.dto.JobTitleDto;
import npp.dto.StatusDto;
import npp.dto.SupervisorDto;
import npp.dto.UserDto;
import npp.dto.WorkInfoDto;
import npp.faces.WorkInfoListModel;
import npp.spec.manager.SessionManager;
import npp.spec.service.DepartmentService;
import npp.spec.service.JobCategoryService;
import npp.spec.service.JobTitleService;
import npp.spec.service.StatusService;
import npp.spec.service.UserService;
import npp.spec.service.WorkInfoService;
import npp.utils.FileUtil;
import npp.utils.PasswordHash;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@SessionScoped
public class WorkInfoControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -837767060030386913L;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private List<WorkInfoDto> baseList = null;
	private WorkInfoListModel listModel = null;
	private List<WorkInfoDto> allList = null;

	private int first;
	private String firstName;
	private String lastName;
	private boolean addLoginUser = false;
	private boolean deleteMode = false;
	private int viewMode = 0;
	private String password;
	private String passwordConfirm;

	private WorkInfoDto editWorkInfo;
	private UserDto editUser;

	private WorkInfoCondition condition;

	private List<WorkInfoDto> selecteds;

	@Inject
	private WorkInfoService workInfoService;
	@Inject
	private UserService userService;
	@Inject
	private SessionManager sessionManager;
	@Inject
	private JobTitleService jobTitleService;
	@Inject
	private DepartmentService departmentService;
	@Inject
	private StatusService statusService;
	@Inject
	private JobCategoryService jobCategoryService;

	private String tmpName;

	private boolean hasPhoto = false;

	private UploadedFile uploadFile;


	@PostConstruct
	public void init() {
		condition = new WorkInfoCondition();
		uploadFile = null;
	}

	public WorkInfoListModel getListModel() {
		if (baseList == null) {
			try {
				baseList = workInfoService.getAllList(condition.genQuery());
				listModel = new WorkInfoListModel(baseList);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return baseList == null ? new WorkInfoListModel(
				new ArrayList<WorkInfoDto>()) : listModel;

	}

	public void startAdd() {
		editWorkInfo = new WorkInfoDto();
		editUser = new UserDto();
	}

	public void apply() throws IOException {
		try {
			if (addLoginUser) {
				if (!password.equals(passwordConfirm)) {
					sessionManager.addGlobalMessageFatal(
							"Password is not equal to Confirm Password.", null);
					throw new ValidatorException(new FacesMessage(
							FacesMessage.SEVERITY_FATAL,
							"Password and Confirm Password are not equal.",
							null));
				}
			}
			if (hasPhoto) {
				String photoName = editWorkInfo.getId()
						+ FileUtil.getSuffix(tmpName);
				FileUtil.renameFile(sessionManager.getPhotoPath(), tmpName,
						photoName);
				editWorkInfo.setPhotoName(photoName);
			}
			editWorkInfo.setName(firstName + " " + lastName);
			workInfoService.add(editWorkInfo);
			if (addLoginUser) {
				editUser.setPassword(PasswordHash.makePasswordHash(password));
				editUser.getEmployee().setId(editWorkInfo.getId());
				userService.add(editUser);
			}
			clear();
		} catch (IOException e) {
			logger.error("Employee Date update error.", e);
			sessionManager.addGlobalMessageFatal("Employee Data update error.",
					null);
			throw e;
		}
	}

	public void search() throws IOException {
		// if(condition.getSupervisorName()!= null &&
		// !condition.getSupervisorName().equals(""))
		// {
		// WorkInfoCondition sc = new WorkInfoCondition();
		// sc.setName(condition.getSupervisorName());
		// List<WorkInfoDto> superList =
		// workInfoService.getAllList(sc.genQuery());
		// }
		clear();
	}

	public void clear() {
		baseList = null;
		allList = null;
		addLoginUser = false;
		first = 0;
		firstName = "";
		lastName = "";
		password = "";
		passwordConfirm = "";
		selecteds = null;
		deleteMode = false;
		hasPhoto = false;
	}

	public void startDelete() {
		deleteMode = true;
	}

	public void endDelete() {
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
			for (WorkInfoDto employee : selecteds) {
				workInfoService.delete(employee.getId());
			}
			clear();
		} catch (IOException e) {
			logger.error("Employee Date delete error.", e);
			sessionManager.addGlobalMessageFatal("Employee Data delete error.",
					null);
			throw e;
		}
	}

	public int getEmployeeListLength() {
		if (listModel == null)
			return 0;
		return listModel.getRowCount();
	}

	public WorkInfoCondition getCondition() {
		return condition;
	}

	public void setCondition(WorkInfoCondition condition) {
		this.condition = condition;
	}

	public List<WorkInfoDto> getBaseList() throws IOException {
		if (baseList == null)
			baseList = workInfoService.getAllList(new DynamicQuery());
		return baseList;
	}

	public void setBaseList(List<WorkInfoDto> baseList) {
		this.baseList = baseList;
	}

	public List<WorkInfoDto> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<WorkInfoDto> selecteds) {
		this.selecteds = selecteds;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isAddLoginUser() {
		return addLoginUser;
	}

	public void setAddLoginUser(boolean addLoginUser) {
		this.addLoginUser = addLoginUser;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public WorkInfoDto getEditWorkInfo() {
		if (editWorkInfo == null)
			editWorkInfo = new WorkInfoDto();
		return editWorkInfo;
	}

	public void setEditWorkInfo(WorkInfoDto editWorkInfo) {
		this.editWorkInfo = editWorkInfo;
	}

	public UserDto getEditUser() {
		if (editUser == null)
			editUser = new UserDto();
		return editUser;
	}

	public void setEditUser(UserDto editUser) {
		this.editUser = editUser;
	}

	public void handleFileUpload(FileUploadEvent event) {

		try {
			uploadFile = event.getFile();
			tmpName = UUID.randomUUID().toString()
					+ FileUtil.getSuffix(uploadFile.getFileName());
			FileUtil.copyFile(sessionManager.getPhotoPath() + tmpName,
					uploadFile.getInputstream());
			hasPhoto = true;
			sessionManager.addGlobalMessageInfo("Successful",
					uploadFile.getFileName() + " is uploaded.");
		} catch (IOException e) {
			e.printStackTrace();
			sessionManager.addGlobalMessageInfo("Fail",
					uploadFile.getFileName() + " is uploaded.");
		}

	}

	public int getViewMode() {
		return viewMode;
	}

	public void setViewMode(int viewMode) {
		this.viewMode = viewMode;
	}

	public List<String> completeEmployeeName(String query) {
		ArrayList<String> filterList = new ArrayList<String>();
		if (baseList == null) {
			try {
				baseList = workInfoService.getAllList(condition.genQuery());
				listModel = new WorkInfoListModel(baseList);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		for (WorkInfoDto dto : baseList) {
			if (dto.getName().toLowerCase().contains(query.toLowerCase()))
				filterList.add(dto.getName());
		}
		return filterList;
	}

	public void nameValidate(FacesContext context, UIComponent component,
			Object value) {
		boolean find = false;
		String name = (String) value;

		for (WorkInfoDto dto : getAllList()) {
			if (dto.getName().equals(name)) {
				find = true;
			}
		}
		if (!find) {
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_FATAL,
					"Employee Name doesn't exist.", null));
		}
	}

	public UploadedFile getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(UploadedFile uploadFile) {
		this.uploadFile = uploadFile;
	}


	public void upload() throws IOException {
		System.out.println("uploadFile: " + uploadFile.getFileName());
		if (uploadFile != null && !uploadFile.getFileName().trim().equals("")) {
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
		uploadFile = null;
	}

	private void addEmployees(ArrayList<WorkInfoDto> list) throws IOException {
		for (WorkInfoDto e : list) {
			workInfoService.add(e);
		}
	}

	private ArrayList<WorkInfoDto> readExcelFromInputstream(
			InputStream inputstream) {

//		// HSSFWorkbook wb = new HSSFWorkbook(new
//		// FileInputStream("e:\\workbook.xls"));
//		// HSSFSheet sheet = wb.getSheetAt(0);
//		//
//		// for (Iterator<Row> iter = (Iterator<Row>) sheet.rowIterator();
//		// iter.hasNext();) {
//		// Row row = iter.next();
//		// for (Iterator<Cell> iter2 = (Iterator<Cell>) row.cellIterator();
//		// iter2.hasNext();) {
//		// Cell cell = iter2.next();
//		// String content = cell.getStringCellValue();// 除非是sring类型，否则这样迭代读取会有错误
//		// System.out.println(content);
//		HSSFWorkbook rwb = null;
//		try {
//
//			rwb = new HSSFWorkbook(inputstream);
//			HSSFSheet st = rwb.getSheetAt(0);
//			if (st != null) {
//				ArrayList<WorkInfoDto> list = new ArrayList<WorkInfoDto>();
//				ArrayList<String> lables = new ArrayList<String>();
//				boolean first = true;
//				for (Iterator<Row> iter = st.rowIterator(); iter.hasNext();) {
//					Row row = iter.next();
//					if (first) {
//						for (Iterator<Cell> iter2 = row.cellIterator(); iter2
//								.hasNext();) {
//							Cell cell = iter2.next();
//							lables.add(cell.getStringCellValue());
//						}
//						first = false;
//					}
//
//					else {
//						int j = 0;
//						WorkInfoDto e = new WorkInfoDto();
//						for (Iterator<Cell> iter2 = row.cellIterator(); iter2
//								.hasNext();) {
//							Cell cell = iter2.next();
//							String val = "";
//							if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN)
//								val = String
//										.valueOf(cell.getBooleanCellValue());
//							else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
//								val = String.valueOf((int) cell
//										.getNumericCellValue());
//							else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
//								val = cell.getStringCellValue();
//							setEmployeeValue(lables.get(j++), val, e);
//						}
//						list.add(e);
//					}
//				}
//				return list;
//			}
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		} finally {
//			try {
//				inputstream.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return null;



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
							if (lables.get(j).toLowerCase().equals("id")&& exist(val))
							{
								System.out.println(lables.get(j) + " : " + val);
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
		for(WorkInfoDto dto : getAllList())
		{
			if(dto.getId().equals(id))
				return true;
		}
		return false;
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

	public boolean isDeleteMode() {
		return deleteMode;
	}

	public void setDeleteMode(boolean deleteMode) {
		this.deleteMode = deleteMode;
	}

	public List<WorkInfoDto> getAllList() {
		if (allList == null) {
			try {
				allList = workInfoService.getAllList(new DynamicQuery());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return allList == null ? new ArrayList<WorkInfoDto>() : allList;
	}

	public void setAllList(List<WorkInfoDto> allList) {
		this.allList = allList;
	}

	public static final String MATCH_MAIL = "([a-zA-Z0-9][a-zA-Z0-9_.+\\-]*)@(([a-zA-Z0-9][a-zA-Z0-9_\\-]+\\.)+[a-zA-Z]{2,6})";
	public static final String MATCH_PHONE = "([0-9]+)";


	public void emailValidate(FacesContext context, UIComponent component,
			Object value) {
		String text = value.toString();
		if (!text.matches(MATCH_MAIL)) {
			 throw new ValidatorException(new
			 FacesMessage(FacesMessage.SEVERITY_ERROR,"Invalid Email Format. Ex. allen@worksap.co.jp", null));
		}
	}
	public void phoneValidate(FacesContext context, UIComponent component,
			Object value) {
		String text = value.toString();
		if (!text.matches(MATCH_PHONE)) {
			 throw new ValidatorException(new
			 FacesMessage(FacesMessage.SEVERITY_ERROR,"Invalid Phone Format. Ex. 15500000000", null));
		}
	}
}
