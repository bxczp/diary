package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.DiaryType;

/**
 *@date 2016年2月26日
 * DiaryTypeDao.java
 *@author CZP
 *@parameter
 */
public class DiaryTypeDao {
	public List<DiaryType> diaryTypeCountList(Connection conn) throws Exception{
		List<DiaryType> diaryCountTypes=new ArrayList<>();
//		使用右链接查询
		String sql="select  t_diarytype.diaryTypeId as diaryTypeId,t_diarytype.typeName as typeName,COUNT(t_diary.diaryId) as diaryCount from t_diary RIGHT JOIN t_diarytype on t_diary.typeId=t_diarytype.diaryTypeId GROUP BY t_diarytype.typeName";
		PreparedStatement preparedStatement=conn.prepareStatement(sql);
		ResultSet rs=preparedStatement.executeQuery();
		while(rs.next()){
			DiaryType diaryType=new DiaryType();
			diaryType.setTypeName(rs.getString("typeName"));
			diaryType.setDiaryTypeId(rs.getInt("diaryTypeId"));
			diaryType.setDiaryCount(rs.getInt("diaryCount"));
			diaryCountTypes.add(diaryType);
		}
		return diaryCountTypes;
	}
	
	
	public List<DiaryType> diaryTypesList(Connection conn) throws Exception {
		List<DiaryType> diaryTypesList=new ArrayList<>();
		String sql="select * from t_diaryType ";
		PreparedStatement preparedStatement=conn.prepareStatement(sql);
		ResultSet rs=preparedStatement.executeQuery();
		while(rs.next()){
			DiaryType diaryType=new DiaryType();
			diaryType.setDiaryTypeId(rs.getInt("diaryTypeId"));
			diaryType.setTypeName(rs.getString("typeName"));
			diaryTypesList.add(diaryType);
		}
		return diaryTypesList;
	}
	
	
	public int diaryTypeAdd(Connection conn,DiaryType diaryType) throws Exception{
		String sql="insert into t_diaryType values(null,?)";
		PreparedStatement preparedStatement=conn.prepareStatement(sql);
		preparedStatement.setString(1, diaryType.getTypeName());
		return preparedStatement.executeUpdate();
	}
	
	
	public int diaryTypeDelete(Connection conn,String diaryTypeId) throws Exception{
		String sql="delete from t_diarytype where diaryTypeId =? ";
		PreparedStatement preparedStatement=conn.prepareStatement(sql);
		preparedStatement.setInt(1, Integer.parseInt(diaryTypeId));
		return preparedStatement.executeUpdate();
	}
	
	public int diaryTypeUpdate(Connection conn,DiaryType diaryType) throws Exception{
		String sql="update t_diaryType set typeName=? where diaryTypeId=?";
		PreparedStatement preparedStatement=conn.prepareStatement(sql);
		preparedStatement.setString(1, diaryType.getTypeName());
		preparedStatement.setInt(2, diaryType.getDiaryTypeId());
		return preparedStatement.executeUpdate();
	}
	
	
	public DiaryType diaryTypeShow(Connection conn,String diaryTypeId) throws Exception{
		String sql="select * from t_diaryType where diaryTypeId=?";
		PreparedStatement preparedStatement=conn.prepareStatement(sql);
		DiaryType diaryType=new DiaryType();
		preparedStatement.setInt(1, Integer.parseInt(diaryTypeId));
		ResultSet rs=preparedStatement.executeQuery();
		if(rs.next()){
			diaryType.setDiaryTypeId(rs.getInt("diaryTypeId"));
			diaryType.setTypeName(rs.getString("typeName"));
		}
		return diaryType;
	}
	

}
