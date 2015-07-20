package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.OccupationDto;

import org.primefaces.model.SelectableDataModel;


public class OccupationListModel extends ListDataModel<OccupationDto> implements
		SelectableDataModel<OccupationDto> {

	public OccupationListModel(List<OccupationDto> list) {
		super(list);
	}

	@Override
	public OccupationDto getRowData(String rowKey) {
		String seq = rowKey;
		List<OccupationDto> list = (List<OccupationDto>) getWrappedData();
		for (OccupationDto dto : list) {
			if (seq.equals(dto.getSeq()+"")) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(OccupationDto dto) {
		return dto.getSeq()+"";
	}

}
