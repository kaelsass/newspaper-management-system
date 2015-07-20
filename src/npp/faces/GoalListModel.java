package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.GoalDto;

import org.primefaces.model.SelectableDataModel;


public class GoalListModel extends ListDataModel<GoalDto> implements
		SelectableDataModel<GoalDto> {

	public GoalListModel(List<GoalDto> list) {
		super(list);
	}

	@Override
	public GoalDto getRowData(String rowKey) {
		String seq = rowKey;
		List<GoalDto> list = (List<GoalDto>) getWrappedData();
		for (GoalDto dto : list) {
			if (seq.equals(dto.getSeq()+"")) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(GoalDto dto) {
		return dto.getSeq()+"";
	}

}
