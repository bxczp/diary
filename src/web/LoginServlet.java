package web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDao;
import model.User;
import util.DbUtil;

/**
 * @date 2016年2月24日 LoginServlet.java
 * @author CZP
 * @parameter
 */
public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DbUtil dbutil = new DbUtil();
	private User user;
	private UserDao userDao = new UserDao();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String userName = req.getParameter("userName");
		String password = req.getParameter("password");
		String remember = req.getParameter("remember");
		String yangshi = req.getParameter("yangshi");
		Connection conn = null;
		HttpSession session = null;
		try {
			conn = dbutil.getConn();
			session = req.getSession();
			user = new User(userName, password);
			User currentUser = userDao.login(conn, user);
			if (currentUser == null) {
				// 登录失败
				req.setAttribute("user", user);
				req.setAttribute("error", "用户名或密码不正确");
				req.getRequestDispatcher("login.jsp").forward(req, resp);
			} else {// 登陆成功
				session.setAttribute("yangshi", yangshi);
				session.setAttribute("currentUser", currentUser);
				// 不能写成remember.equals(""),因为如果没有勾“记住我”，则remember为空值，会出错
				if ("remember-me".equals(remember)) {
					// 记住我
					rememberMe(userName, password, resp);
				}
				// 登陆成功后请求mainservlet
				req.getRequestDispatcher("main").forward(req, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbutil.closeConn(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private void rememberMe(String userName, String password, HttpServletResponse resp) {
		Cookie cookie = new Cookie("user", userName + "-" + password);
		cookie.setMaxAge(24 * 60 * 60);
		resp.addCookie(cookie);
	}

}
