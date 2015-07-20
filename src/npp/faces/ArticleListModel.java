package npp.faces;

import java.util.List;

import javax.faces.model.ListDataModel;

import npp.dto.ArticleDto;

import org.primefaces.model.SelectableDataModel;


public class ArticleListModel extends ListDataModel<ArticleDto> implements
		SelectableDataModel<ArticleDto> {

	public ArticleListModel(List<ArticleDto> list) {
		super(list);
	}

	@Override
	public ArticleDto getRowData(String rowKey) {
		String seq = rowKey;
		List<ArticleDto> list = (List<ArticleDto>) getWrappedData();
		for (ArticleDto dto : list) {
			if (seq.equals(dto.getSeq() + "")) {
				return dto;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(ArticleDto dto) {
		return dto.getSeq()+"";
	}

}
