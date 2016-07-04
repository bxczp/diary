package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Diary;
import model.PageBean;
import util.DateUtil;
import util.StringUtil;

/**
 * @date 2016年2月25日 DiaryDao.java
 * @author CZP
 * @parameter
 */
public class DiaryDao {
	// 分页查询
	public List<Diary> diaryList(Connection conn, PageBean pageBean, Diary s_diary) throws Exception {
		StringBuffer sb = new StringBuffer("select * from t_diary t1,t_diaryType t2 where t1.typeId=t2.diaryTypeId");
		if (s_diary != null) {
			if (s_diary.getTypeId() != -1) {
				sb.append(" and t1.typeId = " + s_diary.getTypeId());
			}
			if (StringUtil.isNotEmpty(s_diary.getReleaseDateStr())) {
				// eg 2016年3月30日 分隔成 20163
				String str = s_diary.getReleaseDateStr().split("年")[0]
						+ s_diary.getReleaseDateStr().split("年")[1].split("月")[0];
				// 出现乱码
				sb.append(" and DATE_FORMAT(t1.releaseDate,'%Y%m') like " + "'" + str + "'");
			}
			if (StringUtil.isNotEmpty(s_diary.getTitle())) {
				sb.append(" and t1.title like " + "'%" + s_diary.getTitle() + "%'");
			}
		}

		sb.append(" order by releaseDate desc ");
		if (pageBean != null) {// SQl语句的分页查询 开始的记录号以及每页的记录数
			sb.append(" limit " + pageBean.getStart() + "," + pageBean.getPageSize());
		}
		List<Diary> list = new ArrayList<Diary>();
		PreparedStatement preparedStatement = conn.prepareStatement(sb.toString());
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			Diary diary = new Diary();
			diary.setTypeId(rs.getInt("diaryTypeId"));
			diary.setTitle(rs.getString("title"));
			diary.setContent(rs.getString("content"));
			diary.setDiaryId(rs.getInt("diaryId"));
			diary.setTypeName(rs.getString("typeName"));
			diary.setReleaseDate(DateUtil.formatString(rs.getString("releaseDate"), "yyyy-MM-dd HH:mm:ss"));
			list.add(diary);
		}
		return list;
	}

	public int diaryCount(Connection conn, Diary s_diary) throws Exception {
		StringBuffer sb = new StringBuffer(
				"select count(*) as total from t_diary t1,t_diaryType t2 where t1.typeId=t2.diaryTypeId");
		if (s_diary != null) {
			if (s_diary.getTypeId() != -1) {
				sb.append(" and t1.typeId = " + s_diary.getTypeId());
			}
			if (StringUtil.isNotEmpty(s_diary.getReleaseDateStr())) {
				// eg 2016年3月30日 分隔成 20163
				String str = s_diary.getReleaseDateStr().split("年")[0]
						+ s_diary.getReleaseDateStr().split("年")[1].split("月")[0];
				// 出现乱码
				sb.append(" and DATE_FORMAT(t1.releaseDate,'%Y%m') like " + "'" + str + "'");
			}
			if (StringUtil.isNotEmpty(s_diary.getTitle())) {
				sb.append(" and t1.title like " + "'%" + s_diary.getTitle() + "%'");
			}
		}
		PreparedStatement preparedStatement = conn.prepareStatement(sb.toString());
		ResultSet rs = preparedStatement.executeQuery();
		if (rs.next()) {
			return rs.getInt("total");
		} else {
			return 0;
		}
	}

	public List<Diary> diaryCountList(Connection conn) throws Exception {
		List<Diary> diaryCountList = new ArrayList<>();
		// 获取日期的sql语句
		String sql = "SELECT DATE_FORMAT(releaseDate,'%Y年%m月') as releaseDate ,COUNT(*) as count from t_diary GROUP BY DATE_FORMAT(releaseDate,'%Y年%m月') ORDER BY DATE_FORMAT(releaseDate,'%Y年%m月') DESC ;";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			Diary diary = new Diary();
			diary.setReleaseDateStr(rs.getString("releaseDate"));
			diary.setDiaryCount(rs.getInt("count"));
			diaryCountList.add(diary);
		}
		return diaryCountList;
	}

	public Diary diaryShow(Connection conn, String diaryId) throws Exception {
		StringBuffer sb = new StringBuffer(
				"select * from t_diary t1,t_diaryType t2 where t1.typeId=t2.diaryTypeId and t1.diaryId=? ");
		PreparedStatement preparedStatement = conn.prepareStatement(sb.toString());
		preparedStatement.setString(1, diaryId);
		Diary diary = new Diary();
		ResultSet rs = preparedStatement.executeQuery();
		if (rs.next()) {
			diary.setDiaryId(rs.getInt("diaryId"));
			diary.setTitle(rs.getString("title"));
			diary.setContent(rs.getString("content"));
			diary.setTypeId(rs.getInt("typeId"));
			diary.setTypeName(rs.getString("typeName"));
			diary.setReleaseDate(rs.getDate("releaseDate"));
		}
		return diary;
	}

	public int diaryAdd(Connection conn, Diary diary) throws Exception {
		String sql = "insert into t_diary values(null,?,?,?,now())";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setString(1, diary.getTitle());
		preparedStatement.setString(2, diary.getContent());
		preparedStatement.setInt(3, diary.getTypeId());
		return preparedStatement.executeUpdate();
	}

	public int diaryDelete(Connection conn, int diaryId) throws Exception {
		String sql = "delete from t_diary where diaryId=?";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, diaryId);
		return preparedStatement.executeUpdate();

	}

	public int diaryUpdate(Connection conn, Diary diary) throws Exception {
		String sql = "update t_diary set title=?,content=?,typeId=? where diaryId=?";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setString(1, diary.getTitle());
		preparedStatement.setString(2, diary.getContent());
		preparedStatement.setInt(3, diary.getTypeId());
		preparedStatement.setInt(4, diary.getDiaryId());
		return preparedStatement.executeUpdate();
	}

	public boolean existDiaryWithTypeId(Connection conn, String typeId) throws Exception {
		String sql = "select * from t_diary where typeId= ?";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, Integer.parseInt(typeId));
		ResultSet rs = preparedStatement.executeQuery();
		if (rs.next()) {
			return true;
		} else {
			return false;
		}
	}

}
