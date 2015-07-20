package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.AgDto;

import org.primefaces.model.SelectableDataModel;


public class AgListModel extends ListDataModel<AgDto> implements
		SelectableDataModel<AgDto> {

	public AgListModel(List<AgDto> list) {
		super(list);
	}

	@Override
	public AgDto getRowData(String rowKey) {
		String seq = rowKey;
		List<AgDto> list = (List<AgDto>) getWrappedData();
		for (AgDto dto : list) {
			if (seq.equals(dto.getAppraisalSeq() +"_" + dto.getGoal().getSeq())) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(AgDto dto) {
		return dto.getAppraisalSeq() +"_" + dto.getGoal().getSeq();
	}

}
