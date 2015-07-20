package npp.impl.controler;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import npp.dto.PunchDto;
import npp.spec.manager.SessionManager;
import npp.spec.service.PunchService;


@Named
@RequestScoped
public class PunchControler implements Serializable {


	/**
	 *
	 */
	private static final long serialVersionUID = 535854861133713243L;

	private boolean in;

	private String note;

	private Date current;
	private PunchDto editDto;

	@Inject
	private PunchService punchService;
	@Inject
	private SessionManager sessionManager;

	@PostConstruct
	public void init()
	{
		current = new Date();
		;
		try {
			List<PunchDto> list = punchService.getUnfinishedList();
			if(list.size() == 0)
			{
				in = true;
				editDto = new PunchDto(0, sessionManager.getLoginUser().getEmployee(), getCurrentDate() + " " + getCurrentTime(), "", "", "");
			}
			else
			{
				in = false;
				editDto = list.get(0);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
//	public static void main(String[] args)
//	{
//		Date d = new Date();
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	}
	public String getCurrentDate()
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(current);
	}
	public String getCurrentTime()
	{
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		return df.format(current);
	}

	public void apply() throws IOException
	{
		if(isIn())
		{
			editDto.setPunchInNote(note);
			punchService.add(editDto);
		}
		else
		{
			editDto.setPunchOutTime(getCurrentDate() + " " + getCurrentTime());
			editDto.setPunchOutNote(note);
			punchService.update(editDto);
		}
	}
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	public PunchDto getEditDto() {
		return editDto;
	}
	public void setEditDto(PunchDto editDto) {
		this.editDto = editDto;
	}
	public boolean isIn() {
		return in;
	}
	public void setIn(boolean in) {
		this.in = in;
	}

}
