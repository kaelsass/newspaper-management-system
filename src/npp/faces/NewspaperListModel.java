package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.NewspaperDto;

import org.primefaces.model.SelectableDataModel;


public class NewspaperListModel extends ListDataModel<NewspaperDto> implements
		SelectableDataModel<NewspaperDto> {

	public NewspaperListModel(List<NewspaperDto> list) {
		super(list);
	}

	@Override
	public NewspaperDto getRowData(String rowKey) {
		String seq = rowKey;
		List<NewspaperDto> list = (List<NewspaperDto>) getWrappedData();
		for (NewspaperDto dto : list) {
			if (seq.equals(dto.getSeq() + "")) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(NewspaperDto dto) {
		return dto.getSeq()+"";
	}

}
