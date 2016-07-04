package web;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import dao.UserDao;
import model.User;
import util.DateUtil;
import util.DbUtil;
import util.PropertiesUtil;

/**
 * @date 2016年2月27日 UserServlet.java
 * @author CZP
 * @parameter
 */
public class UserServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DbUtil dbUtil = new DbUtil();
	UserDao userDao = new UserDao();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		if ("preSave".equals(action)) {
			userPreSave(req, resp);
		} else if ("save".equals(action)) {
			userSave(req, resp);
		}
	}

	public void userPreSave(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("mainPage", "user/userAve.jsp");
		req.getRequestDispatcher("mainTemp.jsp").forward(req, resp);
	}

	public void userSave(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 使用FileUpLoad组件(Tomcat自带的组件)实现头像图片的上传功能
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> items = null;
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("currentUser");
		boolean imageChange = false;
		
		try {// 获取上传的所以字段信息
			items = upload.parseRequest(new ServletRequestContext(req));
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		Iterator<FileItem> itr = items.iterator();
		while (itr.hasNext()) {
			FileItem item = (FileItem) itr.next();// 对所有字段进行遍历
			if (item.isFormField()) {// 普通表单信息
				String fieldName = item.getFieldName();
				if ("nickName".equals(fieldName)) {
					user.setNickName(item.getString("UTF-8"));
				}
				if ("mood".equals(fieldName)) {
					user.setMood(item.getString("UTF-8"));
				}
			} else if (!"".equals(item.getName())) {
				// 上传的图片文件
				imageChange = true;
				System.out.println("上传图片");
				String imageName = DateUtil.getCurrentDateStr();
				System.out.println("在UserServlet中的imageName:"+imageName);
				// 文件名+"."+后缀名
				user.setImageName(imageName + "." + item.getName().split("\\.")[1]);
				String filePath = PropertiesUtil.getValue("imagePath") + imageName + "."
						+ item.getName().split("\\.")[1];
				try {
					item.write(new File(filePath));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		Connection conn = null;
		if (!imageChange) {
			user.setImageName(user.getImageName().replace(PropertiesUtil.getValue("imageFile"), ""));
		}
		try {
			conn = dbUtil.getConn();
			int saveNum = userDao.userUpdate(conn, user);
			if (saveNum > 0) {
				user.setImageName(PropertiesUtil.getValue("imagePath") + user.getImageName());
				session.setAttribute("currentUser", user);
				req.getRequestDispatcher("main?all=true").forward(req, resp);
			} else {
				req.setAttribute("currentUser", user);
				req.setAttribute("error", "用户信息更新失败");
				req.setAttribute("mainPage", "user/userAve.jsp");
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
