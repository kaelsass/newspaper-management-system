package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.dto.RoleDto;


public interface RoleDao {
	public List<RoleDto> getAllList(Transaction transaction) throws IOException;
	public void add(Transaction transaction, RoleDto roleDto) throws IOException;
	public void delete(Transaction transaction, int roleid) throws IOException;
	public RoleDto findByID(Transaction transaction, int roleid) throws IOException;
}
