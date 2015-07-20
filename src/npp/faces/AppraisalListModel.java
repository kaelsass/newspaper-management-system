package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.AppraisalDto;

import org.primefaces.model.SelectableDataModel;


public class AppraisalListModel extends ListDataModel<AppraisalDto> implements
		SelectableDataModel<AppraisalDto> {

	public AppraisalListModel(List<AppraisalDto> list) {
		super(list);
	}

	@Override
	public AppraisalDto getRowData(String rowKey) {
		String seq = rowKey;
		List<AppraisalDto> list = (List<AppraisalDto>) getWrappedData();
		for (AppraisalDto dto : list) {
			if (seq.equals(dto.getSeq()+"")) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(AppraisalDto dto) {
		return dto.getSeq()+"";
	}

}
