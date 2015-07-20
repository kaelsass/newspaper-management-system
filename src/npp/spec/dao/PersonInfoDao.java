package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.dto.PersonInfoDto;


public interface PersonInfoDao {

	public void delete(Transaction transaction, String id)throws IOException;

	public void update(Transaction transaction, PersonInfoDto employeeDto)throws IOException;

	public void add(Transaction transaction, PersonInfoDto employeeDto)throws IOException;

	public List<PersonInfoDto> getAllList(Transaction transaction)throws IOException;

	public PersonInfoDto findByID(Transaction transaction, String id)throws IOException;

}
