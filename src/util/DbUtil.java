package util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @date 2016年2月24日 DbUtil.java
 * @author CZP
 * @parameter
 */
public class DbUtil {

	public Connection getConn() throws Exception {
		// jdbc4已经不需要显式的调用Class.forName了，在jdbc4中，调用getConnection的时候DriverManager会自动去
		// 加载合适的驱动 Class.forName(PropertiesUtil.getValue("jdbcName")); 可以省去
		Class.forName(PropertiesUtil.getValue("jdbcName"));
		Connection conn = DriverManager.getConnection(PropertiesUtil.getValue("url"),
				PropertiesUtil.getValue("userName"), PropertiesUtil.getValue("password"));
		return conn;
	}

	public void closeConn(Connection conn) throws Exception {
		if (conn != null) {
			conn.close();
		}
	}

	public static void main(String[] args) {
		DbUtil dbUtil = new DbUtil();
		try {
			dbUtil.getConn();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("success");
	}

}
