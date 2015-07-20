package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.ReturnDto;

import org.primefaces.model.SelectableDataModel;


public class ReturnListModel extends ListDataModel<ReturnDto> implements
		SelectableDataModel<ReturnDto> {

	public ReturnListModel(List<ReturnDto> list) {
		super(list);
	}

	@Override
	public ReturnDto getRowData(String rowKey) {
		String seq = rowKey;
		List<ReturnDto> list = (List<ReturnDto>) getWrappedData();
		for (ReturnDto dto : list) {
			if (seq.equals(dto.getSeq() + "")) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(ReturnDto dto) {
		return dto.getSeq()+"";
	}

}
