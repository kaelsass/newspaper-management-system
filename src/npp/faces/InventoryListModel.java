package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.InventoryDto;

import org.primefaces.model.SelectableDataModel;


public class InventoryListModel extends ListDataModel<InventoryDto> implements
		SelectableDataModel<InventoryDto> {

	public InventoryListModel(List<InventoryDto> list) {
		super(list);
	}

	@Override
	public InventoryDto getRowData(String rowKey) {
		String seq = rowKey;
		List<InventoryDto> list = (List<InventoryDto>) getWrappedData();
		for (InventoryDto dto : list) {
			if (seq.equals(dto.getIssue().getSeq())) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(InventoryDto dto) {
		return dto.getIssue().getSeq();
	}

}
