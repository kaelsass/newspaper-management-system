package npp.impl.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;

import npp.dto.DepartmentDto;
import npp.spec.dao.DepartmentDao;
import npp.spec.dao.Transaction;
import npp.spec.service.DepartmentService;

import org.primefaces.model.TreeNode;


public class DepartmentServiceImpl implements DepartmentService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6952156154353886912L;
	private Transaction transaction;
	private DepartmentDao departmentDao;

	@Inject
	public DepartmentServiceImpl(Transaction transaction,DepartmentDao departmentDao){
		this.transaction = transaction;
		this.departmentDao = departmentDao;
	}

	@Override
	public TreeNode getTree() throws IOException {
		try{
			transaction.begin();
			TreeNode root = departmentDao.getTree(transaction);
			transaction.commit();
			return root;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public List<DepartmentDto> getAllList() throws IOException {
		try{
			transaction.begin();
			List<DepartmentDto> departmentList = departmentDao.getAllList(transaction);
			transaction.commit();
			return departmentList;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}
	@Override
	public DepartmentDto findBySeq(int departmentSeq) throws IOException {
		try{
			transaction.begin();
			DepartmentDto departmentDto = departmentDao.findBySeq(transaction, departmentSeq);
			transaction.commit();
			return departmentDto;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}


	@Override
	@RolesAllowed({"admin", "add"})
	public void add(DepartmentDto departmentDto) throws IOException {
		try{
			transaction.begin();
			departmentDao.add(transaction, departmentDto);
			transaction.commit();
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	@RolesAllowed({"admin", "update"})
	public void update(DepartmentDto departmentDto) throws IOException {
		try{
			transaction.begin();
			departmentDao.updateDepartment(transaction, departmentDto);
			transaction.commit();
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}

	@Override
	@RolesAllowed({"admin", "delete"})
	public void delete(int departmentSeq) throws IOException {
		try{
			transaction.begin();
			departmentDao.delete(transaction, departmentSeq);
			transaction.commit();
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}
	@Override
	public DepartmentDto findByName(String name) throws IOException {
		try{
			transaction.begin();
			DepartmentDto departmentDto = departmentDao.findDepartmentByName(transaction, name);
			transaction.commit();
			return departmentDto;
		}catch(IOException e){
			transaction.rollback();
			throw e;
		}
	}


}
