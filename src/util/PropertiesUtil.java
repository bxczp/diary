package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @date 2016年2月24日 PropertiesUtil.java
 * @author CZP
 * @parameter
 */
public class PropertiesUtil {
	public static String getValue(String key) {
		Properties properties = new Properties();
		// 获取src目录下的properties文件输入流 (以下三种方法都可行)
		InputStream in1 = new PropertiesUtil().getClass().getResourceAsStream("/diary.properties");
		InputStream in2 = PropertiesUtil.class.getResourceAsStream("/diary.properties");
		InputStream in3 = PropertiesUtil.class.getClassLoader().getResourceAsStream("diary.properties");
		try {
			properties.load(in1);
			// properties.load(in2);
			// properties.load(in3);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String value = String.valueOf(properties.get(key));
		return value;
	}

	public static void main(String[] args) {
		System.out.println(PropertiesUtil.getValue("pageSize"));
	}
}
