package util;

/**
 * @date 2016年4月3日 PageUtil.java
 * @author CZP
 * @parameter
 */
public class PageUtil {

	// 生成pagation的信息
	public static  String genPagation(int totalNum, int currentPage, int pageSize) {
		int totalPage = totalNum % pageSize == 0 ? totalNum / pageSize : totalNum / pageSize + 1;
		StringBuffer pageCode = new StringBuffer();
		pageCode.append("<li><a href='main?page=1'>首页</a></li>");
		// currentPage==1时表示是首页，没有上一页的选择（disabled）
		if (currentPage == 1) {
			// href='#' 跳转到本页面顶部
			pageCode.append("<li class='disabled'><a href='#'>上一页</a></li>");
		} else {
			pageCode.append("<li><a href='main?page=" + (currentPage - 1) + "'>上一页</a></li>");
		}
		// 其余页 显示五项 x-2，x-1，x，x+1,x+2
		for (int i = currentPage - 2; i <= currentPage + 2; i++) {
			if (i < 1 || i > totalPage) {
				continue;
			}
			// 正好是当前页href='#' 跳转到本页面顶部
			if (i == currentPage) {
				pageCode.append("<li class='active'><a href='#'>" + i + "</a></li>");
			} else {// 不是当前页
				pageCode.append("<li><a href='main?page=" + i + "'>" + i + "</a></li>");
			}
		}
		// currentPage==totalPage时表示是尾页，没有下一页的选择（disabled）
		if (currentPage == totalPage) {
			pageCode.append("<li class='disabled'><a href='#'>下一页</a></li>");
		} else {
			pageCode.append("<li><a href='main?page=" + (currentPage + 1) + "'>下一页</a></li>");
		}
		pageCode.append("<li><a href='main?page=" + totalPage + "'>尾页</a></li>");
		return pageCode.toString();
	}

}
