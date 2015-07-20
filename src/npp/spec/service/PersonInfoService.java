package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.dto.PersonInfoDto;


public interface PersonInfoService {

	public void delete(String id) throws IOException;

	public void update(PersonInfoDto employeeDto) throws IOException;

	public void add(PersonInfoDto employeeDto) throws IOException;

	public List<PersonInfoDto> getAllList() throws IOException;

	public PersonInfoDto findByID(String id) throws IOException;

}
