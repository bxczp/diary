package web;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DiaryDao;
import dao.DiaryTypeDao;
import model.DiaryType;
import util.DbUtil;
import util.StringUtil;

/**
 * @date 2016年2月27日 DiaryTypeServlet.java
 * @author CZP
 * @parameter
 */
public class DiaryTypeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	DbUtil dbUtil = new DbUtil();
	DiaryTypeDao diaryTypeDao = new DiaryTypeDao();
	DiaryDao diaryDao=new DiaryDao();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		if ("list".equals(action)) {
			this.diaryTypeList(req, resp);
		} else if ("preSave".equals(action)) {
			this.diaryTypePreSave(req, resp);
		} else if ("save".equals(action)) {
			this.diaryTypeSave(req, resp);
		} else if("delete".equals(action)){
			this.diaryTypeDelete(req, resp);
		}
	}

	public void diaryTypeList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = null;
		try {
			conn = dbUtil.getConn();
			List<DiaryType> diaryTypeList = diaryTypeDao.diaryTypesList(conn);
			req.setAttribute("diaryTypeList", diaryTypeList);
			req.setAttribute("mainPage", "diaryType/diaryTypeList.jsp");
			req.getRequestDispatcher("mainTemp.jsp").forward(req, resp);
			;
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

	public void diaryTypePreSave(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String diaryTypeId = req.getParameter("diaryTypeId");
		if (StringUtil.isNotEmpty(diaryTypeId)) {
			Connection conn = null;
			try {
				conn = dbUtil.getConn();
				DiaryType diaryType = diaryTypeDao.diaryTypeShow(conn, diaryTypeId);
				req.setAttribute("diaryType", diaryType);
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
		req.setAttribute("mainPage", "diaryType/diaryTypeAve.jsp");
		req.getRequestDispatcher("mainTemp.jsp").forward(req, resp);

	}

	public void diaryTypeSave(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String diaryTypeId = req.getParameter("diaryTypeId");
		String typeName = req.getParameter("typeName");
		DiaryType diaryType = new DiaryType();
		if (StringUtil.isNotEmpty(diaryTypeId)) {
			diaryType.setDiaryTypeId(Integer.parseInt(diaryTypeId));
		}
		diaryType.setTypeName(typeName);
		Connection conn=null;
		try {
			conn=dbUtil.getConn();
			int saveNum;
			if(StringUtil.isNotEmpty(diaryTypeId)){
				saveNum=diaryTypeDao.diaryTypeUpdate(conn, diaryType);
			}else{
				saveNum=diaryTypeDao.diaryTypeAdd(conn, diaryType);
			}
			if(saveNum>0){
				req.getRequestDispatcher("diaryType?action=list").forward(req, resp);
			}else{
				req.setAttribute("diaryType", diaryType);
				req.setAttribute("error", "保存失败");
				req.setAttribute("mainPage", "diaryType/diaryAve.jsp");
				req.getRequestDispatcher("mainTemp.jsp").forward(req, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				dbUtil.closeConn(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	
	public void diaryTypeDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String diaryTypeId=req.getParameter("diaryTypeId");
		Connection conn=null;
		try {
			conn=dbUtil.getConn();
			if(diaryDao.existDiaryWithTypeId(conn,diaryTypeId)){
				req.setAttribute("error", "该类别下存在日记，不能删除！");
			}else{
				diaryTypeDao.diaryTypeDelete(conn, diaryTypeId);
			}
			req.getRequestDispatcher("diaryType?action=list").forward(req,resp);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				dbUtil.closeConn(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
