package npp.condition;

import java.util.Date;



public class ClientSummaryCondition {
	private int[] ageRangeSeqs;
	private String[] sexs;
	private int[] occupationSeqs;
	private int[] educationSeqs;
	private int[] newspaperSeqs;
	private Date from;
	private Date to;


	// You must declared default constructor for Framework.
	public ClientSummaryCondition() {
		clear();
	}

	public void clear()
	{
		this.ageRangeSeqs = new int[0];
		this.newspaperSeqs = new int[0];
		this.sexs = new String[0];
		this.occupationSeqs = new int[0];
		this.educationSeqs = new int[0];
		this.from = null;
		this.to = null;
	}


	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public int[] getNewspaperSeqs() {
		return newspaperSeqs;
	}

	public void setNewspaperSeqs(int[] newspaperSeqs) {
		this.newspaperSeqs = newspaperSeqs;
	}

	public int[] getAgeRangeSeqs() {
		return ageRangeSeqs;
	}

	public void setAgeRangeSeqs(int[] ageRangeSeqs) {
		this.ageRangeSeqs = ageRangeSeqs;
	}

	public String[] getSexs() {
		return sexs;
	}

	public void setSexs(String[] sexs) {
		this.sexs = sexs;
	}

	public int[] getOccupationSeqs() {
		return occupationSeqs;
	}

	public void setOccupationSeqs(int[] occupationSeqs) {
		this.occupationSeqs = occupationSeqs;
	}

	public int[] getEducationSeqs() {
		return educationSeqs;
	}

	public void setEducationSeqs(int[] educationSeqs) {
		this.educationSeqs = educationSeqs;
	}
}
