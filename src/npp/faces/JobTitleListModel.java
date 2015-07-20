package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.JobTitleDto;

import org.primefaces.model.SelectableDataModel;


public class JobTitleListModel extends ListDataModel<JobTitleDto> implements
		SelectableDataModel<JobTitleDto> {

	public JobTitleListModel(List<JobTitleDto> list) {
		super(list);
	}

	@Override
	public JobTitleDto getRowData(String rowKey) {
		String seq = rowKey;
		List<JobTitleDto> list = (List<JobTitleDto>) getWrappedData();
		for (JobTitleDto dto : list) {
			if (seq.equals(dto.getSeq() + "")) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(JobTitleDto dto) {
		return dto.getSeq()+"";
	}

}
