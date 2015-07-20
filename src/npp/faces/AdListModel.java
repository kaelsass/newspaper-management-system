package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.AdDto;

import org.primefaces.model.SelectableDataModel;


public class AdListModel extends ListDataModel<AdDto> implements
		SelectableDataModel<AdDto> {

	public AdListModel(List<AdDto> list) {
		super(list);
	}

	@Override
	public AdDto getRowData(String rowKey) {
		String seq = rowKey;
		List<AdDto> list = (List<AdDto>) getWrappedData();
		for (AdDto dto : list) {
			if (seq.equals(dto.getID())) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(AdDto dto) {
		return dto.getID();
	}

}
