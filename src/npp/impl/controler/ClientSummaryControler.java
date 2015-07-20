package npp.impl.controler;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import npp.condition.ClientSummaryCondition;
import npp.condition.DynamicQuery;
import npp.condition.InventoryAgeCondition;
import npp.condition.InventoryEducationCondition;
import npp.condition.InventoryOccupationCondition;
import npp.condition.InventorySexCondition;
import npp.condition.Parameter;
import npp.condition.WorkInfoCondition;
import npp.dto.AgePair;
import npp.dto.AgeSummaryDto;
import npp.dto.DatePair;
import npp.dto.EducationDto;
import npp.dto.EducationSummaryDto;
import npp.dto.InventoryDto;
import npp.dto.InventorySummaryDto;
import npp.dto.MonthInventory;
import npp.dto.NewspaperDto;
import npp.dto.OccupationDto;
import npp.dto.OccupationSummaryDto;
import npp.dto.SexDto;
import npp.dto.SexSummaryDto;
import npp.spec.service.EducationService;
import npp.spec.service.InventoryAgeService;
import npp.spec.service.InventoryEducationService;
import npp.spec.service.InventoryOccupationService;
import npp.spec.service.InventorySexService;
import npp.spec.service.NewspaperService;
import npp.spec.service.OccupationService;
import npp.spec.service.SexService;
import npp.utils.DateUtil;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

@Named
@SessionScoped
public class ClientSummaryControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1717033802946005319L;
	private List<AgeSummaryDto> ageRangeList = null;
	private List<SexSummaryDto> sexList = null;
	private List<OccupationSummaryDto> occupationList = null;
	private List<EducationSummaryDto> educationList = null;

	private CartesianChartModel ageQuantityModel = null;
	private CartesianChartModel sexQuantityModel = null;
	private CartesianChartModel occupationQuantityModel = null;
	private CartesianChartModel educationQuantityModel = null;

	private List<NewspaperDto> newspapers = null;
	private List<AgePair> agePairs = null;
	private List<SexDto> sexes = null;
	private List<OccupationDto> occupations = null;
	private List<EducationDto> educations = null;

	private ClientSummaryCondition condition;
	private WorkInfoCondition workInfoCondition;

	private int viewMode = 0;


	@Inject
	private InventoryAgeService inventoryAgeService;
	@Inject
	private InventorySexService inventorySexService;
	@Inject
	private InventoryOccupationService inventoryOccupationService;
	@Inject
	private InventoryEducationService inventoryEducationService;

	@Inject
	private NewspaperService newspaperService;
	@Inject
	private SexService sexService;
	@Inject
	private OccupationService occupationService;
	@Inject
	private EducationService educationService;

	private ArrayList<DatePair> dates = null;



	@PostConstruct
	public void init() {
		condition = new ClientSummaryCondition();
		workInfoCondition = new WorkInfoCondition();
		dates = new ArrayList<DatePair>();
	}

	public CartesianChartModel getAgeQuantityModel() {
		initAgeQuantityModel();
		return ageQuantityModel;
	}

	public CartesianChartModel getSexQuantityModel() {
		initSexQuantityModel();
		return sexQuantityModel;
	}

	public CartesianChartModel getOccupationQuantityModel() {
		initOccupationQuantityModel();
		return occupationQuantityModel;
	}

	public CartesianChartModel getEducationQuantityModel() {
		initEducationQuantityModel();
		return educationQuantityModel;
	}

	private void initAgeQuantityModel() {
		ageQuantityModel = new CartesianChartModel();
		List<NewspaperDto> newspapers = getAllNewspapers();

		for (int i = 0; i < newspapers.size(); i++) {
			NewspaperDto newspaper = newspapers.get(i);
			ChartSeries cs = new ChartSeries();
			cs.setLabel(newspaper.getName());
			for (AgeSummaryDto dto : getAgeRangeList()) {
				cs.set(dto.getAgePair().getFormat(),
						dto.getQuantityForNewspaper(i));
			}
			ageQuantityModel.addSeries(cs);
		}
	}

	private void initSexQuantityModel() {
		sexQuantityModel = new CartesianChartModel();
		List<NewspaperDto> newspapers = getAllNewspapers();

		for (int i = 0; i < newspapers.size(); i++) {
			NewspaperDto newspaper = newspapers.get(i);
			ChartSeries cs = new ChartSeries();
			cs.setLabel(newspaper.getName());
			for (SexSummaryDto dto : getSexList()) {
				cs.set(dto.getSex().getName(), dto.getQuantityForNewspaper(i));
			}
			sexQuantityModel.addSeries(cs);
		}
	}

	private void initOccupationQuantityModel() {
		occupationQuantityModel = new CartesianChartModel();
		List<NewspaperDto> newspapers = getAllNewspapers();

		for (int i = 0; i < newspapers.size(); i++) {
			NewspaperDto newspaper = newspapers.get(i);
			ChartSeries cs = new ChartSeries();
			cs.setLabel(newspaper.getName());
			for (OccupationSummaryDto dto : getOccupationList()) {
				cs.set(dto.getOccupation().getName(),
						dto.getQuantityForNewspaper(i));
			}
			occupationQuantityModel.addSeries(cs);
		}
	}

	private void initEducationQuantityModel() {
		educationQuantityModel = new CartesianChartModel();
		List<NewspaperDto> newspapers = getAllNewspapers();

		for (int i = 0; i < newspapers.size(); i++) {
			NewspaperDto newspaper = newspapers.get(i);
			ChartSeries cs = new ChartSeries();
			cs.setLabel(newspaper.getName());
			for (EducationSummaryDto dto : getEducationList()) {
				cs.set(dto.getEducation().getName(),
						dto.getQuantityForNewspaper(i));
			}
			educationQuantityModel.addSeries(cs);
		}
	}

	private List<NewspaperDto> getAllNewspapers() {
		if (newspapers == null) {
			newspapers = new ArrayList<NewspaperDto>();
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
				newspapers = newspaperService.getAllList(dq);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return newspapers;
	}

	private List<AgePair> getAllAgeRanges() {
		if (agePairs == null) {
			agePairs = new ArrayList<AgePair>();
			if (condition.getAgeRangeSeqs().length > 0) {
				for (int seq : condition.getAgeRangeSeqs()) {
					agePairs.add(AgePair.allRanges[seq]);
				}
			}
			else
			{
				for (AgePair agePair : AgePair.allRanges) {
					agePairs.add(agePair);
				}
			}
			agePairs.add(new AgePair());
		}

		return agePairs;
	}

	private List<SexDto> getAllSexes() {
		if (sexes == null) {
			try {
				sexes = sexService.getAllList();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sexes.add(new SexDto("Not Recorded"));
		}
		return sexes;
	}

	private List<OccupationDto> getAllOccupations() {
		if (occupations == null) {
			occupations = new ArrayList<OccupationDto>();
			DynamicQuery dq = new DynamicQuery();
			if (condition.getOccupationSeqs().length > 0) {
				int[] seqs = condition.getNewspaperSeqs();
				for (int seq : seqs) {
					Parameter para = new Parameter("seq", "=", seq);
					para.setType(Parameter.OR);
					dq.addParameter(para);
				}
			}
			try {
				occupations = occupationService.getAllList(dq);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			OccupationDto notRecorded = new OccupationDto();
			notRecorded.setName("Not Recorded");
			occupations.add(notRecorded);
		}
		return occupations;
	}

	private List<EducationDto> getAllEducations() {
		if (educations == null) {
			educations = new ArrayList<EducationDto>();
			DynamicQuery dq = new DynamicQuery();
			if (condition.getEducationSeqs().length > 0) {
				int[] seqs = condition.getNewspaperSeqs();
				for (int seq : seqs) {
					Parameter para = new Parameter("seq", "=", seq);
					para.setType(Parameter.OR);
					dq.addParameter(para);
				}
			}
			try {
				educations = educationService.getAllList(dq);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			EducationDto notRecorded = new EducationDto();
			notRecorded.setName("Not Recorded");
			educations.add(notRecorded);
		}
		return educations;
	}

	public void search() throws IOException {
		clear();
	}

	public void clear() {
		agePairs = null;
		sexes = null;
		occupations = null;
		educations = null;
		ageRangeList = null;
		sexList = null;
		occupationList = null;
		educationList = null;
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

	public List<AgeSummaryDto> getAgeRangeList() {
		if (ageRangeList == null) {
			try {
				ageRangeList = new ArrayList<AgeSummaryDto>();
				dates = DateUtil.makeDatePairsByMonth(condition.getFrom(),
						condition.getTo());
				for (AgePair agePair : getAllAgeRanges()) {
					AgeSummaryDto psDto = new AgeSummaryDto();
					psDto.setAgePair(agePair);

					for (NewspaperDto newspaper : getAllNewspapers()) {
						InventorySummaryDto dto = new InventorySummaryDto();
						dto.setNewspaper(newspaper);
						for (DatePair pair : dates) {
							dto.getList().add(
									makeAgeMonthInventory(agePair, newspaper,
											pair));
						}
						psDto.getList().add(dto);
					}
					ageRangeList.add(psDto);
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return ageRangeList == null ? new ArrayList<AgeSummaryDto>()
				: ageRangeList;
	}

	public List<OccupationSummaryDto> getOccupationList() {
		if (occupationList == null) {
			try {
				occupationList = new ArrayList<OccupationSummaryDto>();
				dates = DateUtil.makeDatePairsByMonth(condition.getFrom(),
						condition.getTo());
				for (OccupationDto occupation : getAllOccupations()) {
					OccupationSummaryDto psDto = new OccupationSummaryDto();
					psDto.setOccupation(occupation);
					for (NewspaperDto newspaper : getAllNewspapers()) {
						InventorySummaryDto dto = new InventorySummaryDto();
						dto.setNewspaper(newspaper);
						for (DatePair pair : dates) {
							dto.getList().add(
									makeOccupationMonthInventory(occupation,
											newspaper, pair));
						}
						psDto.getList().add(dto);
					}
					occupationList.add(psDto);
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return occupationList == null ? new ArrayList<OccupationSummaryDto>()
				: occupationList;
	}

	public List<EducationSummaryDto> getEducationList() {
		if (educationList == null) {
			try {
				educationList = new ArrayList<EducationSummaryDto>();
				dates = DateUtil.makeDatePairsByMonth(condition.getFrom(),
						condition.getTo());
				for (EducationDto education : getAllEducations()) {
					EducationSummaryDto psDto = new EducationSummaryDto();
					psDto.setEducation(education);
					for (NewspaperDto newspaper : getAllNewspapers()) {
						InventorySummaryDto dto = new InventorySummaryDto();
						dto.setNewspaper(newspaper);
						for (DatePair pair : dates) {
							dto.getList().add(
									makeEducationMonthInventory(education,
											newspaper, pair));
						}
						psDto.getList().add(dto);
					}
					educationList.add(psDto);
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return educationList == null ? new ArrayList<EducationSummaryDto>()
				: educationList;
	}

	public List<SexSummaryDto> getSexList() {
		if (sexList == null) {
			try {
				sexList = new ArrayList<SexSummaryDto>();
				dates = DateUtil.makeDatePairsByMonth(condition.getFrom(),
						condition.getTo());
				for (SexDto sex : getAllSexes()) {
					SexSummaryDto psDto = new SexSummaryDto();
					psDto.setSex(sex);
					for (NewspaperDto newspaper : getAllNewspapers()) {
						InventorySummaryDto dto = new InventorySummaryDto();
						dto.setNewspaper(newspaper);
						for (DatePair pair : dates) {
							dto.getList()
									.add(makeSexMonthInventory(sex, newspaper,
											pair));
						}
						psDto.getList().add(dto);
					}
					sexList.add(psDto);
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return sexList == null ? new ArrayList<SexSummaryDto>() : sexList;
	}

	private MonthInventory makeAgeMonthInventory(AgePair ageRange,
			NewspaperDto newspaper, DatePair pair) throws IOException {
		InventoryAgeCondition ic = new InventoryAgeCondition();
		ic.setNewspaperSeqs(new int[] { newspaper.getSeq() });
		ic.setAgePair(ageRange);
		ic.setFrom(pair.getFrom());
		ic.setTo(pair.getTo());
		List<InventoryDto> list = inventoryAgeService.getAllList(ic);
		MonthInventory mi = new MonthInventory();
		mi.setDatePair(pair);
		mi.setList(list);
		return mi;
	}

	private MonthInventory makeSexMonthInventory(SexDto sex,
			NewspaperDto newspaper, DatePair pair) throws IOException {
		InventorySexCondition ic = new InventorySexCondition();
		ic.setNewspaperSeqs(new int[] { newspaper.getSeq() });
		ic.setSex(sex.getName());
		ic.setFrom(pair.getFrom());
		ic.setTo(pair.getTo());
		List<InventoryDto> list = inventorySexService.getAllList(ic);
		MonthInventory mi = new MonthInventory();
		mi.setDatePair(pair);
		mi.setList(list);
		return mi;
	}

	private MonthInventory makeOccupationMonthInventory(
			OccupationDto occupation, NewspaperDto newspaper, DatePair pair)
			throws IOException {
		InventoryOccupationCondition ic = new InventoryOccupationCondition();
		ic.setNewspaperSeqs(new int[] { newspaper.getSeq() });
		ic.setOccupationSeq(occupation.getSeq());
		ic.setFrom(pair.getFrom());
		ic.setTo(pair.getTo());
		List<InventoryDto> list = inventoryOccupationService.getAllList(ic);
		MonthInventory mi = new MonthInventory();
		mi.setDatePair(pair);
		mi.setList(list);
		return mi;
	}

	private MonthInventory makeEducationMonthInventory(EducationDto education,
			NewspaperDto newspaper, DatePair pair) throws IOException {
		InventoryEducationCondition ic = new InventoryEducationCondition();
		ic.setNewspaperSeqs(new int[] { newspaper.getSeq() });
		ic.setEducationSeq(education.getSeq());
		ic.setFrom(pair.getFrom());
		ic.setTo(pair.getTo());
		List<InventoryDto> list = inventoryEducationService.getAllList(ic);
		MonthInventory mi = new MonthInventory();
		mi.setDatePair(pair);
		mi.setList(list);
		return mi;
	}

	public ClientSummaryCondition getCondition() {
		return condition;
	}

	public void setCondition(ClientSummaryCondition condition) {
		this.condition = condition;
	}
}
