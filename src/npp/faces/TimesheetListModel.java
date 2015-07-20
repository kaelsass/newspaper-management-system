package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.TimesheetDto;

import org.primefaces.model.SelectableDataModel;


public class TimesheetListModel extends ListDataModel<TimesheetDto> implements
		SelectableDataModel<TimesheetDto> {

	public TimesheetListModel(List<TimesheetDto> list) {
		super(list);
	}

	@Override
	public TimesheetDto getRowData(String rowKey) {
		String seq = rowKey;
		List<TimesheetDto> list = (List<TimesheetDto>) getWrappedData();
		for (TimesheetDto dto : list) {
			if (seq.equals(dto.getSeq()+"")) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(TimesheetDto dto) {
		return dto.getSeq()+"";
	}

}
