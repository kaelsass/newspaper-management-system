package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.TrackerLogDto;

import org.primefaces.model.SelectableDataModel;


public class TrackerLogListModel extends ListDataModel<TrackerLogDto> implements
		SelectableDataModel<TrackerLogDto> {

	public TrackerLogListModel(List<TrackerLogDto> list) {
		super(list);
	}

	@Override
	public TrackerLogDto getRowData(String rowKey) {
		String seq = rowKey;
		List<TrackerLogDto> list = (List<TrackerLogDto>) getWrappedData();
		for (TrackerLogDto dto : list) {
			if (seq.equals(dto.getSeq()+"")) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(TrackerLogDto dto) {
		return dto.getSeq() + "";
	}

}
