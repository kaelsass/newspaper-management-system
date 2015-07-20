package npp.spec.dao;

import java.io.IOException;
import java.util.List;

import npp.dto.AppraisalRecord;

public interface AppraisalRecordDao {

	List<AppraisalRecord> getAllList(Transaction transaction, int appraisalSeq)
			throws IOException;

}
