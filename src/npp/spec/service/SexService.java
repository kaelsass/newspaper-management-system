package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.dto.SexDto;


public interface SexService {

	public List<SexDto> getAllList() throws IOException;
	public SexDto findSex(String name) throws IOException;
}
