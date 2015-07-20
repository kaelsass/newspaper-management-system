package npp.dto;

import java.util.ArrayList;

import org.primefaces.model.chart.PieChartModel;

public class InventorySummaryDto {
	private NewspaperDto newspaper;
	private ArrayList<MonthInventory> list;
	public InventorySummaryDto()
	{
		newspaper = new NewspaperDto();
		list = new ArrayList<MonthInventory>();
	}

	public ArrayList<MonthInventory> getList() {
		return list;
	}
	public void setList(ArrayList<MonthInventory> list) {
		this.list = list;
	}

	public NewspaperDto getNewspaper() {
		return newspaper;
	}

	public void setNewspaper(NewspaperDto newspaper) {
		this.newspaper = newspaper;
	}

	public int getSalesQuantity() {
		int total = 0;
		for(MonthInventory mi : list)
		{
			total += mi.getSalesQuantity();
		}
		return total;
	}

	public double getSalesMoney() {
		double total = 0;
		for(MonthInventory mi : list)
		{
			total += mi.getSalesMoney();
		}
		return total;
	}

	public int getSubscriberQuantity() {
		int total = 0;
		for(MonthInventory mi : list)
		{
			total += mi.getSubscriberQuantity();
		}
		return total;
	}
	public int getNonSubscriberQuantity() {
		int total = 0;
		for(MonthInventory mi : list)
		{
			total += mi.getNonSubscriberQuantity();
		}
		return total;
	}
	public int getRetailQuantity() {
		int total = 0;
		for(MonthInventory mi : list)
		{
			total += mi.getRetailQuantity();
		}
		return total;
	}

	public double getSubscriberMoney() {
		double total = 0;
		for(MonthInventory mi : list)
		{
			total += mi.getSubscriberMoney();
		}
		return total;
	}

	public double getRetailMoney() {
		double total = 0;
		for(MonthInventory mi : list)
		{
			total += mi.getRetailMoney();
		}
		return total;
	}
	public double getAdMoney() {
		double total = 0;
		for(MonthInventory mi : list)
		{
			total += mi.getAdMoney();
		}
		return total;
	}
	public PieChartModel getQuantityProportionModel()
	{
		PieChartModel quantityProportionModel = new PieChartModel();
		quantityProportionModel.set("Subscriber", this.getSubscriberQuantity());
		quantityProportionModel.set("Non-Subscriber", this.getNonSubscriberQuantity());
		quantityProportionModel.set("Retail", this.getRetailQuantity());
		return quantityProportionModel;

	}
	public PieChartModel getMoneyProportionModel()
	{
		PieChartModel moneyProportionModel = new PieChartModel();
		moneyProportionModel.set("Subscriber", this.getSubscriberMoney());
		moneyProportionModel.set("Retail", this.getRetailMoney());
		moneyProportionModel.set("Advertisement", this.getAdMoney());
		return moneyProportionModel;
	}

	public int getQuantityForMonth(int i) {
		if(i >= list.size())
			return 0;
		return list.get(i).getSalesQuantity();
	}

	public double getMoneyForMonth(int i) {
		if(i >= list.size())
			return 0;
		return list.get(i).getSalesMoney();
	}

}
