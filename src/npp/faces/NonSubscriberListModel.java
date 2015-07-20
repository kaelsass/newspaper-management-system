package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.NonSubscriberDto;

import org.primefaces.model.SelectableDataModel;


public class NonSubscriberListModel extends ListDataModel<NonSubscriberDto> implements
		SelectableDataModel<NonSubscriberDto> {

	public NonSubscriberListModel(List<NonSubscriberDto> list) {
		super(list);
	}

	@Override
	public NonSubscriberDto getRowData(String rowKey) {
		String seq = rowKey;
		List<NonSubscriberDto> list = (List<NonSubscriberDto>) getWrappedData();
		for (NonSubscriberDto dto : list) {
			if (seq.equals(dto.getSeq() + "")) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(NonSubscriberDto dto) {
		return dto.getSeq()+"";
	}

}
