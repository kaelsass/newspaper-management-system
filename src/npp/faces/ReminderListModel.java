package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.ReminderDto;

import org.primefaces.model.SelectableDataModel;


public class ReminderListModel extends ListDataModel<ReminderDto> implements
		SelectableDataModel<ReminderDto> {

	public ReminderListModel(List<ReminderDto> list) {
		super(list);
	}

	@Override
	public ReminderDto getRowData(String rowKey) {
		String seq = rowKey;
		List<ReminderDto> list = (List<ReminderDto>) getWrappedData();
		for (ReminderDto dto : list) {
			if (seq.equals(dto.getSeq()+"")) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(ReminderDto dto) {
		return dto.getSeq()+"";
	}

}
