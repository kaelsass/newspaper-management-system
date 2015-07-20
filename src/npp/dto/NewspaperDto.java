package npp.dto;


public class NewspaperDto {
	private int seq;
	private String name;
	private String issn;
	private double issuePrice;
	private double monthPrice;
	private PublicationDateDto pdate;
	public NewspaperDto()
	{
		init(0, "", "", 0, 0, new PublicationDateDto(0, 0));
	}
	public NewspaperDto(int seq, String name, String issn, double dayPrice, double monthPrice,
			PublicationDateDto date) {
		init(seq, name, issn, dayPrice, monthPrice, date);
	}
	public NewspaperDto(NewspaperDto dto) {
		init(dto.getSeq(), dto.getName(), dto.getIssn(), dto.getIssuePrice(), dto.getMonthPrice(), dto.getPdate());
	}
	private void init(int seq, String name, String issn, double issuePrice, double monthPrice, PublicationDateDto pdate)
	{
		this.seq = seq;
		this.name = name;
		this.issn = issn;
		this.issuePrice = issuePrice;
		this.monthPrice = monthPrice;
		this.pdate = pdate;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIssn() {
		return issn;
	}
	public void setIssn(String issn) {
		this.issn = issn;
	}

	public PublicationDateDto getPdate() {
		return pdate;
	}
	public void setPdate(PublicationDateDto pdate) {
		this.pdate = pdate;
	}
	public double getMonthPrice() {
		return monthPrice;
	}
	public void setMonthPrice(double monthPrice) {
		this.monthPrice = monthPrice;
	}
	public double getIssuePrice() {
		return issuePrice;
	}
	public void setIssuePrice(double issuePrice) {
		this.issuePrice = issuePrice;
	}
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof NewspaperDto))
			return false;
		NewspaperDto newspaper = (NewspaperDto) obj;
		return this.seq == newspaper.getSeq();
	}

}
