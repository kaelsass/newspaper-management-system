package npp.spec.dao;

import java.io.IOException;

import npp.dto.SupervisorDto;


public interface SupervisorDao {

	SupervisorDto findByID(Transaction transaction, String id) throws IOException;

}
