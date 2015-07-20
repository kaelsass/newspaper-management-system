package npp.impl.controler;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import npp.dto.RoleDto;
import npp.faces.RoleListModel;
import npp.spec.service.RoleService;


@Named
@SessionScoped
@RolesAllowed("admin")
public class RoleControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1826229747128429910L;

	private List<RoleDto> baseList = null;
	private RoleListModel listModel = null;

	private int first;


	private List<RoleDto> selecteds;

	@Inject
	private RoleService roleService;

	public RoleListModel getListModel() {
		if (baseList == null) {
			try {
				baseList = roleService.getAllList();
				listModel = new RoleListModel(baseList);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		return baseList == null ? new RoleListModel(new ArrayList<RoleDto>())
				: listModel;
	}
	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	// public void apply() throws IOException {
	// try {
	// if (roleSelection.length < 1) {
	// sessionManager.addGlobalMessageFatal(
	// dictionary.get("Role is required."), null);
	// return;
	// }
	// editUser.getRoleList().clear();
	// for (RoleDto roleDto : roleSelection) {
	// editUser.getRoleList().add(roleDto);
	// }
	// if (editUser.isChangePassword()
	// && (editUser.getPlanePassword() == null || editUser
	// .getPlanePassword().equals(""))) {
	// sessionManager.addGlobalMessageFatal(
	// dictionary.get("Password is required."), null);
	// return;
	// }
	// if (editMode) {
	// userService.updateUser(editUser);
	// replaceUserData(editUser);
	// } else {
	// if (!editUser.isChangePassword()) {
	// sessionManager.addGlobalMessageFatal(
	// dictionary.get("Password is required."), null);
	// return;
	// }
	// userService.addUser(editUser);
	// baseList.add(new UserDto(editUser.getId(), "", editUser
	// .getFirstName(), editUser.getLastName(), editUser
	// .getPhone(), editUser.getEmail(), departmentService
	// .findBySeq(editUser.getDepartmentSeq()), editUser
	// .getRoleList()));
	// }
	// editUser = null;
	// editMode = false;
	// addMode = false;
	// } catch (IOException e) {
	// logger.error("User Date update error.", e);
	// sessionManager.addGlobalMessageFatal("User Data update error.",
	// null);
	// throw e;
	// }
	// }

	// public void applyPassword() {
	//
	// if (password.equals(passwordConfirm)) {
	// editUser.setPlanePassword(password);
	// passwordEdit = false;
	// } else {
	// sessionManager.addGlobalMessageFatal("Password Change Error.",
	// dictionary.get("passwordConfirmNotEqual"));
	// }
	// }
	//
	// public void discardPassword() {
	// passwordEdit = false;
	// }
	//
	// public WrappedUserDto getEditUser() {
	// return editUser;
	// }
	//
	// public void setEditUser(WrappedUserDto editUser) {
	// this.editUser = editUser;
	// }


	public List<RoleDto> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<RoleDto> selecteds) {
		this.selecteds = selecteds;
	}

	public List<RoleDto> getBaseList() throws IOException {
		if(baseList == null)
			baseList = roleService.getAllList();
		return baseList;
	}

	public void setBaseList(List<RoleDto> baseList) {
		this.baseList = baseList;
	}

}
