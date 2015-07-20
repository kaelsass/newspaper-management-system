package npp.impl.controler;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import npp.dto.EvalGroupDto;
import npp.spec.manager.SessionManager;
import npp.spec.service.EvalGroupService;

@Named
@SessionScoped
public class EvalGroupControler implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 469282104365892582L;


	private List<EvalGroupDto> baseList = null;
	private int[] seqs;

	@Inject
	private EvalGroupService evalGroupService;
	@Inject
	private SessionManager sessionManager;



	public void apply() throws IOException {
		for(EvalGroupDto dto : getBaseList())
		{
			if(containSeq(dto.getSeq()))
			{
				dto.setChoose(true);
			}
			else
			{
				dto.setChoose(false);
			}
			evalGroupService.update(dto);
		}
		sessionManager.addGlobalMessageInfo("Successfully Updated", null);
	}

	private boolean containSeq(int seq) {
		if(seqs == null)
			return false;
		for(int cur : seqs)
		{
			if(cur == seq)
				return true;
		}
		return false;
	}

	public List<EvalGroupDto> getBaseList() {
		if (baseList == null) {
			try {
				baseList = evalGroupService.getAllList();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return baseList;
	}

	public void setBaseList(List<EvalGroupDto> baseList) {
		this.baseList = baseList;
	}

	public int[] getSeqs() {
		return seqs;
	}

	public void setSeqs(int[] seqs) {
		this.seqs = seqs;
	}
}
