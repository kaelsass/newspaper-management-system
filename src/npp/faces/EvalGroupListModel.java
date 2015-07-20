package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.EvalGroupDto;

import org.primefaces.model.SelectableDataModel;


public class EvalGroupListModel extends ListDataModel<EvalGroupDto> implements
		SelectableDataModel<EvalGroupDto> {

	public EvalGroupListModel(List<EvalGroupDto> list) {
		super(list);
	}

	@Override
	public EvalGroupDto getRowData(String rowKey) {
		String seq = rowKey;
		List<EvalGroupDto> list = (List<EvalGroupDto>) getWrappedData();
		for (EvalGroupDto dto : list) {
			if (seq.equals(dto.getSeq()+"")) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(EvalGroupDto dto) {
		return dto.getSeq()+"";
	}

}
