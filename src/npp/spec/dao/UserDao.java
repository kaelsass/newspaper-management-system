package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.condition.DynamicQuery;
import npp.dto.UserDto;


public interface UserDao {
	public UserDto findBySeq(Transaction transaction, String id) throws IOException;
	public List<UserDto> getAllList(Transaction transaction, DynamicQuery query) throws IOException;
	public void add(Transaction transaction, UserDto userDto) throws IOException;
	public void update(Transaction transaction, UserDto userDto) throws IOException;
	public void delete(Transaction transaction, String id) throws IOException;
}
