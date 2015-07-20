package npp.impl.controler;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import npp.dto.DepartmentDto;
import npp.spec.service.DepartmentService;

import org.primefaces.model.TreeNode;


@Named
@SessionScoped
public class StructureControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 469282104365892582L;

	private TreeNode root;
	private boolean editMode = false;;
	@Inject
	private DepartmentService departmentService;

	private DepartmentDto editDepartment;

	private boolean addMode = false;

	private boolean editStructureMode = false;
	private boolean deleteMode = false;

	private DepartmentDto addDepartment;


    public TreeNode getRoot() {
    	if(root == null)
			try {
				root = departmentService.getTree();
				root.setExpanded(true);
				setChildrenExpanded(root);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        return root;
    }

	private void setChildrenExpanded(TreeNode root) {
		if(root == null)
			return;
		List<TreeNode> list = root.getChildren();
		for(TreeNode n : list)
		{
			n.setExpanded(true);
			setChildrenExpanded(n);
		}
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public void startAdd(DepartmentDto dto)
	{
		editDepartment = dto;
		addDepartment = new DepartmentDto(0, "", "", dto.getSeq());
		addMode  = true;
		editMode = false;
	}

	public void startEdit(DepartmentDto dto)
	{
		editDepartment = dto;
		editMode = true;
		addMode = false;
	}

	public void startDelete(DepartmentDto dto)
	{
		deleteMode = true;
		editDepartment = dto;
	}
	public void editStructure()
	{
		editStructureMode = true;
	}
	public void finishEditStructure()
	{
		editStructureMode = false;
	}

	public void apply() throws IOException
	{
		if(editMode)
		{
			departmentService.update(editDepartment);
		}
		else if(addMode)
		{
			departmentService.add(addDepartment);
		}
		clear();
	}

	public void delete() throws IOException
	{
		departmentService.delete(editDepartment.getSeq());
		clear();
	}

	public void clear()
	{
		root = null;
		addMode = false;
		editMode = false;
		editDepartment = null;
		addDepartment = null;
		deleteMode = false;
	}
	public void clearEditPart()
	{
		addMode = false;
		editMode = false;
		editDepartment = null;
		addDepartment = null;
		deleteMode = false;
	}
	public boolean isBrowseMode()
	{
		return !addMode && !editMode;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public DepartmentDto getEditDepartment() {
		return editDepartment;
	}

	public void setEditDepartment(DepartmentDto editDepartment) {
		this.editDepartment = editDepartment;
	}

	public boolean isAddMode() {
		return addMode;
	}

	public void setAddMode(boolean addMode) {
		this.addMode = addMode;
	}

	public boolean isEditStructureMode() {
		return editStructureMode;
	}

	public void setEditStructureMode(boolean editStructureMode) {
		this.editStructureMode = editStructureMode;
	}

	public DepartmentDto getAddDepartment() {
		return addDepartment;
	}

	public void setAddDepartment(DepartmentDto addDepartment) {
		this.addDepartment = addDepartment;
	}

	public boolean isDeleteMode() {
		return deleteMode;
	}

	public void setDeleteMode(boolean deleteMode) {
		this.deleteMode = deleteMode;
	}
}
