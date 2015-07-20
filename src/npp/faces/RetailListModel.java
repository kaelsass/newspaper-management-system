package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.RetailDto;

import org.primefaces.model.SelectableDataModel;


public class RetailListModel extends ListDataModel<RetailDto> implements
		SelectableDataModel<RetailDto> {

	public RetailListModel(List<RetailDto> list) {
		super(list);
	}

	@Override
	public RetailDto getRowData(String rowKey) {
		String seq = rowKey;
		List<RetailDto> list = (List<RetailDto>) getWrappedData();
		for (RetailDto dto : list) {
			if (seq.equals(dto.getSeq() + "")) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(RetailDto dto) {
		return dto.getSeq()+"";
	}

}
