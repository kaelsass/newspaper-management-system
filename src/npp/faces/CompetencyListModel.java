package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.CompetencyDto;

import org.primefaces.model.SelectableDataModel;


public class CompetencyListModel extends ListDataModel<CompetencyDto> implements
		SelectableDataModel<CompetencyDto> {

	public CompetencyListModel(List<CompetencyDto> list) {
		super(list);
	}

	@Override
	public CompetencyDto getRowData(String rowKey) {
		String seq = rowKey;
		List<CompetencyDto> list = (List<CompetencyDto>) getWrappedData();
		for (CompetencyDto dto : list) {
			if (seq.equals(dto.getSeq()+"")) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(CompetencyDto dto) {
		return dto.getSeq()+"";
	}

}
