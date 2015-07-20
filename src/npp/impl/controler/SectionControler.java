package npp.impl.controler;

import gui.ava.html.image.generator.HtmlImageGenerator;

import java.io.File;
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
import npp.condition.Parameter;
import npp.condition.SectionCondition;
import npp.dto.ArticleDto;
import npp.dto.IssueDto;
import npp.dto.SectionDto;
import npp.faces.ArticleListModel;
import npp.faces.SectionListModel;
import npp.spec.manager.SessionManager;
import npp.spec.service.ArticleService;
import npp.spec.service.IssueService;
import npp.spec.service.SectionService;
import npp.utils.DialogUtil;
import npp.utils.FileUtil;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@SessionScoped
public class SectionControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 3396584269069259033L;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private List<SectionDto> baseList = null;
	private SectionListModel listModel = null;
	private List<ArticleDto> articleList = null;
	private List<ArticleDto> availableArticleList = null;
	private List<ArticleDto> selectedArticles = null;
	private List<ArticleDto> addedArticles = null;
	private List<ArticleDto> filteredArticles = null;

	private int first;
	private boolean editMode;
	private boolean deleteMode;
	private boolean browseMode;

	private List<SectionDto> selecteds;
	private SectionDto editDto;
	private SectionCondition condition;
	private ArticleDto editArticle;

	@Inject
	private SectionService sectionService;
	@Inject
	private ArticleService articleService;
	@Inject
	private SessionManager sessionManager;
	@Inject
	private IssueService issueService;

	private String previewPhotoName;

	@PostConstruct
	public void init() {
		editMode = false;
		browseMode = false;
		deleteMode = false;
		editDto = new SectionDto();
		editArticle = new ArticleDto();
		condition = new SectionCondition();
		previewPhotoName = "";
	}

	// public SectionListModel getListModel() {
	// if (baseList == null) {
	// try {
	// baseList = sectionService.getAllList(condition.genQuery());
	// listModel = new SectionListModel(baseList);
	// } catch (IOException e) {
	// throw new RuntimeException(e);
	// }
	// }
	// return baseList == null ? new SectionListModel(
	// new ArrayList<SectionDto>()) : listModel;
	// }

	public void startAdd(IssueDto dto) {
		editDto = new SectionDto();
		editDto.setIssue(dto);
		editMode = false;
	}

	public void startEdit(SectionDto dto) {
		editDto = new SectionDto(dto);
		editMode = true;
		clearArticles();
	}

	public void startBrowse(List<SectionDto> sections) {
		baseList = sections;
		listModel = new SectionListModel(baseList);
		browseMode = true;
	}

	public void endBrowse() {
		clear();
	}

	public void startDelete(SectionDto dto) {
		editDto = new SectionDto(dto);
		deleteMode = true;
	}

	public void endDelete() {
		deleteMode = false;
	}

	public void apply() throws IOException {
		System.out.println("apply Section");
		sectionService.update(editDto);
		sessionManager.addGlobalMessageInfo("Section Info has beed updated.",
				null);
		clear();
	}

	public void addSection() throws IOException {
		sectionService.add(editDto);
		DialogUtil.hideDialog("addSectionDialog_w");
	}

	public void preview() throws IOException
	{
		HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
		// imageGenerator.loadUrl("http://www.baidu.com");
		// String htmlstr =
		// "<table width='654' cellpadding='0' cellspacing='0' bordercolor='#FFFFFF'><tr><td><img src='http://www.apkfather.com/yhqserver/images/mdl.jpg'/></td><td><img src='http://www.apkfather.com/yhqserver/images/mdl.jpg'/></td><td><img src='http://www.apkfather.com/yhqserver/images/mdl.jpg'/></td></tr><tr><td><img src='http://www.apkfather.com/yhqserver/images/mdl.jpg'/></td><td><img src='http://www.apkfather.com/yhqserver/images/mdl.jpg'/></td><td><img src='http://www.apkfather.com/yhqserver/images/mdl.jpg'/></td></tr><tr><td><img src='http://www.apkfather.com/yhqserver/images/mdl.jpg'/></td><td><img src='http://www.apkfather.com/yhqserver/images/mdl.jpg'/></td><td><img src='http://www.apkfather.com/yhqserver/images/mdl.jpg'/></td></tr><tr><td><img src='http://www.apkfather.com/yhqserver/images/mdl.jpg'/></td></tr></table>";
		imageGenerator.loadHtml(editDto.getHtml());
		imageGenerator.getBufferedImage();
		previewPhotoName = getPhotoNamePrefix() + ".png";
		imageGenerator.saveAsImage(sessionManager.getThumbnailPath()
				+ previewPhotoName);

	}

	public void saveAsImage() throws IOException {
		HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
		// imageGenerator.loadUrl("http://www.baidu.com");
		// String htmlstr =
		// "<table width='654' cellpadding='0' cellspacing='0' bordercolor='#FFFFFF'><tr><td><img src='http://www.apkfather.com/yhqserver/images/mdl.jpg'/></td><td><img src='http://www.apkfather.com/yhqserver/images/mdl.jpg'/></td><td><img src='http://www.apkfather.com/yhqserver/images/mdl.jpg'/></td></tr><tr><td><img src='http://www.apkfather.com/yhqserver/images/mdl.jpg'/></td><td><img src='http://www.apkfather.com/yhqserver/images/mdl.jpg'/></td><td><img src='http://www.apkfather.com/yhqserver/images/mdl.jpg'/></td></tr><tr><td><img src='http://www.apkfather.com/yhqserver/images/mdl.jpg'/></td><td><img src='http://www.apkfather.com/yhqserver/images/mdl.jpg'/></td><td><img src='http://www.apkfather.com/yhqserver/images/mdl.jpg'/></td></tr><tr><td><img src='http://www.apkfather.com/yhqserver/images/mdl.jpg'/></td></tr></table>";
		imageGenerator.loadHtml(editDto.getHtml());
		imageGenerator.getBufferedImage();
		String newPhotoName = getPhotoNamePrefix() + ".png";
		editDto.setPhotoName(newPhotoName);
		System.out.println("filename: " + newPhotoName);
		imageGenerator.saveAsImage(sessionManager.getThumbnailPath()
				+ newPhotoName);
		System.out.println("filepath: " + sessionManager.getThumbnailPath()
				+ newPhotoName);
		apply();
		// imageGenerator.saveAsHtmlWithMap("hello-world.html",
		// "hello-world.png");
	}

	public void clear() {
		baseList = null;
		selecteds = null;
		browseMode = false;
		deleteMode = false;
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public void delete() throws IOException {
		try {
			sectionService.delete(editDto.getSeq());
			clear();
		} catch (IOException e) {
			logger.error("Section Data delete error.", e);
			sessionManager.addGlobalMessageFatal("Section Data delete error.",
					null);
			throw e;
		}
	}

	public int getStatusListLength() {
		if (listModel == null)
			return 0;
		return listModel.getRowCount();
	}

	public List<SectionDto> getBaseList() throws IOException {
		if (baseList == null) {
			baseList = sectionService.getAllList(condition.genQuery());
			listModel = new SectionListModel(baseList);
		}
		return baseList;
	}

	public void setBaseList(List<SectionDto> baseList) {
		this.baseList = baseList;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public List<SectionDto> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<SectionDto> selecteds) {
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
		IssueCondition ic = new IssueCondition();
		// ic.setNewspaperSeq(condition.getNewspaperSeq());
		ic.setFrom(condition.getFrom());
		ic.setTo(condition.getTo());
		List<IssueDto> list = issueService.getAllList(ic.genQuery());
		for (IssueDto dto : list) {
			condition.getIssues().add(dto);
		}
	}

	public SectionDto getEditDto() {
		return editDto;
	}

	public void setEditDto(SectionDto editDto) {
		this.editDto = editDto;
	}

	public String getPhotoNamePrefix() {
		String name = editDto.getIssue().getNewspaper().getName()
				.replaceAll("[ ]+", "_")
				+ File.separator
				+ editDto.getIssue().getNumber()
				+ File.separator + editDto.getNumber() + Math.random();
		return name;
	}

	public void uploadPhoto(FileUploadEvent event) {

		UploadedFile uploadFile;
		try {
			uploadFile = event.getFile();
			String newFilepath = getPhotoNamePrefix()
					+ FileUtil.getSuffix(uploadFile.getFileName());
			FileUtil.copyFile(sessionManager.getThumbnailPath() + newFilepath,
					uploadFile.getInputstream());
			System.out.println("status : " + editDto.getStatus());
			editDto.setPhotoName(newFilepath);
			sectionService.update(editDto);
			sessionManager.addGlobalMessageInfo(uploadFile.getFileName()
					+ " is uploaded.", null);
		} catch (IOException e) {
			e.printStackTrace();
			sessionManager.addGlobalMessageInfo(event.getFile().getFileName()
					+ " uploaded fail.", null);
		}
	}

	public boolean isBrowseMode() {
		return browseMode;
	}

	public void setBrowseMode(boolean browseMode) {
		this.browseMode = browseMode;
	}

	public void changeIssue(int issueSeq) {
		if (issueSeq == 0) {
			baseList = new ArrayList<SectionDto>();
			listModel = new SectionListModel(baseList);
			return;
		}
		clear();
		condition.clear();
		condition.setIssueSeq(issueSeq);
	}

	public void clearAll() {
		clear();
		condition.clear();
	}

	public SectionCondition getCondition() {
		return condition;
	}

	public void setCondition(SectionCondition condition) {
		this.condition = condition;
	}

	public ArticleListModel getArticleListModel() {
		if (articleList == null) {
			DynamicQuery dq = new DynamicQuery();
			dq.addParameter(new Parameter("section_seq", "=", editDto.getSeq()));
			try {
				articleList = articleService.getAllList(dq);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				articleList = null;
			}
		}
		return articleList == null ? new ArticleListModel(
				new ArrayList<ArticleDto>())
				: new ArticleListModel(articleList);
	}

	public void setArticleList(List<ArticleDto> articleList) {
		this.articleList = articleList;
	}

	public ArticleListModel getAvailableArticleListModel() {
		if (availableArticleList == null) {
			DynamicQuery dq = new DynamicQuery();
			dq.addParameter(new Parameter("section_seq", "=", 0));
			dq.addParameter(new Parameter("status_seq", "=", 1));

			try {
				availableArticleList = articleService.getAllList(dq);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				availableArticleList = null;
			}
		}
		return availableArticleList == null ? new ArticleListModel(
				new ArrayList<ArticleDto>()) : new ArticleListModel(
				availableArticleList);
	}

	public void setAvailableArticleList(List<ArticleDto> availableArticleList) {
		this.availableArticleList = availableArticleList;
	}

	public ArticleDto getEditArticle() {
		return editArticle;
	}

	public void setEditArticle(ArticleDto editArticle) {
		this.editArticle = editArticle;
	}

	public void startBrowseArticle(ArticleDto article) {
		editArticle = new ArticleDto(article);
	}

	public List<ArticleDto> getSelectedArticles() {
		return selectedArticles;
	}

	public void setSelectedArticles(List<ArticleDto> selectedArticles) {
		this.selectedArticles = selectedArticles;
	}

	public List<ArticleDto> getAddedArticles() {
		return addedArticles;
	}

	public void setAddedArticles(List<ArticleDto> addedArticles) {
		this.addedArticles = addedArticles;
	}

	public void addArticles() throws IOException {
		if (addedArticles != null) {
			for (ArticleDto dto : addedArticles) {
				dto.setSection(editDto);
				articleService.update(dto);
			}
		}
		clearArticles();
	}

	public void deleteArticle() throws IOException {
		if (selectedArticles != null) {
			for (ArticleDto dto : selectedArticles) {
				dto.setSection(new SectionDto());
				articleService.update(dto);
			}
		}
		clearArticles();
	}

	private void clearArticles() {
		articleList = null;
		availableArticleList = null;
		selectedArticles = null;
		addedArticles = null;
		filteredArticles = null;
	}

	public List<ArticleDto> getFilteredArticles() {
		return filteredArticles;
	}

	public void setFilteredArticles(List<ArticleDto> filteredArticles) {
		this.filteredArticles = filteredArticles;
	}

	public void numberValidate(FacesContext context, UIComponent ui,
			Object value) throws IOException {
		int number = (Integer) value;
		if (number <= 0) {
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Page number must be positive.", null));
		}
		if (editMode && editDto.getNumber() == number)
			return;

		DynamicQuery dq = new DynamicQuery();
		dq.addParameter(new Parameter("issue_seq", "=", editDto.getIssue()
				.getSeq()));
		List<SectionDto> list = sectionService.getAllList(dq);
		for (SectionDto dto : list) {
			if (dto.getNumber() == number) {
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Section Number has existed.", null));
			}
		}
	}

	public void importArticle()
	{
		editDto.setHtml(editDto.getHtml() + "\n" + editArticle.getHtml());
	}

	public String getPreviewPhotoName() {
		return previewPhotoName;
	}

	public void setPreviewPhotoName(String previewPhotoName) {
		this.previewPhotoName = previewPhotoName;
	}
}
