package npp.dto;

public class StatusDto {
	private int seq;
	private String name;

	// You must declared default constructor for Framework.
	public StatusDto() {
		init(0, "");
	}

	public StatusDto(int seq, String name) {
		init(seq, name);
	}

	public StatusDto(StatusDto dto) {
		init(dto.getSeq(), dto.getName());
	}

	public void init(int seq, String name) {
		this.seq = seq;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getFormatName() {
		if (name == null)
			return null;
		int base = 60;
		StringBuffer sb = new StringBuffer();
		int count = 0;
		while ((count + base) < name.length()) {
			sb.append(name.substring(count, count + base));
			sb.append("\n");
			count += base;
		}
		sb.append(name.substring(count));
		return sb.toString();
	}

}
