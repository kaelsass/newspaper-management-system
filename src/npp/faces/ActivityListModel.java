package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.ActivityDto;

import org.primefaces.model.SelectableDataModel;


public class ActivityListModel extends ListDataModel<ActivityDto> implements
		SelectableDataModel<ActivityDto> {

	public ActivityListModel(List<ActivityDto> list) {
		super(list);
	}

	@Override
	public ActivityDto getRowData(String rowKey) {
		String seq = rowKey;
		List<ActivityDto> list = (List<ActivityDto>) getWrappedData();
		for (ActivityDto dto : list) {
			if (seq.equals(dto.getSeq()+"")) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(ActivityDto dto) {
		return dto.getSeq()+"";
	}

}
