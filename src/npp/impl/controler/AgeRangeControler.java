package npp.impl.controler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import npp.dto.AgePair;


@Named
@SessionScoped
public class AgeRangeControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6156728038843400463L;


	private List<AgePair> baseList = null;

	public List<AgePair> getBaseList() {
		if(baseList == null)
		{
			baseList = new ArrayList<AgePair>();
			for(AgePair agePair: AgePair.allRanges)
			baseList.add(agePair);
		}
		return baseList;
	}

	public void setBaseList(List<AgePair> baseList) {
		this.baseList = baseList;
	}

}
