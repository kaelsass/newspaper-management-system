package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.QuestionDto;

import org.primefaces.model.SelectableDataModel;


public class QuestionListModel extends ListDataModel<QuestionDto> implements
		SelectableDataModel<QuestionDto> {

	public QuestionListModel(List<QuestionDto> list) {
		super(list);
	}

	@Override
	public QuestionDto getRowData(String rowKey) {
		String seq = rowKey;
		List<QuestionDto> list = (List<QuestionDto>) getWrappedData();
		for (QuestionDto dto : list) {
			if (seq.equals(dto.getSeq() + "")) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(QuestionDto dto) {
		return dto.getSeq() + "";
	}

}
