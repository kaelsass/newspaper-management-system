package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;


public class ItemListModel extends ListDataModel<String> implements
		SelectableDataModel<String> {

	public ItemListModel(List<String> list) {
		super(list);
	}

	@Override
	public String getRowData(String rowKey) {
		String seq = rowKey;
		List<String> list = (List<String>) getWrappedData();
		for (String dto : list) {
			if (seq.equals(dto)) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(String dto) {
		return dto;
	}

}
