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


<script type="text/javascript">
	function userSave ()
    {
	    $ ("#fm").form ("submit",
	    {
	        url : "${pageContext.request.contextPath}/user?action=save",
	        success : function ()
	        {
		        $.messager.confirm ("tip", "正在刷新...", function (r)
		        {
			        if (r)
			        {
				        $("#panel").panel("reload");
			        }
		        });
	        }
	    })
    }
</script>

</head>
<body>
	<div class="easyui-panel" id="panel" title="个人中心" style="width: 600px; height: 350px;" buttons="#panel-buttons">
		<img alt="用户图片" src="${currentUser.imageName }" style="width: 250px; height: 200px;">
		<form action="" enctype="multipart/form-data" method="post" id="fm">
			<table>
				<tr>
					<td>用户昵称：</td>
					<td><input type="text" id="nickName" name="nickName" value="${currentUser.nickName }" /></td>
				</tr>
				<tr>
					<td>心情</td>
					<td><input type="text" id="mood" name="mood" value="${currentUser.mood }" /></td>
				</tr>
				<tr>
					<td>用户头像：</td>
					<td><input type="file" id="imagePath" name="imagePath" /></td>
				</tr>
			</table>
			
		</form>
	</div>


	<div id="panel-buttons">
		<a href="javascript:userSave()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	</div>
</body>
</html>