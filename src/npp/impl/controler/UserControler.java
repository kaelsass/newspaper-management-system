package npp.impl.controler;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import npp.condition.UserCondition;
import npp.condition.WorkInfoCondition;
import npp.dto.UserDto;
import npp.dto.WorkInfoDto;
import npp.faces.UserListModel;
import npp.spec.manager.SessionManager;
import npp.spec.service.UserService;
import npp.spec.service.WorkInfoService;
import npp.utils.PasswordHash;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Named
@SessionScoped
public class UserControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1826229747128429910L;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	private Conversation conversation;

	private List<UserDto> baseList = null;
	private UserListModel listModel = null;

	private List<UserDto> allUsers = null;
	private List<WorkInfoDto> allEmployees = null;

	private int first;

	private UserCondition condition;
	private UserDto editUser;

	private String password;
	private String passwordConfirm;

	private List<UserDto> selecteds;

	@Inject
	private UserService userService;
	@Inject
	private WorkInfoService workInfoService;
	@Inject
	private SessionManager sessionManager;

	private boolean editMode = false;

	private boolean addMode = false;

	private boolean deleteMode = false;

	private boolean changePassword = false;

	@PostConstruct
	public void init(){
		condition = new UserCondition();
		editUser = new UserDto();
		try {
			allUsers = userService.getAllList(condition.genQuery());
			allEmployees = workInfoService.getAllList(new WorkInfoCondition()
					.genQuery());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void reload() throws IOException {
		allUsers = userService.getAllList(condition.genQuery());
		allEmployees = workInfoService.getAllList(new WorkInfoCondition()
				.genQuery());
	}

	public UserListModel getListModel() {
		if (baseList == null) {
			try {
				baseList = userService.getAllList(condition.genQuery());
				listModel = new UserListModel(baseList);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		return baseList == null ? new UserListModel(new ArrayList<UserDto>())
				: listModel;
	}

	public void startAdd() {
		editUser = new UserDto();
		addMode = true;
		changePassword = true;
	}

	public void startEdit(ActionEvent event) {
		UserDto selectedUser = (UserDto) event.getComponent().getAttributes()
				.get("user");
		editUser = new UserDto(selectedUser);
		editMode = true;
	}
	public void search() throws IOException
	{
		clear();
		WorkInfoCondition wic = new WorkInfoCondition();
		wic.setName(condition.getEmployeeName());
		List<WorkInfoDto> list = workInfoService.getAllList(wic.genQuery());
		for(WorkInfoDto dto : list)
		{
			condition.getEmployees().add(dto);
		}
	}

	public void clear() {
		baseList = null;
		selecteds = null;
		first = 0;
		editUser = new UserDto();
		password = "";
		passwordConfirm = "";
		changePassword = false;
		condition.getEmployees().clear();
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public void delete() throws IOException {
		try {
			for (UserDto user : selecteds) {
				userService.delete(user.getUserName());
				int index = findUserData(user.getUserName());
				if (index >= 0) {
					baseList.remove(index);
				}
			}
			deleteMode = false;
		} catch (IOException e) {
			logger.error("User Date delete error.", e);
			sessionManager.addGlobalMessageFatal("User Data delete error.",
					null);
			throw e;
		}
	}

	public void apply() throws IOException {
		try {
			if (changePassword && (!password.equals(passwordConfirm))) {
				sessionManager.addGlobalMessageFatal(
						"Password is not equal to Confirm Password.", null);
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_FATAL,
						"Password and Confirm Password are not equal", null));
			}
			if (editMode) {
				if (changePassword)
					editUser.setPassword(PasswordHash
							.makePasswordHash(password));
				userService.update(editUser);

			} else {
				editUser.setEmployee(findEmployeeByName(editUser.getEmployee()
						.getName()));
				editUser.setPassword(PasswordHash.makePasswordHash(password));
				userService.add(editUser);
			}
			clear();
			reload();
		} catch (IOException e) {
			logger.error("User Date update error.", e);
			sessionManager
					.addGlobalMessageFatal(e.getMessage(), e.getMessage());
			throw e;
		}
	}

	private WorkInfoDto findEmployeeByName(String name) {
		for (WorkInfoDto dto : allEmployees) {
			if (dto.getName().equals(name))
				return dto;
		}
		return null;
	}

	private int findUserData(String name) {
		for (int i = 0; i < baseList.size(); i++) {
			UserDto userDto = baseList.get(i);
			if (userDto.getUserName().equals(name)) {
				return i;
			}
		}
		return -1;
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

	public void conversationBegin() {
		if (conversation.isTransient()) {
			conversation.begin();
		}
	}

	public void conversationEnd() {
		if (!conversation.isTransient()) {
			conversation.end();
		}
	}

	public List<UserDto> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<UserDto> selecteds) {
		this.selecteds = selecteds;
	}

	public UserCondition getCondition() {
		return condition;
	}

	public void setCondition(UserCondition condition) {
		this.condition = condition;
	}

	public UserDto getEditUser() {
		return editUser;
	}

	public void setEditUser(UserDto editUser) {
		this.editUser = editUser;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public boolean isAddMode() {
		return addMode;
	}

	public void setAddMode(boolean addMode) {
		this.addMode = addMode;
	}

	public boolean isChangePassword() {
		return changePassword;
	}

	public void setChangePassword(boolean changePassword) {
		this.changePassword = changePassword;
	}

	public List<String> completeUserName(String query) {
		ArrayList<String> filterList = new ArrayList<String>();
		for (UserDto dto : allUsers) {
			if (dto.getUserName().toLowerCase().contains(query.toLowerCase()))
				filterList.add(dto.getUserName());
		}
		return filterList;
	}

	public List<String> completeEmployeeName(String query) {
		ArrayList<String> filterList = new ArrayList<String>();
		for (WorkInfoDto dto : allEmployees) {
			if (dto.getName().toLowerCase().contains(query.toLowerCase()))
				filterList.add(dto.getName());
		}
		return filterList;
	}

	// public void passwordValidator(FacesContext context, UIComponent
	// component,
	// Object value) {
	// if (password == null || passwordConfirm == null)
	// return;
	// System.out.println("password: " + password);
	// System.out.println("confirm: " + passwordConfirm);
	// if (!passwordConfirm.equals(password)) {
	// sessionManager.addGlobalMessageFatal(
	// "Password and Confirm Password are not equal", null);
	// throw new ValidatorException(new FacesMessage(
	// FacesMessage.SEVERITY_FATAL,
	// "Password and Confirm Password are not equal", null));
	// }
	// }

	public void nameValidate(FacesContext context, UIComponent component,
			Object value) {

		String userName = (String) value;
		for (UserDto dto : allUsers) {
			if (dto.getUserName().equals(userName)) {
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "User Name has existed.",
						null));
			}
		}
	}


	public boolean isDeleteMode() {
		return deleteMode;
	}

	public void setDeleteMode(boolean deleteMode) {
		this.deleteMode = deleteMode;
	}

	public void startDelete()
	{
		deleteMode = true;
	}
	public void endDelete()
	{
		deleteMode = false;
	}

	public List<String> completeEmployeeID(String query) {
		ArrayList<String> filterList = new ArrayList<String>();
		for (WorkInfoDto dto : allEmployees) {
			if (dto.getId().contains(query))
				filterList.add(dto.getId());
		}
		return filterList;
	}
	public void idValidate(FacesContext context, UIComponent component,
			Object value) {
		String id = (String) value;
		for (WorkInfoDto dto : allEmployees) {
			if (dto.getId().equals(id)) {
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_FATAL,
						"Employee ID has existed.", null));
			}
		}

	}
	public void userNameValidate(FacesContext context, UIComponent component,
			Object value) {
		String name = (String) value;
		for (UserDto dto : allUsers) {
			if (dto.getUserName().equals(name)) {
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_FATAL,
						"User Name has existed.", null));
			}
		}
	}
}
