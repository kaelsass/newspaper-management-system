package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.dto.RoleDto;


public interface RoleService {

	public List<RoleDto> getAllList() throws IOException;
}
