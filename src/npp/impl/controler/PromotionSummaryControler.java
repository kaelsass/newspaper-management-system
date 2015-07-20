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
import npp.condition.PromotionSummaryCondition;
import npp.condition.WorkInfoCondition;
import npp.dto.DatePair;
import npp.dto.InventoryDto;
import npp.dto.InventorySummaryDto;
import npp.dto.MonthInventory;
import npp.dto.NewspaperDto;
import npp.dto.PromotionDto;
import npp.dto.PromotionSummaryDto;
import npp.spec.service.InventoryService;
import npp.spec.service.NewspaperService;
import npp.spec.service.PromotionService;
import npp.utils.DateUtil;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;


@Named
@SessionScoped
public class PromotionSummaryControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1717033802946005319L;
	private List<PromotionSummaryDto> baseList = null;

	private CartesianChartModel monthlyQuantityModel = null;
	private CartesianChartModel monthlyMoneyModel = null;
//	private CartesianChartModel detailQuantityTrendModel = null;
//	private CartesianChartModel detailMoneyTrendModel = null;
	private CartesianChartModel totalQuantityModel = null;
	private CartesianChartModel totalMoneyModel = null;
	private PieChartModel quantityProportionModel = null;
	private PieChartModel moneyProportionModel = null;

	private PromotionSummaryCondition condition;
	private WorkInfoCondition workInfoCondition;

	private int viewMode = 0;

	@Inject
	private InventoryService inventoryService;

	@Inject
	private NewspaperService newspaperService;
	@Inject
	private PromotionService promotionService;
	private ArrayList<DatePair> dates = null;

	@PostConstruct
	public void init() {
		condition = new PromotionSummaryCondition();
		workInfoCondition = new WorkInfoCondition();
		dates = new ArrayList<DatePair>();
	}

//	public InventorySummaryListModel getListModel() {
//		if (baseList == null) {
//			try {
//				baseList = new ArrayList<InventorySummaryDto>();
//				newspapers = getAllNewspapers();
//				ArrayList<DatePair> dates = DateUtil.makeDatePairsByMonth(
//						condition.getFrom(), condition.getTo()); // not finish
//				for (NewspaperDto newspaper : newspapers) {
//					InventorySummaryDto dto = new InventorySummaryDto();
//					dto.setNewspaper(newspaper);
//					for (DatePair pair : dates) {
//						dto.getList().add(makeMonthInventory(newspaper, pair));
//					}
//					baseList.add(dto);
//				}
//				listModel = new InventorySummaryListModel(baseList);
//			} catch (IOException e) {
//				throw new RuntimeException(e);
//			}
//		}
//		return listModel;
//	}
	public List<PromotionSummaryDto> getBaseList() {
		if (baseList == null) {
			try {
				baseList = new ArrayList<PromotionSummaryDto>();
				List<PromotionDto> promotions = getAllPromotions();
				List<NewspaperDto> newspapers = getAllNewspapers();
				dates  = DateUtil.makeDatePairsByMonth(
						condition.getFrom(), condition.getTo());
				for(PromotionDto promotion : promotions)
				{
					PromotionSummaryDto psDto = new PromotionSummaryDto();
					psDto.setPromotion(promotion);
					for(NewspaperDto newspaper : newspapers)
					{
						InventorySummaryDto dto = new InventorySummaryDto();
						dto.setNewspaper(newspaper);
						for (DatePair pair : dates) {
							dto.getList().add(makeMonthInventory(promotion, newspaper, pair));
						}
						psDto.getList().add(dto);
					}
					baseList.add(psDto);
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return baseList == null ? new ArrayList<PromotionSummaryDto>() : baseList;
	}

	public CartesianChartModel getTotalQuantityModel() {
		initTotalQuantityModel();
		return totalQuantityModel;
	}

	private void initTotalQuantityModel() {
		totalQuantityModel = new CartesianChartModel();
		List<NewspaperDto> newspapers = getAllNewspapers();

		for(int i = 0; i < newspapers.size(); i++)
		{
			NewspaperDto newspaper = newspapers.get(i);
			ChartSeries cs = new ChartSeries();
			cs.setLabel(newspaper.getName());
			for (PromotionSummaryDto dto : getBaseList()) {
				cs.set(dto.getPromotion().getName(),
						dto.getQuantityForNewspaper(i));
			}
			totalQuantityModel.addSeries(cs);
		}
	}

	public CartesianChartModel getTotalMoneyModel() {
		initTotalMoneyModel();
		return totalMoneyModel;
	}

	private void initTotalMoneyModel() {
		totalMoneyModel = new CartesianChartModel();
		List<NewspaperDto> newspapers = getAllNewspapers();
		for(int i = 0; i < newspapers.size(); i++)
		{
			NewspaperDto newspaper = newspapers.get(i);
			ChartSeries cs = new ChartSeries();
			cs.setLabel(newspaper.getName());
			for (PromotionSummaryDto dto : getBaseList()) {
				cs.set(dto.getPromotion().getName(),
						dto.getMoneyForNewspaper(i));
			}
			totalMoneyModel.addSeries(cs);
		}
	}

	public CartesianChartModel getMonthlyMoneyModel() {
		initMonthlyMoneyModel();
		return monthlyMoneyModel;
	}

	public CartesianChartModel getMonthlyQuantityModel() {
		initMonthlyQuantityModel();
		return monthlyQuantityModel;
	}

	private void initMonthlyQuantityModel() {
		monthlyQuantityModel = new CartesianChartModel();
		for (PromotionSummaryDto dto : getBaseList()) {
			ChartSeries promotion = new ChartSeries();
			promotion.setLabel(dto.getPromotion().getName());
			for(int i = 0; i < dates.size(); i++)
			{
				promotion.set(dates.get(i).getFormatDate(), dto.getQuantityForMonth(i));
			}

			monthlyQuantityModel.addSeries(promotion);
		}
	}

	private void initMonthlyMoneyModel() {
		monthlyMoneyModel = new CartesianChartModel();
		for (PromotionSummaryDto dto : getBaseList()) {
			ChartSeries promotion = new ChartSeries();
			promotion.setLabel(dto.getPromotion().getName());
			for(int i = 0; i < dates.size(); i++)
			{
				promotion.set(dates.get(i).getFormatDate(), dto.getMoneyForMonth(i));
			}

			monthlyMoneyModel.addSeries(promotion);
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
		for (PromotionSummaryDto dto : getBaseList()) {
			quantityProportionModel.set(dto.getPromotion().getName(),
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
		for (PromotionSummaryDto dto : getBaseList()) {
			moneyProportionModel.set(dto.getPromotion().getName(),
					dto.getsalesMoney());
		}
	}


	private MonthInventory makeMonthInventory(PromotionDto promotion, NewspaperDto newspaper,
			DatePair pair) throws IOException {
		InventoryCondition ic = new InventoryCondition();
		ic.setNewspaperSeqs(new int[] { newspaper.getSeq() });
		ic.setPromotionSeq(promotion.getSeq());
		ic.setFrom(pair.getFrom());
		ic.setTo(pair.getTo());
		List<InventoryDto> list = inventoryService.getAllList(ic);
		MonthInventory mi = new MonthInventory();
		mi.setDatePair(pair);
		mi.setList(list);
		return mi;
	}

	private List<NewspaperDto> getAllNewspapers() {
		List<NewspaperDto> list = new ArrayList<NewspaperDto>();
		DynamicQuery dq = new DynamicQuery();
		if (condition.getNewspaperSeqs().length > 0) {
			int[] seqs = condition.getNewspaperSeqs();
			for (int seq : seqs) {
				Parameter para = new Parameter("seq", "=", seq);
				para.setType(Parameter.OR);
				dq.addParameter(para);
			}
		}
		try {
			list = newspaperService.getAllList(dq);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	private List<PromotionDto> getAllPromotions() throws IOException {
		DynamicQuery dq = new DynamicQuery();
		if (condition.getPromotionSeqs().length > 0) {
			int[] seqs = condition.getPromotionSeqs();
			for (int seq : seqs) {
				Parameter para = new Parameter("seq", "=", seq);
				para.setType(Parameter.OR);
				dq.addParameter(para);
			}
		}
		return promotionService.getAllList(dq);
	}

	public void search() throws IOException {
		clear();
	}

	public void clear() {
		baseList = null;
	}

	public PromotionSummaryCondition getCondition() {
		return condition;
	}

	public void setCondition(PromotionSummaryCondition condition) {
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



	public void setBaseList(List<PromotionSummaryDto> baseList) {
		this.baseList = baseList;
	}


	public void setMonthlyMoneyModel(CartesianChartModel monthlyMoneyModel) {
		this.monthlyMoneyModel = monthlyMoneyModel;
	}

//
//	public CartesianChartModel getDetailQuantityTrendModel() {
//		initDetailQuantityTrendModel();
//		return detailQuantityTrendModel;
//	}
//
//	public void setDetailQuantityTrendModel(
//			CartesianChartModel detailQuantityTrendModel) {
//		this.detailQuantityTrendModel = detailQuantityTrendModel;
//	}
//
//	public CartesianChartModel getDetailMoneyTrendModel() {
//		initDetailMoneyTrendModel();
//		return detailMoneyTrendModel;
//	}
//
//	public void setDetailMoneyTrendModel(
//			CartesianChartModel detailMoneyTrendModel) {
//		this.detailMoneyTrendModel = detailMoneyTrendModel;
//	}

//	private void initDetailQuantityTrendModel() {
//		detailQuantityTrendModel = new CartesianChartModel();
//		ChartSeries subscriber = new ChartSeries();
//		subscriber.setLabel("Subscriber");
//		ChartSeries nonSubscriber = new ChartSeries();
//		nonSubscriber.setLabel("Non-Subscriber");
//		ChartSeries retail = new ChartSeries();
//		retail.setLabel("Retail");
//		ArrayList<ArrayList<InventorySummaryDto>> list = makeListByDate(getBaseList());
//		for (ArrayList<InventorySummaryDto> cur : list) {
//			double subscriberTotal = 0;
//			double retailTotal = 0;
//			double nonSubscriberTotal = 0;
//			for (InventorySummaryDto is : cur) {
//				subscriberTotal += is.getSubscriberQuantity();
//				nonSubscriberTotal += is.getNonSubscriberQuantity();
//				retailTotal += is.getRetailQuantity();
//			}
//			String dateStr = cur.get(0).getList().get(0).getFormatDate();
//			subscriber.set(dateStr, subscriberTotal);
//			nonSubscriber.set(dateStr, nonSubscriberTotal);
//			retail.set(dateStr, retailTotal);
//
//		}
//		detailQuantityTrendModel.addSeries(subscriber);
//		detailQuantityTrendModel.addSeries(nonSubscriber);
//		detailQuantityTrendModel.addSeries(retail);
//	}
//
//	private void initDetailMoneyTrendModel() {
//		detailMoneyTrendModel = new CartesianChartModel();
//		ChartSeries subscriber = new ChartSeries();
//		subscriber.setLabel("Subscriber");
//		ChartSeries retail = new ChartSeries();
//		retail.setLabel("Retail");
//		ChartSeries ad = new ChartSeries();
//		ad.setLabel("Advertisement");
//		ArrayList<ArrayList<InventorySummaryDto>> list = makeListByDate(getBaseList());
//		for (ArrayList<InventorySummaryDto> cur : list) {
//			double subscriberTotal = 0;
//			double retailTotal = 0;
//			double adTotal = 0;
//			for (InventorySummaryDto is : cur) {
//				subscriberTotal += is.getSubscriberMoney();
//				retailTotal += is.getRetailMoney();
//				adTotal += is.getAdMoney();
//			}
//			String dateStr = cur.get(0).getList().get(0).getFormatDate();
//			subscriber.set(dateStr, subscriberTotal);
//			retail.set(dateStr, retailTotal);
//			ad.set(dateStr, adTotal);
//		}
//
//		detailMoneyTrendModel.addSeries(subscriber);
//		detailMoneyTrendModel.addSeries(retail);
//		detailMoneyTrendModel.addSeries(ad);
//	}

//	private ArrayList<ArrayList<InventorySummaryDto>> makeListByDate(
//			List<InventorySummaryDto> list) {
//		ArrayList<ArrayList<InventorySummaryDto>> ret = new ArrayList<ArrayList<InventorySummaryDto>>();
//		if (list.size() == 0)
//			return ret;
//		int monthCount = list.get(0).getList().size();
//		for (int i = 0; i < monthCount; i++) {
//			ArrayList<InventorySummaryDto> cur = new ArrayList<InventorySummaryDto>();
//			for (InventorySummaryDto is : list) {
//				InventorySummaryDto newDto = new InventorySummaryDto();
//				newDto.setNewspaper(is.getNewspaper());
//				newDto.getList().add(is.getList().get(i));
//				cur.add(newDto);
//			}
//			ret.add(cur);
//		}
//		return ret;
//	}
}
