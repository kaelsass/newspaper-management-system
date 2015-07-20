package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.SectionDto;

import org.primefaces.model.SelectableDataModel;


public class SectionListModel extends ListDataModel<SectionDto> implements
		SelectableDataModel<SectionDto> {

	public SectionListModel(List<SectionDto> list) {
		super(list);
	}

	@Override
	public SectionDto getRowData(String rowKey) {
		String seq = rowKey;
		List<SectionDto> list = (List<SectionDto>) getWrappedData();
		for (SectionDto dto : list) {
			if (seq.equals(dto.getSeq() + "")) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(SectionDto dto) {
		return dto.getSeq()+"";
	}

}
