package npp.spec.service;

import java.io.IOException;
import java.util.List;

import npp.dto.AppraisalRecord;

public interface AppraisalRecordService {

	List<AppraisalRecord> getAllList(int appraisalSeq) throws IOException;

}
