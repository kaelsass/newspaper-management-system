package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.SubjectDto;

import org.primefaces.model.SelectableDataModel;


public class SubjectListModel extends ListDataModel<SubjectDto> implements
		SelectableDataModel<SubjectDto> {

	public SubjectListModel(List<SubjectDto> list) {
		super(list);
	}

	@Override
	public SubjectDto getRowData(String rowKey) {
		String seq = rowKey;
		List<SubjectDto> list = (List<SubjectDto>) getWrappedData();
		for (SubjectDto dto : list) {
			if (seq.equals(dto.getSeq() + "")) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(SubjectDto dto) {
		return dto.getSeq()+"";
	}

}
