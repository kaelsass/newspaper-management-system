package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.AcDto;

import org.primefaces.model.SelectableDataModel;


public class AcListModel extends ListDataModel<AcDto> implements
		SelectableDataModel<AcDto> {

	public AcListModel(List<AcDto> list) {
		super(list);
	}

	@Override
	public AcDto getRowData(String rowKey) {
		String seq = rowKey;
		List<AcDto> list = (List<AcDto>) getWrappedData();
		for (AcDto dto : list) {
			if (seq.equals(dto.getAppraisalSeq() +"_" + dto.getCompetency().getSeq())) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(AcDto dto) {
		return dto.getAppraisalSeq() +"_" + dto.getCompetency().getSeq();
	}

}
