package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.EducationDto;

import org.primefaces.model.SelectableDataModel;


public class EducationListModel extends ListDataModel<EducationDto> implements
		SelectableDataModel<EducationDto> {

	public EducationListModel(List<EducationDto> list) {
		super(list);
	}

	@Override
	public EducationDto getRowData(String rowKey) {
		String seq = rowKey;
		List<EducationDto> list = (List<EducationDto>) getWrappedData();
		for (EducationDto dto : list) {
			if (seq.equals(dto.getSeq()+"")) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(EducationDto dto) {
		return dto.getSeq()+"";
	}

}
