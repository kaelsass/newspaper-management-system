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
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import npp.condition.AuthorCondition;
import npp.condition.DynamicQuery;
import npp.dto.AuthorDto;
import npp.faces.AuthorListModel;
import npp.spec.manager.SessionManager;
import npp.spec.service.AuthorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Named
@SessionScoped
public class AuthorControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -7002349436543401334L;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private List<AuthorDto> baseList = null;
	private AuthorListModel listModel = null;

	private int first;
	private boolean editMode;
	private boolean deleteMode;

	private List<AuthorDto> selecteds;
	private AuthorDto editDto;
	private AuthorCondition condition;

	@Inject
	private AuthorService authorService;
	@Inject
	private SessionManager sessionManager;

	private List<AuthorDto> allList;

	@PostConstruct
	public void init(){
		editMode = false;
		deleteMode = false;
		editDto = new AuthorDto();
		condition = new AuthorCondition();
		try {
			allList = authorService.getAllList(condition.genQuery());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public AuthorListModel getListModel() {
		if (baseList == null) {
			try {
				baseList = authorService.getAllList(condition.genQuery());
				listModel = new AuthorListModel(baseList);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return baseList == null ? new AuthorListModel(
				new ArrayList<AuthorDto>()) : listModel;
	}

	public void startAdd() {
		editDto = new AuthorDto();
		editMode = false;
	}

	public void startEdit(ActionEvent e) {
		AuthorDto dto = (AuthorDto) e.getComponent().getAttributes()
				.get("author");
		editDto = new AuthorDto(dto);
		editMode = true;
	}

	public void startDelete() {
		deleteMode = true;
	}

	public void endDelete() {
		deleteMode = false;
	}

	public void apply() throws IOException {
		if (editMode)
			authorService.update(editDto);
		else
			authorService.add(editDto);
		clear();
	}

	public void clear() {
		baseList = null;
		editDto = new AuthorDto();
		selecteds = null;
		editMode = false;
		deleteMode = false;
		allList = null;
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public void delete() throws IOException {
		try {
			for (AuthorDto dto : selecteds) {
				authorService.delete(dto.getSeq());
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

	public List<AuthorDto> getBaseList() throws IOException {
		if (baseList == null) {
			baseList = authorService.getAllList(condition.genQuery());
			listModel = new AuthorListModel(baseList);
		}
		return baseList;
	}

	public void setBaseList(List<AuthorDto> baseList) {
		this.baseList = baseList;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public List<AuthorDto> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<AuthorDto> selecteds) {
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
	}

	public List<String> completeAuthorName(String query){
		ArrayList<String> filterList = new ArrayList<String>();
		for (AuthorDto dto : getAllList()) {
			if (dto.getName().toLowerCase().contains(query.toLowerCase()))
				filterList.add(dto.getName());
		}
		return filterList;
	}
	public List<String> completeWorkUnit(String query){
		ArrayList<String> filterList = new ArrayList<String>();
		for (AuthorDto dto : getAllList()) {
			if (dto.getWorkUnit().toLowerCase().contains(query.toLowerCase()))
				filterList.add(dto.getWorkUnit());
		}
		return filterList;
	}
	public List<String> completeAddress(String query){
		ArrayList<String> filterList = new ArrayList<String>();
		for (AuthorDto dto : getAllList()) {
			if (dto.getAddress().toLowerCase().contains(query.toLowerCase()))
				filterList.add(dto.getAddress());
		}
		return filterList;
	}

	public AuthorCondition getCondition() {
		return condition;
	}

	public void setCondition(AuthorCondition condition) {
		this.condition = condition;
	}

	public List<AuthorDto> getAllList(){
		if(allList == null)
		{
			try {
				authorService.getAllList(new DynamicQuery());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				allList = null;
			}
		}
		return allList == null ? new ArrayList<AuthorDto>() : allList;
	}

	public void setAllList(List<AuthorDto> allList) {
		this.allList = allList;
	}

	public AuthorDto getEditDto() {
		return editDto;
	}

	public void setEditDto(AuthorDto editDto) {
		this.editDto = editDto;
	}
	public void nameValidate(FacesContext context, UIComponent component, Object value)
	{
		String name = (String) value;
		boolean find = false;
		for(AuthorDto dto: getAllList())
		{
			if(dto.getName().equals(name))
			{
				find = true;
				break;
			}
		}
		if(!find)
		{
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Author Name doesn't exist.", null));
		}
	}

}
