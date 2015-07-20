package npp.condition;
/**
 * 查询参数类，用于表示条件参数对象
 * @author Lixor(at)live.cn
 *
 */
public class Parameter{
	public static final int AND = 0;
	public static final int OR = 1;
	private String field;
	private Object value;
	private String operator;
	private int type;
	/**
	 *
	 * @param field 数据库字段名
	 * @param operator 数据库操作符 =、>=、<、like etc...
	 * @param value 参数值 Object
	 */
	public Parameter(String field,String operator, Object value) {
		super();
		this.field = field;
		this.value = value;
		this.operator = operator;
		this.type = AND;
	}
	public String getField() {
		return field;
	}
	public Object getValue() {
		return value;
	}
	public String getOperator() {
		return operator;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
