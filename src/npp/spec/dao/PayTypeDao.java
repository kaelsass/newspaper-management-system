package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.dto.PayTypeDto;


public interface PayTypeDao {

	public List<PayTypeDto> getAllList(Transaction transaction) throws IOException;

	public PayTypeDto findBySeq(Transaction transaction, int seq)
			throws IOException;

	public void add(Transaction transaction, PayTypeDto dto) throws IOException;

	public void update(Transaction transaction, PayTypeDto dto) throws IOException;

	public void delete(Transaction transaction, int seq) throws IOException;

}
