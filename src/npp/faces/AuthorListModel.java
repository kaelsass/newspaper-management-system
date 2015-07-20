package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.AuthorDto;

import org.primefaces.model.SelectableDataModel;


public class AuthorListModel extends ListDataModel<AuthorDto> implements
		SelectableDataModel<AuthorDto> {

	public AuthorListModel(List<AuthorDto> list) {
		super(list);
	}

	@Override
	public AuthorDto getRowData(String rowKey) {
		String seq = rowKey;
		List<AuthorDto> list = (List<AuthorDto>) getWrappedData();
		for (AuthorDto dto : list) {
			if (seq.equals(dto.getSeq() + "")) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(AuthorDto dto) {
		return dto.getSeq()+"";
	}

}
