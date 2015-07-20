package npp.impl.controler;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import npp.condition.ArticleCondition;
import npp.condition.AuthorCondition;
import npp.condition.DynamicQuery;
import npp.condition.Parameter;
import npp.dto.ArticleDto;
import npp.dto.AuthorDto;
import npp.dto.IssueDto;
import npp.dto.SectionDto;
import npp.faces.ArticleListModel;
import npp.spec.manager.SessionManager;
import npp.spec.service.ArticleService;
import npp.spec.service.AuthorService;
import npp.spec.service.IssueService;
import npp.spec.service.SectionService;
import npp.utils.FileUtil;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Named
@SessionScoped
public class ArticleControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -7002349436543401334L;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private List<ArticleDto> baseList = null;
	private ArticleListModel listModel = null;
	private List<ArticleDto> allList = null;
	private List<IssueDto> issueList = null;
	private List<SectionDto> sectionList = null;

	private int first;
	private boolean editMode;
	private boolean deleteMode;
	private boolean editStatus;

	private List<ArticleDto> selecteds;
	private ArticleDto editDto;
	private ArticleCondition condition;

	@Inject
	private ArticleService articleService;
	@Inject
	private AuthorService authorService;
	@Inject
	private IssueService issueService;
	@Inject
	private SectionService sectionService;
	@Inject
	private SessionManager sessionManager;

	private AuthorCondition authorCondition;



	private UploadedFile uploadFile;

	@PostConstruct
	public void init(){
		editMode = false;
		deleteMode = false;
		editStatus = false;
		editDto = new ArticleDto();
		condition = new ArticleCondition();
		authorCondition = new AuthorCondition();
	}

	public ArticleListModel getListModel() {
		if (baseList == null) {
			try {
				baseList = articleService.getAllList(condition.genQuery());
				listModel = new ArticleListModel(baseList);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return baseList == null ? new ArticleListModel(
				new ArrayList<ArticleDto>()) : listModel;
	}

	public void startAdd() {
		editDto = new ArticleDto();
	}

	public void startEdit(ActionEvent e) {
		ArticleDto selected = (ArticleDto) e.getComponent().getAttributes()
				.get("article");
		editDto = new ArticleDto(selected);
		editMode = true;
	}

	public void startDelete() {
		deleteMode = true;
	}

	public void endDelete() {
		deleteMode = false;
	}

	public void apply() throws IOException {
		AuthorDto authorDto = authorService.findByName(editDto.getAuthor().getName());
		editDto.setAuthor(authorDto);
		if (editMode)
			articleService.update(editDto);
		else
			articleService.add(editDto);
		clear();
	}

	public void clear() {
		baseList = null;
		allList = null;
		editDto = new ArticleDto();
		selecteds = null;
		editMode = false;
		deleteMode = false;
		editStatus = false;
		condition.getAuthors().clear();
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public void delete() throws IOException {
		try {
			for (ArticleDto status : selecteds) {
				articleService.delete(status.getSeq());
			}
			clear();
		} catch (IOException e) {
			logger.error("Article Date delete error.", e);
			sessionManager.addGlobalMessageFatal("Article Data delete error.",
					null);
			throw e;
		}
	}

	public int getStatusListLength() {
		if (listModel == null)
			return 0;
		return listModel.getRowCount();
	}

	public List<ArticleDto> getBaseList() throws IOException {
		if (baseList == null) {
			baseList = articleService.getAllList(condition.genQuery());
			listModel = new ArticleListModel(baseList);
		}
		return baseList;
	}

	public void setBaseList(List<ArticleDto> baseList) {
		this.baseList = baseList;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public List<ArticleDto> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<ArticleDto> selecteds) {
		this.selecteds = selecteds;
	}

	public boolean isDeleteMode() {
		return deleteMode;
	}

	public void setDeleteMode(boolean deleteMode) {
		this.deleteMode = deleteMode;
	}

	public void search() throws IOException {
		clear();
		authorCondition.setName(condition.getAuthorName());
		List<AuthorDto> list = authorService.getAllList(authorCondition
				.genQuery());
		for (AuthorDto dto : list) {
			condition.getAuthors().add(dto);
		}
	}

	public ArticleCondition getCondition() {
		return condition;
	}

	public void setCondition(ArticleCondition condition) {
		this.condition = condition;
	}

	public ArticleDto getEditDto() {
		return editDto;
	}

	public void setEditDto(ArticleDto editDto) {
		this.editDto = editDto;
	}

	public boolean isEditStatus() {
		return editStatus;
	}

	public void setEditStatus(boolean editStatus) {
		this.editStatus = editStatus;
	}

	public void startEditStatus(ArticleDto dto) {
		editStatus = true;
		editDto = new ArticleDto(dto);
	}

	public void applyStatus(ArticleDto dto) throws IOException {
		articleService.update(dto);
		clear();
	}

	public List<ArticleDto> getAllList(){
		if (allList == null) {
			ArticleCondition ac = new ArticleCondition();
			try {
				allList = articleService.getAllList(ac.genQuery());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				allList = null;
			}
		}
		return allList == null? new ArrayList<ArticleDto>() : allList;
	}

	public void setAllList(List<ArticleDto> allList) {
		this.allList = allList;
	}

	public List<String> completeTitle(String query) {
		List<String> list = new ArrayList<String>();
		for (ArticleDto dto : getAllList()) {
			if (dto.getTitle().toLowerCase().contains(query.toLowerCase()))
				list.add(dto.getTitle());
		}
		return list;
	}

	public void upload(FileUploadEvent event) throws IOException {
		uploadFile = event.getFile();
		if (uploadFile != null) {
			String content = "";
			if (FileUtil.isWord(uploadFile.getFileName())) {
				content = FileUtil.readWordFromInputstream(uploadFile
						.getInputstream());
			} else if (FileUtil.isTxt(uploadFile.getFileName())) {
				content = FileUtil.readTxtFromInputStream(uploadFile
						.getInputstream());
			}
			else
			{
				content = editDto.getHtml();
			}
			editDto.setHtml(content);
			FacesMessage message = new FacesMessage("Succesful",
					uploadFile.getFileName() + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	public List<IssueDto> getIssueList() {
		if(issueList != null)
			return issueList;
		DynamicQuery dq = new DynamicQuery();
		dq.addParameter(new Parameter("newspaper_seq", "=", editDto.getSection().getIssue().getNewspaper().getSeq()));
		try {
			issueList = issueService.getAllList(dq);
		} catch (IOException e) {
			e.printStackTrace();
			issueList = new ArrayList<IssueDto>();
		}
		return issueList;
	}

	public void setIssueList(List<IssueDto> issueList) {
		this.issueList = issueList;
	}

	public List<SectionDto> getSectionList() {
		if(sectionList != null)
			return sectionList;
		if(!getIssueList().contains(editDto.getSection().getIssue()))
		{
			clearSection();
			return sectionList;
		}
		DynamicQuery dq = new DynamicQuery();
		dq.addParameter(new Parameter("issue_seq", "=", editDto.getSection().getIssue().getSeq()));
		try {
			sectionList = sectionService.getAllList(dq);
		} catch (IOException e) {
			e.printStackTrace();
			sectionList = new ArrayList<SectionDto>();
		}
		return sectionList;
	}

	public void setSectionList(List<SectionDto> sectionList) {
		this.sectionList = sectionList;
	}
	public void clearIssueSection()
	{
		issueList = null;
		sectionList = null;
	}
	public void clearSection()
	{
		sectionList = null;
	}
}
