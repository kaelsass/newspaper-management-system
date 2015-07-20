package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.PurposeDto;

import org.primefaces.model.SelectableDataModel;


public class PurposeListModel extends ListDataModel<PurposeDto> implements
		SelectableDataModel<PurposeDto> {

	public PurposeListModel(List<PurposeDto> list) {
		super(list);
	}

	@Override
	public PurposeDto getRowData(String rowKey) {
		String seq = rowKey;
		List<PurposeDto> list = (List<PurposeDto>) getWrappedData();
		for (PurposeDto dto : list) {
			if (seq.equals(dto.getSeq() + "")) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(PurposeDto dto) {
		return dto.getSeq()+"";
	}

}
