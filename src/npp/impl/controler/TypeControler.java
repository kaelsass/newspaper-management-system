package npp.impl.controler;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import npp.dto.StatusDto;
import npp.spec.service.TypeService;

@Named
@SessionScoped
public class TypeControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -3131619932344230970L;

	private List<StatusDto> baseList = null;

	@Inject
	private TypeService typeService;

	public List<StatusDto> getBaseList() throws IOException {
		if (baseList == null) {
			baseList = typeService.getAllList();
		}
		return baseList;
	}

	public void setBaseList(List<StatusDto> baseList) {
		this.baseList = baseList;
	}

}
