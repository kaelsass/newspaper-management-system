package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.dto.PurposeDto;
import npp.spec.dao.Transaction;


public interface PurposeDao {

	public List<PurposeDto> getAllList(Transaction transaction) throws IOException;

	public PurposeDto findBySeq(Transaction transaction, int seq) throws IOException;

	public void add(Transaction transaction, PurposeDto dto) throws IOException;

	public void update(Transaction transaction, PurposeDto dto) throws IOException;

	public void delete(Transaction transaction, int seq) throws IOException;

}
