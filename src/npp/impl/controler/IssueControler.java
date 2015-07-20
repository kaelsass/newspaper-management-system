package npp.impl.controler;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import npp.condition.DynamicQuery;
import npp.condition.IssueCondition;
import npp.condition.NewspaperCondition;
import npp.condition.Parameter;
import npp.dto.IssueDto;
import npp.dto.IssueSectionDto;
import npp.dto.NewspaperDto;
import npp.dto.NewspaperIssueDto;
import npp.dto.SectionDto;
import npp.spec.service.IssueService;
import npp.spec.service.NewspaperService;
import npp.spec.service.SectionService;
import npp.utils.DialogUtil;


@Named
@SessionScoped
public class IssueControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -2402509415354556142L;

	private List<NewspaperIssueDto> newspaperIssueList = null;
//	private List<IssueSectionDto> issueSectionList = null;

	private boolean addMode;
	private boolean editMode;
	private boolean deleteMode;
	private boolean deleteSectionMode;
	private int viewMode;

	private IssueDto editDto;
	private SectionDto selectedSection;
	private SectionDto addSection;
	private IssueCondition condition;

	@Inject
	private IssueService issueService;
	@Inject
	private NewspaperService newspaperService;
	@Inject
	private SectionService sectionService;

	@PostConstruct
	public void init(){
		viewMode = 0;
		addMode = false;
		editMode = false;
		deleteMode = false;
		editDto = new IssueDto();
		condition = new IssueCondition();
		addSection = new SectionDto();
		selectedSection = new SectionDto();
	}

	// public IssueListModel getListModel() {
	// if (baseList == null) {
	// try {
	// baseList = issueService.getAllList(condition.genQuery());
	// listModel = new IssueListModel(baseList);
	// } catch (IOException e) {
	// throw new RuntimeException(e);
	// }
	// }
	// return baseList == null ? new IssueListModel(
	// new ArrayList<IssueDto>()) : listModel;
	// }

	public void startAdd() throws IOException {
		editDto = new IssueDto();
//		editDto = issueService.getNextIssue(newspaper.getSeq());
//		editDto.setNewspaper(newspaper);
		addMode = true;
	}
	public void selectNewspaper() throws IOException
	{
		if(editDto.getNewspaper().getSeq() == 0)
			editDto = new IssueDto();
		else
		{
			NewspaperDto newspaperDto = newspaperService.findBySeq(editDto.getNewspaper().getSeq());
			editDto = issueService.getNextIssue(newspaperDto.getSeq());
			editDto.setNewspaper(newspaperDto);
		}
	}

	public void startEdit(IssueDto dto) {
		editDto = new IssueDto(dto);
		editMode = true;
	}

	public void startDelete(IssueDto dto) {
		editDto = new IssueDto(dto);
		deleteMode = true;
	}

	public void endDelete() {
		deleteMode = false;
	}

	public void startDeleteSection(SectionDto dto) {
		this.selectedSection = new SectionDto(dto);
		deleteSectionMode = true;
	}

	public void endDeleteSection() {
		deleteSectionMode = false;
	}

	public void apply() throws IOException {
		if (editMode)
			issueService.update(editDto);
		else
			issueService.add(editDto);
		clear();
	}

	public void clear() {
		System.out.println("clear issueControler");
		editDto = new IssueDto();
		addMode = false;
		editMode = false;
		deleteMode = false;
		deleteSectionMode = false;
		selectedSection = new SectionDto();
		newspaperIssueList = null;
	}

	public void clearAll() {
		clear();
		condition.clear();
	}

	public void delete() throws IOException {
		issueService.delete(editDto.getSeq());
		clear();
	}

	public void deleteSection() throws IOException {
		sectionService.delete(selectedSection.getSeq());
		clear();
	}

	// public int getStatusListLength() {
	// if (listModel == null)
	// return 0;
	// return listModel.getRowCount();
	// }

	public List<NewspaperIssueDto> getNewspaperIssueList() throws IOException {
		if (newspaperIssueList == null) {
			newspaperIssueList = new ArrayList<NewspaperIssueDto>();
			try {
				NewspaperCondition nc = new NewspaperCondition();
				nc.setNewspaperSeqs(condition.getNewspaperSeqs());
				List<NewspaperDto> newspapers = newspaperService.getAllList(nc.genQuery());
				for(NewspaperDto newspaper : newspapers)
				{
					NewspaperIssueDto newDto = new NewspaperIssueDto();
					newDto.setNewspaper(newspaper);
					IssueCondition ic = new IssueCondition();
					ic.setNewspaperSeqs(new int[]{newspaper.getSeq()});
					List<IssueDto> issues = issueService.getAllList(ic.genQuery());
					List<IssueSectionDto> isds = new ArrayList<IssueSectionDto>();
					for (IssueDto issue : issues) {
						IssueSectionDto isd = new IssueSectionDto();
						isd.setIssue(issue);
						DynamicQuery dq = new DynamicQuery();
						dq.addParameter(new Parameter("issue_seq", "=", issue
								.getSeq()));
						List<SectionDto> sections = sectionService.getAllList(dq);
						isd.setSections(sections);
						isds.add(isd);
					}
					newDto.setIssueSectionList(isds);
					newspaperIssueList.add(newDto);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return newspaperIssueList == null ? new ArrayList<NewspaperIssueDto>() : newspaperIssueList;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public boolean isDeleteMode() {
		return deleteMode;
	}

	public void setDeleteMode(boolean deleteMode) {
		this.deleteMode = deleteMode;
	}

	public void search() throws IOException {
		clear();
	}


	public IssueCondition getCondition() {
		return condition;
	}

	public void setCondition(IssueCondition condition) {
		this.condition = condition;
	}

	public List<IssueSectionDto> getIssueSectionList() throws IOException {
		List<IssueSectionDto> list = new ArrayList<IssueSectionDto>();
		for(NewspaperIssueDto dto : getNewspaperIssueList())
		{
			list.addAll(dto.getIssueSectionList());
		}
		return list;
	}

	public int getIssueCount() throws IOException {
		IssueCondition ic = new IssueCondition();
		ic.setNewspaperSeqs(new int[]{editDto.getNewspaper().getSeq()});
		List<IssueDto> list = issueService.getAllList(ic.genQuery());
		return list.size();
	}

	public boolean isDeleteSectionMode() {
		return deleteSectionMode;
	}

	public void setDeleteSectionMode(boolean deleteSectionMode) {
		this.deleteSectionMode = deleteSectionMode;
	}

	public SectionDto getSelectedSection() {
		return selectedSection;
	}

	public void setSelectedSection(SectionDto selectedSection) {
		this.selectedSection = selectedSection;
	}

	public boolean isAddMode() {
		return addMode;
	}

	public void setAddMode(boolean addMode) {
		this.addMode = addMode;
	}

	public IssueDto getEditDto() {
		return editDto;
	}

	public void setEditDto(IssueDto editDto) {
		this.editDto = editDto;
	}

	public int getViewMode() {
		return viewMode;
	}

	public void setViewMode(int viewMode) {
		this.viewMode = viewMode;
	}

	public void numberValidate(FacesContext context, UIComponent ui, Object value) throws IOException
	{
		int number = (Integer) value;
		DynamicQuery dq = new DynamicQuery();
		dq.addParameter(new Parameter("num", "=", number));
		dq.addParameter(new Parameter("newspaper_seq", "=", editDto.getNewspaper().getSeq()));
		List<IssueDto> list = issueService.getAllList(dq);
		if(list.size() > 0)
		{
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Issue Number has existed.", null));
		}
	}
	public void seqValidate(FacesContext context, UIComponent component,
			Object value) {
		int seq = (Integer) value;

		if (seq <= 0) {
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Issue is required", null));
		}
	}

	public SectionDto getAddSection() {
		return addSection;
	}

	public void setAddSection(SectionDto addSection) {
		this.addSection = addSection;
	}
	public void startAddSection(IssueDto issue)
	{
		addSection = new SectionDto();
		addSection.setIssue(issue);
	}
	public void sectionNumberValidate(FacesContext context, UIComponent ui, Object value) throws IOException
	{
		int number = (Integer) value;
		if (number <= 0)
		{
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Page number must be positive.", null));
		}

		DynamicQuery dq = new DynamicQuery();
		dq.addParameter(new Parameter("issue_seq", "=", addSection.getIssue().getSeq()));
		List<SectionDto> list = sectionService.getAllList(dq);
		for(SectionDto dto : list)
		{
			if(dto.getNumber() == number)
			{
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Section Number has existed.", null));
			}
		}
	}
	public void applyAddSection() throws IOException
	{
		sectionService.add(addSection);
		DialogUtil.hideDialog("addSectionDialog_w");
		clear();
	}

}
