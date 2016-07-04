package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;
import util.MD5Util;
import util.PropertiesUtil;

/**
 * @date 2016年2月24日 UserDao.java
 * @author CZP
 * @parameter
 */
public class UserDao {
	public User login(Connection conn, User user) throws Exception {
		User resultUser = null;
		String sql = "select * from t_user where userName=?and password=?";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setString(1, user.getUserName());
		// 对传入的password进行MD5加密
		preparedStatement.setString(2, MD5Util.EncoderPwdByMd5(user.getPassword()));
		ResultSet rs = preparedStatement.executeQuery();
		if (rs.next()) {
			resultUser = new User();
			resultUser.setUserId(rs.getInt("userId"));
			resultUser.setUserName(rs.getString("userName"));
			resultUser.setPassword(rs.getString("password"));
			resultUser.setNickName(rs.getString("nickName"));
			resultUser.setMood(rs.getString("mood"));
			resultUser.setImageName(PropertiesUtil.getValue("imageFile") + rs.getString("imageName"));

		}
		return resultUser;
	}

	public int userUpdate(Connection conn, User user) throws Exception {
		String sql = "update t_user set nickName=?,imageName=?,mood=? where userId=?";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setString(1, user.getNickName());
		preparedStatement.setString(2, user.getImageName());
		preparedStatement.setString(3, user.getMood());
		preparedStatement.setInt(4, user.getUserId());
		return preparedStatement.executeUpdate();
	}
}
