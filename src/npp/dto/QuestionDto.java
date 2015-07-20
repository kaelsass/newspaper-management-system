package npp.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import npp.faces.ItemListModel;
import npp.utils.DateUtil;

public class QuestionDto {
	public static final int SINGLE_CHOICE = 1;
	public static final int MULTI_CHOICE = 2;
	public static final int SHORT_ANSWER = 3;
	private int seq;
	private String name;
	private StatusDto type;
	private Date addDate;
	private Date modDate;
	private List<String> items;
	private ItemListModel itemListModel;

	private int index;
	private String selectedItem;
	private List<String> selectedItems;
	private String answer;

	//You must declared default constructor for Framework.
	public QuestionDto(){
		StatusDto dto = new StatusDto(3, "Short Answer");
		init(0, "", dto, new Date(), new Date(), new ArrayList<String>());
	}

	public QuestionDto(int seq, String name, StatusDto type, Date addDate, Date modDate, List<String> items){
		init(seq, name, type, addDate, modDate, items);
	}

	public QuestionDto(QuestionDto dto) {
		init(dto.getSeq(), dto.getName(), dto.getType(), dto.getAddDate(), dto.getModDate(), dto.getItems());
	}
	public void init(int seq, String name, StatusDto type, Date addDate, Date modDate, List<String> items)
	{
		this.seq = seq;
		this.name = name;
		this.type = type;
		this.addDate = addDate;
		this.modDate = modDate;
		this.items = items;
		index = 0;
		clear();

	}

	public void clear() {
		itemListModel = null;
		selectedItem = "";
		selectedItems = new ArrayList<String>();
		answer = "";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public StatusDto getType() {
		return type;
	}

	public void setType(StatusDto type) {
		this.type = type;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public Date getModDate() {
		return modDate;
	}

	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}

	public List<String> getItems() {
		return items;
	}

	public void setItems(List<String> items) {
		this.items = items;
		itemListModel = null;
	}
	public String getFormatAddDate()
	{
		return DateUtil.getDateFormatInstance().format(addDate);
	}
	public String getFormatModDate()
	{
		return DateUtil.getDateFormatInstance().format(modDate);
	}

	public ItemListModel getItemListModel() {
		if(itemListModel == null)
		{
			itemListModel = new ItemListModel(items);
		}
		return itemListModel;
	}

	public void setItemListModel(ItemListModel itemListModel) {
		this.itemListModel = itemListModel;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof QuestionDto))
			return false;
		QuestionDto dto = (QuestionDto) obj;
		System.out.println(this.getSeq() + " : " + dto.getSeq());
		return this.getSeq() == dto.getSeq();
	}
	public String getFormatItems()
	{
		if(items == null || items.size() == 0)
			return "";
		StringBuffer sb = new StringBuffer();
		for(String item : items)
		{
			sb.append(item).append(", ");
		}

		return sb.toString().substring(0, sb.length()-2);
	}

	public String getFormatSelectedItems()
	{
		if(selectedItems == null || selectedItems.size() == 0)
			return "";
		StringBuffer sb = new StringBuffer();
		for(String item : selectedItems)
		{
			sb.append(item).append(", ");
		}

		return sb.toString().substring(0, sb.length()-2);
	}


	public List<String> getSelectedItems() {
		return selectedItems;
	}

	public void setSelectedItems(List<String> selectedItems) {
		this.selectedItems = selectedItems;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(String selectedItem) {
		this.selectedItem = selectedItem;
	}


}
