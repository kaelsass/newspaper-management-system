package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.QuestionnaireDto;
import npp.dto.QuestionnaireDto;

import org.primefaces.model.SelectableDataModel;


public class QuestionnaireListModel extends ListDataModel<QuestionnaireDto> implements
		SelectableDataModel<QuestionnaireDto> {

	public QuestionnaireListModel(List<QuestionnaireDto> list) {
		super(list);
	}

	@Override
	public QuestionnaireDto getRowData(String rowKey) {
		String seq = rowKey;
		List<QuestionnaireDto> list = (List<QuestionnaireDto>) getWrappedData();
		for (QuestionnaireDto dto : list) {
			if (seq.equals(dto.getSeq() + "")) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(QuestionnaireDto dto) {
		return dto.getSeq() + "";
	}

}
