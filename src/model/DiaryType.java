package model;
/**
 *@date 2016年2月26日
 * DiaryType.java
 *@author CZP
 *@parameter
 */
public class DiaryType {
	private int diaryTypeId;
	private String typeName;
	private int diaryCount;//每个类别的日记数
	
	
	
	public int getDiaryTypeId() {
		return diaryTypeId;
	}
	public void setDiaryTypeId(int diaryTypeId) {
		this.diaryTypeId = diaryTypeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public int getDiaryCount() {
		return diaryCount;
	}
	public void setDiaryCount(int diaryCount) {
		this.diaryCount = diaryCount;
	}
	
	
	
	
}
