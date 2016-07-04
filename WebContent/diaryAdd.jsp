<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 都使用绝对路径 -->
<!-- ！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！ -->
<!-- 要保证jquery.js文件最新的一次载入是在这些控件载入之前！！！！！！！！！！！！ -->
<!-- ！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！ -->
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>


<!-- 	引入ckeditor，一个在线文本编辑器 -->
<script src="${pageContext.request.contextPath}/js/ckeditor/ckeditor.js"></script>

<script type="text/javascript">
	var url;
    function submitForm ()
    {
	    url = "${pageContext.request.contextPath}/diary?action=save";
	    var content = CKEDITOR.instances.content.getData ();
	    var typeId = $ ("#typeId").combobox ("getValue");
	    var title = $ ("#title").val ();
	    if (title == null || title == '')
	    {
		    $.messager.alert ("tip", "title is needed");
		    return;
	    }
	    if (content == null || content == '')
	    {
		    $.messager.alert ("tip", "content is needed");
		    return;
	    }
	    if (typeId == null || typeId == '')
	    {
		    $.messager.alert ("tip", "typeId is needed");
		    return;
	    }
	    $ ('#ff').form ("submit",
	    {
	        url : url,
	        onSubmit : function ()
	        {
		        return $ (this).form ("validate");
	        },
	        success : function (result)
	        {
		        $.messager.alert ("tip", "success");
	        }
	    
	    });
    }
    function clearForm ()
    {
	    $ ("#title").val ("");
	    $ ("#typeId").combobox ("setValue", "");
	    CKEDITOR.instances.content.setData ("");
    }
</script>
</head>
<body>

	<div style="margin: 10px 0;"></div>
	<div class="easyui-panel" title="添加日记" style="width: 900px; height: 450px;">
		<div style="padding: 10px 0 10px 60px">
			<form id="ff" action="" method="post">
				<table>
					<tr>
						<td>日记标题:</td>
						<!-- required=true 与 easyui-validatebox 一起使用 -->
						<td><input class="easyui-validatebox" type="text" id="title" name="title" data-options="required:true"></input></td>
					</tr>
					<tr>
						<td>日记内容:</td>
						<td><textarea id="content" name="content" rows="5" cols="10" class="ckeditor"></textarea></td>
					</tr>
					<tr>
						<td>日记类别:</td>
						<td><select class="easyui-combobox" panelHeight="auto" id="typeId" name="typeId">
								<option value="">请选择...</option>
								<c:forEach var="diaryType" items="${ diaryTypeCountList}">
									<option value="${diaryType.diaryTypeId }">${diaryType.typeName }</option>
<									</c:forEach>
							</select></td>
					</tr>
				</table>
			</form>
		</div>
		<div style="text-align: center; padding: 5px" align="center">
			<a class="easyui-linkbutton" href="javascript:submitForm()">提交</a>
			<a class="easyui-linkbutton" href="javascript:clearForm()">重置</a>
		</div>
	</div>


</body>
</html>