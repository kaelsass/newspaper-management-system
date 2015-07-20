package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.AdDto;
import npp.dto.TaskDto;

import org.primefaces.model.SelectableDataModel;


public class TaskListModel extends ListDataModel<TaskDto> implements
		SelectableDataModel<TaskDto> {

	public TaskListModel(List<TaskDto> list) {
		super(list);
	}

	@Override
	public TaskDto getRowData(String rowKey) {
		String seq = rowKey;
		List<TaskDto> list = (List<TaskDto>) getWrappedData();
		for (TaskDto dto : list) {
			if (seq.equals(dto.getSeq() +"")) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(TaskDto dto) {
		return dto.getSeq() + "";
	}

}
