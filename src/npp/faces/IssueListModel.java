package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.IssueDto;

import org.primefaces.model.SelectableDataModel;


public class IssueListModel extends ListDataModel<IssueDto> implements
		SelectableDataModel<IssueDto> {

	public IssueListModel(List<IssueDto> list) {
		super(list);
	}

	@Override
	public IssueDto getRowData(String rowKey) {
		String seq = rowKey;
		List<IssueDto> list = (List<IssueDto>) getWrappedData();
		for (IssueDto dto : list) {
			if (seq.equals(dto.getSeq() + "")) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(IssueDto dto) {
		return dto.getSeq()+"";
	}

}
