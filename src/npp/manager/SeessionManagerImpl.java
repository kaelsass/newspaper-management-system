package npp.manager;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import npp.dto.UserDto;
import npp.spec.manager.SessionManager;
import npp.spec.service.UserService;


@Stateful
@Named
@SessionScoped
public class SeessionManagerImpl implements SessionManager, Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -7594833796034520517L;

	@Inject
	private UserService userService;

	private UserDto user = null;
	private String photoDir = null;

	@Override
	public String getPhotoPath() throws IOException {
		if(photoDir == null)
			photoDir = FacesContext.getCurrentInstance().getExternalContext()
	            .getRealPath("/photo/") + File.separator;
		createDir(photoDir);
		return photoDir;
	}

	private void createDir(String dir) {
		File f = new File(dir);
		if(!f.exists())
			f.mkdirs();
	}

	@Override
	public UserDto getLoginUser() throws IOException {

		if(user == null){
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			String username = (String) externalContext.getSessionMap().get("username");
			user = userService.findById(username);
		}
		return user;
	}

	@Override
	public void redirect(String url)  throws IOException {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		externalContext.redirect(url);
	}

	@Override
	public void addGlobalMessageFatal(String summary, String detail){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, summary, detail));
	}

	@Override
	public void addGlobalMessageWarn(String summary, String detail){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, summary, detail));
	}

	@Override
	public void addGlobalMessageInfo(String summary, String detail){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
	}

	@Override
	public String getThumbnailPath() throws IOException {
		if(photoDir == null)
			photoDir = FacesContext.getCurrentInstance().getExternalContext()
	            .getRealPath("/thumbnail/") + File.separator;
		createDir(photoDir);
		return photoDir;
	}
	@Override
	public void clear() {
		user = null;
	}
}
