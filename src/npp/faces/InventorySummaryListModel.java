package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.InventorySummaryDto;

import org.primefaces.model.SelectableDataModel;


public class InventorySummaryListModel extends ListDataModel<InventorySummaryDto> implements
		SelectableDataModel<InventorySummaryDto> {

	public InventorySummaryListModel(List<InventorySummaryDto> list) {
		super(list);
	}

	@Override
	public InventorySummaryDto getRowData(String rowKey) {
		String seq = rowKey;
		List<InventorySummaryDto> list = (List<InventorySummaryDto>) getWrappedData();
		for (InventorySummaryDto dto : list) {
			if (seq.equals(dto.getNewspaper().getSeq()+"")) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(InventorySummaryDto dto) {
		return dto.getNewspaper().getSeq()+"";
	}

}
