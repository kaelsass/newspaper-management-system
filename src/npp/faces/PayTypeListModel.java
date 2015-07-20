package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.PayTypeDto;

import org.primefaces.model.SelectableDataModel;


public class PayTypeListModel extends ListDataModel<PayTypeDto> implements
		SelectableDataModel<PayTypeDto> {

	public PayTypeListModel(List<PayTypeDto> list) {
		super(list);
	}

	@Override
	public PayTypeDto getRowData(String rowKey) {
		String seq = rowKey;
		List<PayTypeDto> list = (List<PayTypeDto>) getWrappedData();
		for (PayTypeDto dto : list) {
			if (seq.equals(dto.getSeq() + "")) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(PayTypeDto dto) {
		return dto.getSeq()+"";
	}

}
