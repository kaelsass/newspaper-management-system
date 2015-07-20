package npp.condition;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 动态查询工具类，用于拼接SQL、填充pst
 *
 * @author Lixor(at)live.cn
 *
 */
public class DynamicQuery {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private String andTemplet = " AND %s %s ?";
	private String orTemplet = " OR %s %s ?";

	private String baseSql;
	private String suffix;
	private ArrayList<Parameter> parameters = new ArrayList<Parameter>();

	public DynamicQuery() {
		baseSql = "";
		suffix = "";
		parameters = new ArrayList<Parameter>();
	}

	/**
	 * 要求baseSql带有where条件
	 *
	 * @param baseSql
	 */
	public void setBaseSql(String baseSql) {
		this.baseSql = baseSql;
	}

	public void addParameter(Parameter parameter) {
		parameters.add(parameter);
	}

	public String generateSql() {
		StringBuffer buffer = new StringBuffer(baseSql);
		ArrayList<StringBuffer> orBufferList = new ArrayList<StringBuffer>();
		StringBuffer orBuffer = new StringBuffer();
		Parameter pre = null;
		for (Parameter p : parameters) {
			if (p.getType() == Parameter.OR) {
				if (pre != null && !p.getField().equals(pre.getField())) {
					orBufferList.add(orBuffer);
					orBuffer = new StringBuffer();
				}
				orBuffer.append(String.format(orTemplet, p.getField(),
						p.getOperator()));
				pre = p;
			} else {
				buffer.append(String.format(andTemplet, p.getField(),
						p.getOperator()));
			}

		}
		orBufferList.add(orBuffer);
		for (StringBuffer orBuf : orBufferList) {
			if (!orBuf.toString().trim().equals(""))
				buffer.append(" AND ( 1 = 0 " + orBuf.toString() + " ) ");
		}
		buffer.append(suffix);
		logger.debug(buffer.toString());
		return buffer.toString();
	}

	public void fillPreparedStatement(PreparedStatement pst)
			throws SQLException {
		int count = 1;
		for (Parameter p : parameters) {
			System.out.println(count + " : " + p.getValue());
			pst.setObject(count, p.getValue());
			count++;
		}
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

}
