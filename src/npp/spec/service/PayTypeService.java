package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.dto.PayTypeDto;


public interface PayTypeService {

	public PayTypeDto findBySeq(int seq) throws IOException;

	public List<PayTypeDto> getAllList() throws IOException;

	public void add(PayTypeDto dto) throws IOException;

	public void update(PayTypeDto dto) throws IOException;

	public void delete(int seq) throws IOException;

}
