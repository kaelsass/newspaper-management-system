package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.PromotionDto;

import org.primefaces.model.SelectableDataModel;


public class PromotionListModel extends ListDataModel<PromotionDto> implements
		SelectableDataModel<PromotionDto> {

	public PromotionListModel(List<PromotionDto> list) {
		super(list);
	}

	@Override
	public PromotionDto getRowData(String rowKey) {
		String seq = rowKey;
		List<PromotionDto> list = (List<PromotionDto>) getWrappedData();
		for (PromotionDto dto : list) {
			if (seq.equals(dto.getSeq()+"")) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(PromotionDto dto) {
		return dto.getSeq()+"";
	}

}
