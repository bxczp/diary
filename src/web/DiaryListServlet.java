package web;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DiaryDao;
import jdk.nashorn.internal.runtime.JSONListAdapter;
import model.Diary;
import model.PageBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import util.DateJsonValueProcessor;
import util.DbUtil;
import util.ResponseUtil;
import util.StringUtil;

/**
 * @date 2016年4月10日 DiaryListServlet.java
 * @author CZP
 * @parameter
 */
public class DiaryListServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DiaryDao diaryDao = new DiaryDao();
	private DbUtil dbUtil = new DbUtil();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String s_title = req.getParameter("s_title");
		String s_releaseDate = req.getParameter("releaseDate");
		String page = req.getParameter("page");
		String rows = req.getParameter("rows");
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		Diary s_diary = new Diary();
		if (StringUtil.isNotEmpty(s_title)) {
			s_diary.setTitle(s_title);
		}
		if (StringUtil.isNotEmpty(s_releaseDate)) {
			s_releaseDate = s_releaseDate.split("-")[0] + "年" + s_releaseDate.split("-")[1] + "月";
			s_diary.setReleaseDateStr(s_releaseDate);
		}
		Connection conn = null;
		try {
			conn = dbUtil.getConn();
			List<Diary> diaryList = diaryDao.diaryList(conn, pageBean, s_diary);
			for (Diary d : diaryList) {
				d.setContent(StringUtil.Html2Text(d.getContent()));
			}
			int total = diaryDao.diaryCount(conn, s_diary);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
			JSONArray array = JSONArray.fromObject(diaryList, jsonConfig);
			JSONObject object = new JSONObject();
			object.put("rows", array);
			object.put("total", total);
			ResponseUtil.write(resp, object);
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

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

}
