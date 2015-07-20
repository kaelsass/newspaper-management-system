package npp.impl.controler;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;


@Named
@SessionScoped
public class TestControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 469282104365892582L;


	private String id;

	public void loadId()
	{
		System.out.println("id: " + id);
	}

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


}
