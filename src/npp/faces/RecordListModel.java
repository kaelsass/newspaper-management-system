package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.RecordDto;

import org.primefaces.model.SelectableDataModel;


public class RecordListModel extends ListDataModel<RecordDto> implements
		SelectableDataModel<RecordDto> {

	public RecordListModel(List<RecordDto> list) {
		super(list);
	}

	@Override
	public RecordDto getRowData(String rowKey) {
		String seq = rowKey;
		List<RecordDto> list = (List<RecordDto>) getWrappedData();
		for (RecordDto dto : list) {
			if (seq.equals(dto.getSeq()+"")) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(RecordDto dto) {
		return dto.getSeq() + "";
	}

}
