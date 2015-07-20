package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.EventDto;

import org.primefaces.model.SelectableDataModel;


public class EventListModel extends ListDataModel<EventDto> implements
		SelectableDataModel<EventDto> {

	public EventListModel(List<EventDto> list) {
		super(list);
	}

	@Override
	public EventDto getRowData(String rowKey) {
		String seq = rowKey;
		List<EventDto> list = (List<EventDto>) getWrappedData();
		for (EventDto dto : list) {
			if (seq.equals(dto.getId())) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(EventDto dto) {
		return dto.getId();
	}

}
