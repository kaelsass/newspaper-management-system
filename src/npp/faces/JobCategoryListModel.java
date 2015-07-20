package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.JobCategoryDto;

import org.primefaces.model.SelectableDataModel;


public class JobCategoryListModel extends ListDataModel<JobCategoryDto> implements
		SelectableDataModel<JobCategoryDto> {

	public JobCategoryListModel(List<JobCategoryDto> list) {
		super(list);
	}

	@Override
	public JobCategoryDto getRowData(String rowKey) {
		String seq = rowKey;
		List<JobCategoryDto> list = (List<JobCategoryDto>) getWrappedData();
		for (JobCategoryDto dto : list) {
			if (seq.equals(dto.getSeq() + "")) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(JobCategoryDto dto) {
		return dto.getSeq()+"";
	}

}
