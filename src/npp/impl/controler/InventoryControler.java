package npp.impl.controler;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import npp.condition.InventoryCondition;
import npp.dto.InventoryDto;
import npp.faces.InventoryListModel;
import npp.spec.service.InventoryService;


@Named
@SessionScoped
public class InventoryControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1562103454744365848L;
	private List<InventoryDto> baseList = null;
	private InventoryListModel listModel = null;

	private InventoryCondition condition;

	@Inject
	private InventoryService inventoryService;


	@PostConstruct
	public void init() {
		condition = new InventoryCondition();
	}

	public InventoryListModel getListModel() {
		if (baseList == null) {
			try {
				baseList = inventoryService.getAllList(condition);
				listModel = new InventoryListModel(baseList);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return baseList == null ? new InventoryListModel(
				new ArrayList<InventoryDto>()) : listModel;
	}

	public void clear()
	{
		baseList = null;
	}


	public int getStatusListLength() {
		if (listModel == null)
			return 0;
		return listModel.getRowCount();
	}

	public List<InventoryDto> getBaseList() throws IOException {
		if (baseList == null)
		{
			baseList = inventoryService.getAllList(condition);
			listModel = new InventoryListModel(baseList);
		}
		return baseList;
	}

	public void search() throws IOException
	{
		clear();
	}

	public InventoryCondition getCondition() {
		return condition;
	}

	public void setCondition(InventoryCondition condition) {
		this.condition = condition;
	}
}
