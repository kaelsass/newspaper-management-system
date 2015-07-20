package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.AdDto;
import npp.dto.TrackerDto;

import org.primefaces.model.SelectableDataModel;


public class TrackerListModel extends ListDataModel<TrackerDto> implements
		SelectableDataModel<TrackerDto> {

	public TrackerListModel(List<TrackerDto> list) {
		super(list);
	}

	@Override
	public TrackerDto getRowData(String rowKey) {
		String seq = rowKey;
		List<TrackerDto> list = (List<TrackerDto>) getWrappedData();
		for (TrackerDto dto : list) {
			if (seq.equals(dto.getSeq()+"")) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(TrackerDto dto) {
		return dto.getSeq()+"";
	}

}
