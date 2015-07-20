package npp.impl.controler;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import npp.spec.manager.SessionManager;


@Named
@RequestScoped
public class MainMenuControler  {

	@Inject
	private SessionManager sessionManager;


	private String nextMenu;

	public String getUserName() throws IOException{
		return sessionManager.getLoginUser().getUserName();
	}

	public String transfer(){
		return nextMenu + "?faces-redirect=true";
	}


	public String getNextMenu() {
		return nextMenu;
	}

	public void setNextMenu(String nextMenu) {
		this.nextMenu = nextMenu;
	}

	public String logoff() throws IOException{
		return "/faces/employee.jsf?faces-redirect=true";
	}


}
