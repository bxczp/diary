<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 都使用绝对路径 -->
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>

<!-- 	引入ckeditor，一个在线文本编辑器 -->
<script src="${pageContext.request.contextPath}/js/ckeditor/ckeditor.js"></script>

<script type="text/javascript">
	function openTab (text, url, iconCls)
    {
	    if ($ ("#tabs").tabs ("exists", text))
	    {
		    $ ("#tabs").tabs ("select", text)
	    }
	    else
	    {
		    var content = "<iframe frameborder=0 scolling='auto' style='width:100%;height:100%' src='${pageContext.request.contextPath}/"
		            + url + "'></iframe>";
		    $ ("#tabs").tabs ("add",
		    {
		        title : text,
		        iconCls : iconCls,
		        closable : true,
		        content : content
		    })
	    }
    }
</script>

</head>
<body class="easyui-layout">

	<div region="north" style="width: 100%; height: 100px; background-color: #E0ECFF">
		<h1>日记本</h1>
		<div align="right">
			<strong>${currentUser.userName }</strong>
		</div>
	</div>

	<div region="west" style="width: 200px; height: 100%; background-color: #E0ECFF">
		<div style="margin: 10px 0;">
			<ul class="easyui-tree" lines="true">
				<li>
					<a>我的日记本</a>
				</li>
				<li>
					<a href="javascript:openTab('主页','diaryAll.jsp','icon-home')">主页</a>
				</li>
				<li>
					<a href="javascript:openTab('写日记','diaryAdd.jsp','icon-add')">写日记</a>
				</li>
				<li>
					<a href="javascript:openTab('日记类别管理','diaryTypeManager.jsp','icon-edit')">日记类别管理</a>
				</li>
				<li>
					<a href="javascript:openTab('个人中心管理','userManager.jsp','icon-permission')">个人中心管理</a>
				</li>
			</ul>
		</div>
	</div>

	<div region="center">
		<div class="easyui-tabs" fit="true" id="tabs">
			<div title="首页" data-options="iconCls:'icon-home'">
				<div align="center" style="padding-top: 100px;">
					<font color="blue" size='10'>Welcome:${currentUser.userName }</font>
				</div>
			</div>
		</div>
	</div>




</body>
</html>