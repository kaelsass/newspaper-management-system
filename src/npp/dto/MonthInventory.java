package npp.dto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MonthInventory {
	private DatePair datePair;
	private List<InventoryDto> list;
	public MonthInventory()
	{
		datePair = new DatePair();
		list = new ArrayList<InventoryDto>();
	}

	public List<InventoryDto> getList() {
		return list;
	}
	public void setList(List<InventoryDto> list2) {
		this.list = list2;
	}

	public DatePair getDatePair() {
		return datePair;
	}

	public void setDatePair(DatePair datePair) {
		this.datePair = datePair;
	}

	public int getSalesQuantity() {
		int total = 0;
		for(InventoryDto dto : list)
		{
			total += dto.getSalesQuantity();
		}
		return total;
	}
	public double getSalesMoney()
	{
		double total = 0;
		for(InventoryDto dto : list)
		{
			total += dto.getSalesMoney();
		}
		return total;
	}

	public String getFormatDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
		return df.format(datePair.getFrom());
	}

	public int getSubscriberQuantity() {
		int total = 0;
		for(InventoryDto dto : list)
		{
			total += dto.getSubscriberQuantity();
		}
		return total;
	}
	public int getNonSubscriberQuantity() {
		int total = 0;
		for(InventoryDto dto : list)
		{
			total += dto.getNonSubscriberQuantity();
		}
		return total;
	}
	public int getRetailQuantity() {
		int total = 0;
		for(InventoryDto dto : list)
		{
			total += dto.getRetailQuantity();
		}
		return total;
	}
	public double getSubscriberMoney() {
		double total = 0;
		for(InventoryDto dto : list)
		{
			total += dto.getSubscriberMoney();
		}
		return total;
	}
	public double getRetailMoney() {
		double total = 0;
		for(InventoryDto dto : list)
		{
			total += dto.getRetailMoney();
		}
		return total;
	}

	public double getAdMoney() {
		double total = 0;
		for(InventoryDto dto : list)
		{
			total += dto.getAdMoney();
		}
		return total;
	}
}
