package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.TimesheetPerWeekDto;

import org.primefaces.model.SelectableDataModel;


public class TimesheetPerWeekListModel extends ListDataModel<TimesheetPerWeekDto> implements
		SelectableDataModel<TimesheetPerWeekDto> {

	public TimesheetPerWeekListModel(List<TimesheetPerWeekDto> list) {
		super(list);
	}

	@Override
	public TimesheetPerWeekDto getRowData(String rowKey) {
		String seq = rowKey;
		List<TimesheetPerWeekDto> list = (List<TimesheetPerWeekDto>) getWrappedData();
		for (TimesheetPerWeekDto dto : list) {
			if (seq.equals(dto.getSeq()+"")) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(TimesheetPerWeekDto dto) {
		return dto.getSeq()+"";
	}

}
