package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.AqDto;

import org.primefaces.model.SelectableDataModel;


public class AqListModel extends ListDataModel<AqDto> implements
		SelectableDataModel<AqDto> {

	public AqListModel(List<AqDto> list) {
		super(list);
	}

	@Override
	public AqDto getRowData(String rowKey) {
		String seq = rowKey;
		List<AqDto> list = (List<AqDto>) getWrappedData();
		for (AqDto dto : list) {
			if (seq.equals(dto.getAppraisalSeq() +"_" + dto.getQuestion().getSeq())) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(AqDto dto) {
		return dto.getAppraisalSeq() +"_" + dto.getQuestion().getSeq();
	}

}
