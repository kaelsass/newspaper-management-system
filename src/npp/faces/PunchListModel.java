package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.PunchDto;

import org.primefaces.model.SelectableDataModel;


public class PunchListModel extends ListDataModel<PunchDto> implements
		SelectableDataModel<PunchDto> {

	public PunchListModel(List<PunchDto> list) {
		super(list);
	}

	@Override
	public PunchDto getRowData(String rowKey) {
		String seq = rowKey;
		List<PunchDto> list = (List<PunchDto>) getWrappedData();
		for (PunchDto dto : list) {
			if (seq.equals(dto.getSeq() + "")) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(PunchDto dto) {
		return dto.getSeq()+"";
	}

}
