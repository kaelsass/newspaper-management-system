package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.OfficeDto;

import org.primefaces.model.SelectableDataModel;


public class OfficeListModel extends ListDataModel<OfficeDto> implements
		SelectableDataModel<OfficeDto> {

	public OfficeListModel(List<OfficeDto> list) {
		super(list);
	}

	@Override
	public OfficeDto getRowData(String rowKey) {
		String seq = rowKey;
		List<OfficeDto> list = (List<OfficeDto>) getWrappedData();
		for (OfficeDto dto : list) {
			if (seq.equals(dto.getSeq() + "")) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(OfficeDto dto) {
		return dto.getSeq()+"";
	}

}
