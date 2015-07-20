package npp.impl.controler;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class EditorController implements Serializable {

	private static final long serialVersionUID = 20111020L;

	private String content;
	private String secondContent;
	private String color = "#33fc14";

	public EditorController() {
		content = "Hi Showcase User";
		secondContent = "This is a second editor";
	}

	public void saveListener() {
		content = content.replaceAll("\\r|\\n", "");

		final FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Content",
		    		content.length() > 150 ? content.substring(0, 100) : content);

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void secondSaveListener() {
		secondContent = secondContent.replaceAll("\\r|\\n", "");

		final FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Second Content",
				secondContent.length() > 150 ? secondContent.substring(0, 100) : secondContent);

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void changeColor() {
		if (color.equals("#1433FC")) {
			color = "#33fc14";
		} else {
			color = "#1433FC";
		}
	}

	public String getContent() {
		return content;
	}

	public void setContent(final String content) {
		this.content = content;
	}

	public String getColor() {
		return color;
	}

	public void setColor(final String color) {
		this.color = color;
	}

	public String getSecondContent() {
		return secondContent;
	}

	public void setSecondContent(final String secondContent) {
		this.secondContent = secondContent;
	}
}
