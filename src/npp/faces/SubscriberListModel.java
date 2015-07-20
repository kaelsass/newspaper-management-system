package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.SubscriberDto;

import org.primefaces.model.SelectableDataModel;


public class SubscriberListModel extends ListDataModel<SubscriberDto> implements
		SelectableDataModel<SubscriberDto> {

	public SubscriberListModel(List<SubscriberDto> list) {
		super(list);
	}

	@Override
	public SubscriberDto getRowData(String rowKey) {
		String seq = rowKey;
		List<SubscriberDto> list = (List<SubscriberDto>) getWrappedData();
		for (SubscriberDto dto : list) {
			if (seq.equals(dto.getSeq() + "")) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(SubscriberDto dto) {
		return dto.getSeq()+"";
	}

}
