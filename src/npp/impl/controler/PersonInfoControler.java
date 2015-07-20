package npp.impl.controler;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import npp.dto.PersonInfoDto;
import npp.dto.WorkInfoDto;
import npp.spec.manager.SessionManager;
import npp.spec.service.PersonInfoService;
import npp.spec.service.WorkInfoService;
import npp.utils.FileUtil;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;


@Named
@SessionScoped
public class PersonInfoControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 7675035252102037709L;

	@Inject
	private SessionManager sessionManager;
	@Inject
	private PersonInfoService personInfoService;
	@Inject
	private WorkInfoService workInfoService;

	private PersonInfoDto personInfo;
	private WorkInfoDto workInfo;

	private boolean editPersonInfoMode = false;
	private boolean editWorkInfoMode = false;


	private int activeIndex = 1;

	public void init()
	{
		clear();
		activeIndex = 1;
	}
	public void startEditPerson()
	{
		editPersonInfoMode = true;
	}
	public void startEditWork()
	{
		editWorkInfoMode = true;
	}

	public void apply() throws IOException
	{
		String name = "";
		if(editWorkInfoMode)
		{
			workInfoService.update(workInfo);
			name = workInfo.getName();
		}
		else
		{
			personInfoService.update(personInfo);
			name = personInfo.getName();
		}

		sessionManager.addGlobalMessageInfo("Information for " + name+ " has been updated.", null);
		clear();
	}

	public void clear()
	{
		editWorkInfoMode = false;
		editPersonInfoMode = false;
	}

	public void clearAll()
	{
		personInfo = null;
		workInfo = null;
		editWorkInfoMode = false;
		editPersonInfoMode = false;
	}

	//not called
	public void clearAllAndRedirect() throws IOException
	{
		clearAll();
		FacesContext.getCurrentInstance().getExternalContext().redirect("employeeInfo.jsf");
	}


	public WorkInfoDto getWorkInfo() throws IOException {
		if(workInfo == null)
			workInfo = workInfoService.findByID(sessionManager.getLoginUser().getEmployee().getId());
		return workInfo;
	}


	public void setWorkInfo(WorkInfoDto workInfo) {
		this.workInfo = workInfo;
	}


	public PersonInfoDto getPersonInfo() throws IOException {
		if(personInfo == null)
			personInfo = personInfoService.findByID(sessionManager.getLoginUser().getEmployee().getId());
		return personInfo;
	}


	public void setPersonInfo(PersonInfoDto personInfo) {
		this.personInfo = personInfo;
	}

	public String getPhotoPath() throws IOException
	{
		return sessionManager.getPhotoPath();
	}

	public void uploadPhoto(FileUploadEvent event) {

		UploadedFile uploadFile;
		try {
			uploadFile = event.getFile();
			String newName = personInfo.getId() + Math.random() + FileUtil.getSuffix(uploadFile.getFileName());
			FileUtil.copyFile(sessionManager.getPhotoPath() + newName, uploadFile
					.getInputstream());
			personInfo.setPhotoName(newName);
			sessionManager.addGlobalMessageInfo(uploadFile
					.getFileName() + " is uploaded.", null);
		} catch (IOException e) {
			e.printStackTrace();
			sessionManager.addGlobalMessageInfo(event.getFile()
					.getFileName() + " uploaded fail.", null);
		}
	}

	public int getActiveIndex() {
		return activeIndex;
	}

	public void setActiveIndex(int activeIndex) {
		this.activeIndex = activeIndex;
	}
	public boolean isEditWorkInfoMode() {
		return editWorkInfoMode;
	}
	public void setEditWorkInfoMode(boolean editWorkInfoMode) {
		this.editWorkInfoMode = editWorkInfoMode;
	}
	public boolean isEditPersonInfoMode() {
		return editPersonInfoMode;
	}
	public void setEditPersonInfoMode(boolean editPersonInfoMode) {
		this.editPersonInfoMode = editPersonInfoMode;
	}
	public void startLook(ActionEvent event) throws IOException {
		WorkInfoDto selectedDto = (WorkInfoDto) event.getComponent()
				.getAttributes().get("employee");
		workInfo = new WorkInfoDto(selectedDto);
		PersonInfoDto dto = personInfoService.findByID(selectedDto.getId());
		personInfo = new PersonInfoDto(dto);
	}
}
