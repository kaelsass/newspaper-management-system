package npp.dto;

import java.util.ArrayList;

import org.primefaces.model.chart.PieChartModel;

public class AgeSummaryDto {
	private AgePair agePair;
	private ArrayList<InventorySummaryDto> list;
	public AgeSummaryDto()
	{
		agePair = new AgePair();
		list = new ArrayList<InventorySummaryDto>();
	}


	public int getSalesQuantity() {
		int total = 0;
		for(InventorySummaryDto dto : list)
		{
			total += dto.getSalesQuantity();
		}
		return total;
	}

	public double getsalesMoney() {
		double total = 0;
		for(InventorySummaryDto dto : list)
		{
			total += dto.getSalesMoney();
		}
		return total;
	}

	public ArrayList<InventorySummaryDto> getList() {
		return list;
	}

	public void setList(ArrayList<InventorySummaryDto> list) {
		this.list = list;
	}

	public int getQuantityForNewspaper(int i) {
		if(i >= list.size())
			return 0;
		return list.get(i).getSalesQuantity();
	}


	public double getMoneyForNewspaper(int i) {
		if(i >= list.size())
			return 0;
		return list.get(i).getSalesMoney();
	}


	public int getQuantityForMonth(int i) {
		int total = 0;
		for(InventorySummaryDto dto : list)
		{
			total += dto.getQuantityForMonth(i);
		}
		return total;

	}


	public double getMoneyForMonth(int i) {
		double total = 0;
		for(InventorySummaryDto dto : list)
		{
			total += dto.getMoneyForMonth(i);
		}
		return total;
	}
	public PieChartModel getQuantityProportionModel()
	{
		PieChartModel quantityProportionModel = new PieChartModel();
		for(InventorySummaryDto dto : list)
		{
			quantityProportionModel.set(dto.getNewspaper().getName(), dto.getSalesQuantity());
		}
		return quantityProportionModel;

	}
	public PieChartModel getMoneyProportionModel()
	{
		PieChartModel moneyProportionModel = new PieChartModel();
		for(InventorySummaryDto dto : list)
		{
			moneyProportionModel.set(dto.getNewspaper().getName(), dto.getSalesMoney());
		}
		return moneyProportionModel;
	}


	public AgePair getAgePair() {
		return agePair;
	}


	public void setAgePair(AgePair agePair) {
		this.agePair = agePair;
	}
}
