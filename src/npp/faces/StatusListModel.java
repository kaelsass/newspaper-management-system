package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.StatusDto;

import org.primefaces.model.SelectableDataModel;


public class StatusListModel extends ListDataModel<StatusDto> implements
		SelectableDataModel<StatusDto> {

	public StatusListModel(List<StatusDto> list) {
		super(list);
	}

	@Override
	public StatusDto getRowData(String rowKey) {
		String seq = rowKey;
		List<StatusDto> departmentList = (List<StatusDto>) getWrappedData();
		for (StatusDto dto : departmentList) {
			if (seq.equals(dto.getSeq() + "")) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(StatusDto dto) {
		return dto.getSeq()+"";
	}

}
