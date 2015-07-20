package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.dto.DepartmentDto;

import org.primefaces.model.TreeNode;


public interface DepartmentDao {
	public List<DepartmentDto> getAllList(Transaction transaction) throws IOException;
	public DepartmentDto findBySeq(Transaction transaction, int departmentSeq) throws IOException;
	public void add(Transaction transaction, DepartmentDto departmentDto) throws IOException;
	public void updateDepartment(Transaction transaction, DepartmentDto departmentDto) throws IOException;
	public void delete(Transaction transaction, int departmentSeq)  throws IOException;
	public DepartmentDto findDepartmentByName(Transaction transaction,
			String name) throws IOException;
	public TreeNode getTree(Transaction transaction) throws IOException;
}
