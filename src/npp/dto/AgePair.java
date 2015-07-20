package npp.dto;

public class AgePair {
	public static final AgePair[] allRanges = new AgePair[] {
			new AgePair(0, 1, 10), new AgePair(1, 11, 20),
			new AgePair(2, 21, 30), new AgePair(3, 31, 40),
			new AgePair(4, 41, 50), new AgePair(5, 51, 60),
			new AgePair(6, 60, 200) };
	private int seq;
	private int from;
	private int to;

	public AgePair() {
		seq = -1;
		from = 0;
		to = 0;
	}

	public AgePair(int seq, int from, int to) {
		this.seq = seq;
		this.from = from;
		this.to = to;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getFormat()
	{
		if(seq >= 0)
			return from + " -- " + to;
		else
			return "Not Recorded";
	}

}
