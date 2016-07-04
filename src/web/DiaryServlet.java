package web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DiaryDao;
import model.Diary;
import net.sf.json.JSONObject;
import util.DbUtil;
import util.ResponseUtil;
import util.StringUtil;

/**
 * @date 2016年2月26日 DiaryServlet.java
 * @author CZP
 * @parameter
 */
public class DiaryServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DiaryDao diaryDao = new DiaryDao();
	DbUtil dbUtil = new DbUtil();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		if ("show".equals(action)) {
			this.diaryShow(req, resp);
		} else if ("preSave".equals(action)) {
			this.diaryPreSave(req, resp);
		} else if ("save".equals(action)) {
			this.diarySave(req, resp);
		} else if ("delete".equals(action)) {
			this.diaryDelete(req, resp);
		}
	}

	private void diaryShow(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String diaryId = req.getParameter("diaryId");
		Connection conn=null;
		try {
			 conn= dbUtil.getConn();
			Diary diary = diaryDao.diaryShow(conn, diaryId);
			req.setAttribute("diary", diary);
			req.setAttribute("mainPage", "diary/diaryShow.jsp");
			req.getRequestDispatcher("mainTemp.jsp").forward(req, resp);
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

	public void diaryPreSave(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String diaryId = req.getParameter("diaryId");
		Connection conn = null;
		try {
			if (StringUtil.isNotEmpty(diaryId)) {
				conn = dbUtil.getConn();
				Diary diary = diaryDao.diaryShow(conn, diaryId);
				req.setAttribute("diary", diary);
			}
			req.setAttribute("mainPage", "diary/diaryAve.jsp");
			req.getRequestDispatcher("mainTemp.jsp").forward(req, resp);
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

	public void diaryDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String diaryId = req.getParameter("diaryId");
		Connection conn = null;
		try {
			conn = dbUtil.getConn();
			diaryDao.diaryDelete(conn, Integer.parseInt(diaryId));
			req.getRequestDispatcher("main?all=true").forward(req, resp);
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

	public void diarySave(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String diaryId = req.getParameter("diaryId");
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		String typeId = req.getParameter("typeId");
		Diary diary = new Diary();
		diary.setContent(content);
		diary.setTitle(title);
		diary.setTypeId(Integer.parseInt(typeId));
		if (StringUtil.isNotEmpty(diaryId)) {
			diary.setDiaryId(Integer.parseInt(diaryId));
		}
		Connection conn = null;
		try {
			conn = dbUtil.getConn();
			int saveDiaryNum;
			if (StringUtil.isNotEmpty(diaryId)) {
				saveDiaryNum=diaryDao.diaryUpdate(conn, diary);
			} else {
				saveDiaryNum = diaryDao.diaryAdd(conn, diary);
			}
			if (saveDiaryNum != 0) {// 保存成功，显示全部日记
				req.getRequestDispatcher("main?all=true").forward(req, resp);
			} else {// 保存失败
				req.setAttribute("diary", diary);
				req.setAttribute("error", "保存失败");
				req.setAttribute("mainPage", "diary/diaryAve.jsp");
				req.getRequestDispatcher("mainTemp.jsp").forward(req, resp);
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

}
