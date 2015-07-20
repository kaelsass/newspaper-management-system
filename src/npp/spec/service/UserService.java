package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.UserDto;


public interface UserService {
	public UserDto findById(String id) throws IOException;
	public void add(UserDto userDto) throws IOException;
	public void update(UserDto userDto) throws IOException;
	public void delete(String id) throws IOException;
	public List<UserDto> getAllList(DynamicQuery query) throws IOException;
}
