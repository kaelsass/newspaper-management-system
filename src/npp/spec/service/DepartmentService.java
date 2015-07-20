package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.dto.DepartmentDto;

import org.primefaces.model.TreeNode;


public interface DepartmentService {

	public List<DepartmentDto> getAllList() throws IOException;
	public DepartmentDto findBySeq(int seq) throws IOException;
	public void update(DepartmentDto dto) throws IOException;
	void delete(int seq) throws IOException;
	void add(DepartmentDto dto) throws IOException;
	public DepartmentDto findByName(String name) throws IOException;
	public TreeNode getTree() throws IOException;
}
