package web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DiaryTypeDao;
import net.sf.json.JSONArray;
import util.DbUtil;
import util.ResponseUtil;

/**
 * @date 2016年4月10日 DiaryTypeList.java
 * @author CZP
 * @parameter
 */
public class DiaryTypeListServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DiaryTypeDao diaryTypeDao = new DiaryTypeDao();
	private DbUtil dbUtil = new DbUtil();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		Connection conn = null;
		try {
			conn = dbUtil.getConn();
			JSONArray jsonArray = JSONArray.fromObject(diaryTypeDao.diaryTypesList(conn));
			ResponseUtil.write(resp, jsonArray);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					dbUtil.closeConn(conn);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
