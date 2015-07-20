package npp.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHelper {
//	private static OracleDataSource ds = null;
//
//	static {
//
//		InputStream is = DBHelper.class.getResourceAsStream("config.properties");
//		Properties prop = new Properties();
//		try {
//			ds = new OracleDataSource();
//			prop.load(is);
//			ds.setDriverType(prop.getProperty("type"));
//			ds.setServerName(prop.getProperty("host"));
//			ds.setPortNumber(Integer.parseInt(prop.getProperty("portNumber")));
//			ds.setDatabaseName(prop.getProperty("database")); // sid
//			ds.setUser(prop.getProperty("user"));
//			ds.setPassword(prop.getProperty("password"));
//			// ds.setUsername(prop.getProperty("username"));
//			// ds.setPassword(prop.getProperty("password"));
//			System.out.println("URL:" + ds.getURL());
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			// try {
//			// if (is != null)
//			// is.close();
//			// } catch (IOException e) {
//			// e.printStackTrace();
//			// }
//		}
//	}

	public static Connection getConnection() throws SQLException {
		// return ds.getConnection();
		Connection conn = null;

		try {

			String driver = "com.mysql.jdbc.Driver"; // 数据库驱动

			String url = "jdbc:MySQL://127.0.0.1:3306/npp";// 数据库

			String user = "root"; // 用户名

			String password = "root"; // 密码

			Class.forName(driver); // 加载数据库驱动

			if (null == conn) {

				conn = DriverManager.getConnection(url, user, password);

			}

		} catch (ClassNotFoundException e) {

			System.out.println("Sorry,can't find the Driver!");

			e.printStackTrace();

		} catch (SQLException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return conn;
	}

	public static void close(ResultSet rs, PreparedStatement ps, Connection conn) {
		try {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
//
//	public DataSource getResource() {
//		return ds;
//	}

	public static void main(String args[]) throws ClassNotFoundException, SQLException {

		Connection conn = DBHelper.getConnection();

		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(
				"select * from test");
		if (rset.next())
			System.out.println(rset.getString(1));
		System.out.println(rset.getFetchSize());
		rset.close();
		stmt.close();
		conn.close();
	}

}