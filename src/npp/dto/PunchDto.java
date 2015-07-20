package npp.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PunchDto {
	private int seq;
	private WorkInfoDto employee;
	private String punchInTime;
	private String punchInNote;
	private String punchOutTime;
	private String punchOutNote;

	// You must declared default constructor for Framework.
	public PunchDto() {
		seq = 0;
		employee = new WorkInfoDto();
		this.punchInTime = "";
		this.punchInNote = "";
		this.punchOutTime = "";
		this.punchOutNote = "";
	}

	public PunchDto(int seq, WorkInfoDto employee, String inTime,
			String inNote, String outTime, String outNote) {
		this.seq = seq;
		this.employee = (employee == null ? new WorkInfoDto() : employee);
		punchInTime = inTime;
		punchInNote = inNote;
		punchOutTime = outTime;
		punchOutNote = outNote;
	}

	public PunchDto(PunchDto dto) {
		this.seq = dto.getSeq();
		this.employee = dto.getEmployee();
		this.punchInTime = dto.getPunchInTime();
		this.punchInNote = dto.getPunchInNote();
		this.punchOutTime = dto.getPunchOutTime();
		this.punchOutNote = dto.getPunchOutNote();

	}

	public static void main(String[] args) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(df.format(df.parse("2014-12-12 00:00:00")));

	}

	public String getPunchInTime() {
		return punchInTime;
	}

	public void setPunchInTime(String punchInTime) {
		this.punchInTime = punchInTime;
	}

	public String getPunchInNote() {
		return punchInNote;
	}

	public void setPunchInNote(String punchInNote) {
		this.punchInNote = punchInNote;
	}

	public String getPunchOutNote() {
		return punchOutNote;
	}

	public void setPunchOutNote(String punchOutNote) {
		this.punchOutNote = punchOutNote;
	}

	public String getPunchOutTime() {
		return punchOutTime;
	}

	public void setPunchOutTime(String punchOutTime) {
		this.punchOutTime = punchOutTime;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public WorkInfoDto getEmployee() {
		return employee;
	}

	public void setEmployee(WorkInfoDto employee) {
		this.employee = employee;
	}

	public String getDuration() {
		try {
			if (punchInTime == null || punchOutTime == null
					|| punchInTime.equals("") || punchOutTime.equals("")) {
				return "";
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date begin = df.parse(punchInTime);
			Date end = df.parse(punchOutTime);
			double hours = ((double) (end.getTime() - begin.getTime()))
					/ (1000 * 60 * 60);
			return String.format("%.2f", hours);
		} catch (Exception e) {
			return "0.00";
		}
	}
}
