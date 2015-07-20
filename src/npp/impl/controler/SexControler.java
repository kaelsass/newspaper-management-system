package npp.impl.controler;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import npp.dto.SexDto;
import npp.spec.service.SexService;


@Named
@SessionScoped
public class SexControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 469282104365892582L;


	private List<SexDto> baseList = null;

	@Inject
	private SexService sexService;

	public List<SexDto> getBaseList() throws IOException {
		if (baseList == null)
			baseList = sexService.getAllList();
		return baseList;
	}

	public void setBaseList(List<SexDto> baseList) {
		this.baseList = baseList;
	}
}
