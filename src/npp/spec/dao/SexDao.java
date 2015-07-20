package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.dto.SexDto;


public interface SexDao {
	public List<SexDto> getAllList(Transaction transaction) throws IOException;
	public SexDto findSex(Transaction transaction, String name) throws IOException;
}
