package npp.impl.controler;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import npp.condition.DynamicQuery;
import npp.condition.InventoryCondition;
import npp.condition.Parameter;
import npp.condition.WorkInfoCondition;
import npp.dto.DatePair;
import npp.dto.InventoryDto;
import npp.dto.InventorySummaryDto;
import npp.dto.MonthInventory;
import npp.dto.NewspaperDto;
import npp.faces.InventorySummaryListModel;
import npp.spec.service.InventoryService;
import npp.spec.service.NewspaperService;
import npp.utils.DateUtil;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;


@Named
@SessionScoped
public class InventorySummaryControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1717033802946005319L;
	private List<InventorySummaryDto> baseList = null;
	private InventorySummaryListModel listModel = null;

	private CartesianChartModel monthlyQuantityModel = null;
	private CartesianChartModel monthlyMoneyModel = null;
	private CartesianChartModel detailQuantityTrendModel = null;
	private CartesianChartModel detailMoneyTrendModel = null;
	private CartesianChartModel totalQuantityModel = null;
	private CartesianChartModel totalMoneyModel = null;
	private PieChartModel quantityProportionModel = null;
	private PieChartModel moneyProportionModel = null;

	private InventoryCondition condition;
	private WorkInfoCondition workInfoCondition;

	private int viewMode = 0;

	@Inject
	private InventoryService inventoryService;

	@Inject
	private NewspaperService newspaperService;

	private List<NewspaperDto> newspapers;

	@PostConstruct
	public void init() {
		condition = new InventoryCondition();
		workInfoCondition = new WorkInfoCondition();
	}

	public InventorySummaryListModel getListModel() {
		if (baseList == null) {
			try {
				baseList = new ArrayList<InventorySummaryDto>();
				newspapers = getAllNewspapers();
				ArrayList<DatePair> dates = DateUtil.makeDatePairsByMonth(
						condition.getFrom(), condition.getTo()); // not finish
				for (NewspaperDto newspaper : newspapers) {
					InventorySummaryDto dto = new InventorySummaryDto();
					dto.setNewspaper(newspaper);
					for (DatePair pair : dates) {
						dto.getList().add(makeMonthInventory(newspaper, pair));
					}
					baseList.add(dto);
				}
				listModel = new InventorySummaryListModel(baseList);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return listModel;
	}

	public CartesianChartModel getTotalQuantityModel() {
		initTotalQuantityModel();
		return totalQuantityModel;
	}

	private void initTotalQuantityModel() {
		totalQuantityModel = new CartesianChartModel();
		ChartSeries subscriber = new ChartSeries();
		subscriber.setLabel("Subscriber");
		for (InventorySummaryDto dto : getBaseList()) {
			subscriber.set(dto.getNewspaper().getName(),
					dto.getSubscriberQuantity());
		}
		ChartSeries nonSubscriber = new ChartSeries();
		nonSubscriber.setLabel("Non-Subscriber");
		for (InventorySummaryDto dto : getBaseList()) {
			nonSubscriber.set(dto.getNewspaper().getName(),
					dto.getNonSubscriberQuantity());
		}
		ChartSeries retail = new ChartSeries();
		retail.setLabel("Retail");
		for (InventorySummaryDto dto : getBaseList()) {
			retail.set(dto.getNewspaper().getName(), dto.getRetailQuantity());
		}
		totalQuantityModel.addSeries(subscriber);
		totalQuantityModel.addSeries(nonSubscriber);
		totalQuantityModel.addSeries(retail);
	}

	public CartesianChartModel getTotalMoneyModel() {
		initTotalMoneyModel();
		return totalMoneyModel;
	}

	private void initTotalMoneyModel() {
		totalMoneyModel = new CartesianChartModel();
		ChartSeries subscriber = new ChartSeries();
		subscriber.setLabel("Subscriber");
		for (InventorySummaryDto dto : getBaseList()) {
			subscriber.set(dto.getNewspaper().getName(),
					dto.getSubscriberMoney());
		}
		ChartSeries retail = new ChartSeries();
		retail.setLabel("Retail");
		for (InventorySummaryDto dto : getBaseList()) {
			retail.set(dto.getNewspaper().getName(), dto.getRetailQuantity());
		}
		ChartSeries ad = new ChartSeries();
		ad.setLabel("Advertisement");
		for (InventorySummaryDto dto : getBaseList()) {
			ad.set(dto.getNewspaper().getName(), dto.getAdMoney());
		}
		totalMoneyModel.addSeries(subscriber);
		totalMoneyModel.addSeries(retail);
		totalMoneyModel.addSeries(ad);
	}

	// private void initTotalModel() {
	// totalModel = new CartesianChartModel();
	// ChartSeries quantity = new ChartSeries();
	// quantity.setLabel("Quantity");
	// for(InventorySummaryDto dto : getBaseList())
	// {
	// quantity.set(dto.getNewspaper().getName(), dto.getSalesQuantity());
	// }
	//
	// ChartSeries money = new ChartSeries();
	// money.setLabel("Money");
	// for(InventorySummaryDto dto : getBaseList())
	// {
	// money.set(dto.getNewspaper().getName(), dto.getsalesMoney());
	// }
	//
	// totalModel.addSeries(quantity);
	// totalModel.addSeries(money);
	// }

	private MonthInventory makeMonthInventory(NewspaperDto newspaper,
			DatePair pair) throws IOException {
		InventoryCondition ic = new InventoryCondition();
		ic.setNewspaperSeqs(new int[] { newspaper.getSeq() });
		ic.setFrom(pair.getFrom());
		ic.setTo(pair.getTo());
		List<InventoryDto> list = inventoryService.getAllList(ic);
		MonthInventory mi = new MonthInventory();
		mi.setDatePair(pair);
		mi.setList(list);
		return mi;
	}

	private List<NewspaperDto> getAllNewspapers() throws IOException {
		DynamicQuery dq = new DynamicQuery();
		if (condition.getNewspaperSeqs().length > 0) {
			int[] seqs = condition.getNewspaperSeqs();
			for (int seq : seqs) {
				Parameter para = new Parameter("seq", "=", seq);
				para.setType(Parameter.OR);
				dq.addParameter(para);
			}
		}
		return newspaperService.getAllList(dq);
	}

	// private void fillEmptyRecords(List<InventorySummaryDto> list) {
	// for(NewspaperDto newspaper : newspapers)
	// {
	// Date from = condition.getFrom();
	// Date to = condition.getTo();
	// PersonRecordDto pr = getRecordsForNewspaper(newspaper, list);
	// if(pr != null)
	// {
	// for(Date cur = new Date(from.getTime()); cur.compareTo(to) <= 0; cur =
	// new Date(cur.getTime() + 24*3600*1000))
	// {
	// DayRecord dr = getRecordsForDate(cur, pr.getList());
	// if(dr == null)
	// {
	// pr.getList().add(makeRecordForDate(cur));
	// }
	// }
	// }
	// else
	// {
	// PersonRecordDto newPerson = new PersonRecordDto();
	// newPerson.setEmployee(dto);
	// newPerson.setList(makeRecordsForDate(from, to));
	// list.add(newPerson);
	// }
	// }
	// }

	public void search() throws IOException {
		clear();
	}

	public void clear() {
		baseList = null;
	}

	public InventoryCondition getCondition() {
		return condition;
	}

	public void setCondition(InventoryCondition condition) {
		this.condition = condition;
	}

	public WorkInfoCondition getWorkInfoCondition() {
		return workInfoCondition;
	}

	public void setWorkInfoCondition(WorkInfoCondition workInfoCondition) {
		this.workInfoCondition = workInfoCondition;
	}

	public int getViewMode() {
		return viewMode;
	}

	public void setViewMode(int viewMode) {
		this.viewMode = viewMode;
	}

	public List<InventorySummaryDto> getBaseList() {
		if (baseList == null) {
			try {
				baseList = new ArrayList<InventorySummaryDto>();
				newspapers = getAllNewspapers();
				ArrayList<DatePair> dates = DateUtil.makeDatePairsByMonth(
						condition.getFrom(), condition.getTo()); // not finish
				for (NewspaperDto newspaper : newspapers) {
					InventorySummaryDto dto = new InventorySummaryDto();
					dto.setNewspaper(newspaper);
					for (DatePair pair : dates) {
						dto.getList().add(makeMonthInventory(newspaper, pair));
					}
					baseList.add(dto);
				}
				listModel = new InventorySummaryListModel(baseList);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return baseList;
	}

	public void setBaseList(List<InventorySummaryDto> baseList) {
		this.baseList = baseList;
	}

	public CartesianChartModel getMonthlyMoneyModel() {
		initMonthlyMoneyModel();
		return monthlyMoneyModel;
	}

	public void setMonthlyMoneyModel(CartesianChartModel monthlyMoneyModel) {
		this.monthlyMoneyModel = monthlyMoneyModel;
	}

	public CartesianChartModel getMonthlyQuantityModel() {
		initMonthlyQuantityModel();
		return monthlyQuantityModel;
	}

	private void initMonthlyQuantityModel() {
		monthlyQuantityModel = new CartesianChartModel();
		for (InventorySummaryDto dto : getBaseList()) {
			ChartSeries newspaper = new ChartSeries();
			newspaper.setLabel(dto.getNewspaper().getName());
			for (MonthInventory mi : dto.getList()) {
				newspaper.set(mi.getFormatDate(), mi.getSalesQuantity());
			}
			monthlyQuantityModel.addSeries(newspaper);
		}
	}

	private void initMonthlyMoneyModel() {
		monthlyMoneyModel = new CartesianChartModel();
		for (InventorySummaryDto dto : getBaseList()) {
			ChartSeries newspaper = new ChartSeries();
			newspaper.setLabel(dto.getNewspaper().getName());
			for (MonthInventory mi : dto.getList()) {
				newspaper.set(mi.getFormatDate(), mi.getSalesMoney());
			}
			monthlyMoneyModel.addSeries(newspaper);
		}
	}

	public void setMonthlyQuantityModel(CartesianChartModel monthlyQuantityModel) {
		this.monthlyQuantityModel = monthlyQuantityModel;
	}

	public PieChartModel getQuantityProportionModel() {
		initQuantityProportionModel();
		return quantityProportionModel;
	}

	private void initQuantityProportionModel() {
		quantityProportionModel = new PieChartModel();
		for (InventorySummaryDto dto : getBaseList()) {
			quantityProportionModel.set(dto.getNewspaper().getName(),
					dto.getSalesQuantity());
		}
	}

	public void setQuantityProportionModel(PieChartModel quantityProportion) {
		this.quantityProportionModel = quantityProportion;
	}

	public PieChartModel getMoneyProportionModel() {
		initMoneyProportionModel();
		return moneyProportionModel;
	}

	public void setMoneyProportionModel(PieChartModel moneyProportionModel) {
		this.moneyProportionModel = moneyProportionModel;
	}

	private void initMoneyProportionModel() {
		moneyProportionModel = new PieChartModel();
		for (InventorySummaryDto dto : getBaseList()) {
			moneyProportionModel.set(dto.getNewspaper().getName(),
					dto.getSalesMoney());
		}
	}

	public CartesianChartModel getDetailQuantityTrendModel() {
		initDetailQuantityTrendModel();
		return detailQuantityTrendModel;
	}

	public void setDetailQuantityTrendModel(
			CartesianChartModel detailQuantityTrendModel) {
		this.detailQuantityTrendModel = detailQuantityTrendModel;
	}

	public CartesianChartModel getDetailMoneyTrendModel() {
		initDetailMoneyTrendModel();
		return detailMoneyTrendModel;
	}

	public void setDetailMoneyTrendModel(
			CartesianChartModel detailMoneyTrendModel) {
		this.detailMoneyTrendModel = detailMoneyTrendModel;
	}

	private void initDetailQuantityTrendModel() {
		detailQuantityTrendModel = new CartesianChartModel();
		ChartSeries subscriber = new ChartSeries();
		subscriber.setLabel("Subscriber");
		ChartSeries nonSubscriber = new ChartSeries();
		nonSubscriber.setLabel("Non-Subscriber");
		ChartSeries retail = new ChartSeries();
		retail.setLabel("Retail");
		ArrayList<ArrayList<InventorySummaryDto>> list = makeListByDate(getBaseList());
		for (ArrayList<InventorySummaryDto> cur : list) {
			double subscriberTotal = 0;
			double retailTotal = 0;
			double nonSubscriberTotal = 0;
			for (InventorySummaryDto is : cur) {
				subscriberTotal += is.getSubscriberQuantity();
				nonSubscriberTotal += is.getNonSubscriberQuantity();
				retailTotal += is.getRetailQuantity();
			}
			String dateStr = cur.get(0).getList().get(0).getFormatDate();
			subscriber.set(dateStr, subscriberTotal);
			nonSubscriber.set(dateStr, nonSubscriberTotal);
			retail.set(dateStr, retailTotal);

		}
		detailQuantityTrendModel.addSeries(subscriber);
		detailQuantityTrendModel.addSeries(nonSubscriber);
		detailQuantityTrendModel.addSeries(retail);
	}

	private void initDetailMoneyTrendModel() {
		detailMoneyTrendModel = new CartesianChartModel();
		ChartSeries subscriber = new ChartSeries();
		subscriber.setLabel("Subscriber");
		ChartSeries retail = new ChartSeries();
		retail.setLabel("Retail");
		ChartSeries ad = new ChartSeries();
		ad.setLabel("Advertisement");
		ArrayList<ArrayList<InventorySummaryDto>> list = makeListByDate(getBaseList());
		for (ArrayList<InventorySummaryDto> cur : list) {
			double subscriberTotal = 0;
			double retailTotal = 0;
			double adTotal = 0;
			for (InventorySummaryDto is : cur) {
				subscriberTotal += is.getSubscriberMoney();
				retailTotal += is.getRetailMoney();
				adTotal += is.getAdMoney();
			}
			String dateStr = cur.get(0).getList().get(0).getFormatDate();
			subscriber.set(dateStr, subscriberTotal);
			retail.set(dateStr, retailTotal);
			ad.set(dateStr, adTotal);
		}

		detailMoneyTrendModel.addSeries(subscriber);
		detailMoneyTrendModel.addSeries(retail);
		detailMoneyTrendModel.addSeries(ad);
	}

	private ArrayList<ArrayList<InventorySummaryDto>> makeListByDate(
			List<InventorySummaryDto> list) {
		ArrayList<ArrayList<InventorySummaryDto>> ret = new ArrayList<ArrayList<InventorySummaryDto>>();
		if (list.size() == 0)
			return ret;
		int monthCount = list.get(0).getList().size();
		for (int i = 0; i < monthCount; i++) {
			ArrayList<InventorySummaryDto> cur = new ArrayList<InventorySummaryDto>();
			for (InventorySummaryDto is : list) {
				InventorySummaryDto newDto = new InventorySummaryDto();
				newDto.setNewspaper(is.getNewspaper());
				newDto.getList().add(is.getList().get(i));
				cur.add(newDto);
			}
			ret.add(cur);
		}
		return ret;
	}
}
