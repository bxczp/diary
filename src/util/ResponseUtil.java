package util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

/**
 * @date 2016年4月10日 ResponseUtil.java
 * @author CZP
 * @parameter
 */
public class ResponseUtil {

	public static void write(HttpServletResponse response, Object o) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		if (o != null) {
			writer.println(o.toString());
			writer.flush();
			writer.close();
		}
	}

}
