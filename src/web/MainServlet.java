package web;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DiaryDao;
import dao.DiaryTypeDao;
import model.Diary;
import model.PageBean;
import util.DbUtil;
import util.PageUtil;
import util.PropertiesUtil;
import util.StringUtil;

/**
 * @date 2016年2月24日 MainServlet.java
 * @author CZP
 * @parameter
 */
public class MainServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DbUtil dbUtil = new DbUtil();
	private DiaryDao diaryDao = new DiaryDao();
	private DiaryTypeDao diaryTypeDao = new DiaryTypeDao();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		HttpSession session = req.getSession();
		String yangshi = (String) session.getAttribute("yangshi");
		// 获取页数
		String page = req.getParameter("page");
		if (StringUtil.isEmpty(page)) {
			page = "1";
		}
		String diaryTypeId = req.getParameter("diary_typeId");
		String diaryReleaseDateStr = req.getParameter("diary_releaseDateStr");
		// 定义条件查询实体
		Diary s_diary = new Diary();
		// 是否搜索
		String all = req.getParameter("all");
		// 搜索框中的内容
		String s_title = req.getParameter("s_title");
		if ("true".equals(all)) {
			if (StringUtil.isNotEmpty(s_title)) {
				s_diary.setTitle(s_title);
			}
			session.setAttribute("s_title", s_title);
			// 移除选择的条件
			session.removeAttribute("diaryReleaseDateStr");
			session.removeAttribute("diaryTypeId");
		} else {
			// 按类别选择日记
			if (StringUtil.isNotEmpty(diaryTypeId)) {
				s_diary.setTypeId(Integer.parseInt(diaryTypeId));
				session.setAttribute("diaryTypeId", diaryTypeId);
				session.removeAttribute("diaryReleaseDateStr");
				session.removeAttribute("s_title");
			}
			// 按日期选择日记
			if (StringUtil.isNotEmpty(diaryReleaseDateStr)) {
				// diaryReleaseDateStr=new
				// String(diaryReleaseDateStr.getBytes("ISO-8859-1"),"UTF-8");
				s_diary.setReleaseDateStr(diaryReleaseDateStr);
				session.setAttribute("diaryReleaseDateStr", diaryReleaseDateStr);
				session.removeAttribute("diaryTypeId");
				session.removeAttribute("s_title");
			}
			/**
			 * 以上是 第一次有条件查询时 要把查询条件 放入session 并且 设置查询实体s_diary 由于 不可能是 多条件 查询，所以
			 * session中有且只有 一个查询条件 需要人工remove 接下来的
			 * 不是第一次条件查询，所以查询条件为空，当session中存有查询条件 每次 从session中获取 查询条件
			 */
			if (StringUtil.isEmpty(diaryTypeId) && session.getAttribute("diaryTypeId") != null) {
				diaryTypeId = (String) session.getAttribute("diaryTypeId");
				s_diary.setTypeId(Integer.parseInt(diaryTypeId));
			}
			if (StringUtil.isEmpty(diaryReleaseDateStr) && session.getAttribute("diaryReleaseDateStr") != null) {
				diaryReleaseDateStr = (String) session.getAttribute("diaryReleaseDateStr");
				s_diary.setReleaseDateStr(diaryReleaseDateStr);
			}
			if (StringUtil.isEmpty(s_title) && session.getAttribute("s_title") != null) {
				s_title = (String) session.getAttribute("s_title");
				s_diary.setTitle(s_title);
			}
		}
		Connection conn = null;
		try {
			conn = dbUtil.getConn();
			session.setAttribute("diaryTypeCountList", diaryTypeDao.diaryTypeCountList(conn));
			session.setAttribute("diaryCountList", diaryDao.diaryCountList(conn));
			// 第一个参数是 当前页数，第二个是每页的记录数
			PageBean pageBean = new PageBean(Integer.parseInt(page),
					Integer.parseInt(PropertiesUtil.getValue("pageSize")));
			List<Diary> list = diaryDao.diaryList(conn, pageBean, s_diary);
			/**
			 * diaryList 与 diarytypeCountlist,diaryCountList 的存放范围不一样
			 */
			req.setAttribute("diaryList", list);
			// 总记录数
			int total = diaryDao.diaryCount(conn, s_diary);
			String pageCode = PageUtil.genPagation(total, Integer.parseInt(page),
					Integer.parseInt(PropertiesUtil.getValue("pageSize")));
			req.setAttribute("pageCode", pageCode);
			req.setAttribute("mainPage", "diary/diaryList.jsp");
			if ("b".equals(yangshi)) {
				req.getRequestDispatcher("mainTemp.jsp").forward(req, resp);
			} else {
				req.getRequestDispatcher("mainTemp2.jsp").forward(req, resp);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeConn(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public DbUtil getDbUtil() {
		return dbUtil;
	}

	public DiaryDao getDiaryDao() {
		return diaryDao;
	}

	public void setDbUtil(DbUtil dbUtil) {
		this.dbUtil = dbUtil;
	}

	public void setDiaryDao(DiaryDao diaryDao) {
		this.diaryDao = diaryDao;
	}

}
