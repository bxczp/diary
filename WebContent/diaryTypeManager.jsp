<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<title>Insert title here</title>
<script type="text/javascript">
	$ (function ()
    {
	    $ ("#dlg").dialog (
	    {
		    onClose : function ()
		    {
			    $ ("#typeName").val ("");
			    $ ("#diaryTypeId").val ("");
		    }
	    })
    });
</script>
<script type="text/javascript">
	var url;
    
    function openAddDlg ()
    {
	    $ ("#dlg").dialog ("open").dialog ("setTitle", "添加日记类别");
	    url = "${pageContext.request.contextPath}/diaryType?action=save";
    }

    function openModifyDlg ()
    {
	    var selections = $ ("#dg").datagrid ("getSelections");
	    if (selections.length != 1)
	    {
		    $.messager.alert ("tip", "选择一条数据");
		    return;
	    }
	    var row = selections[0];
	    $ ("#dlg").dialog ("open").dialog ("setTitle", "修改日记类别");
	    $ ("#fm").form ("load", row);
	    url = "${pageContext.request.contextPath}/diaryType?action=save&diaryTypeId=" + row.diaryTypeId;
    }

    function removeDiaryType ()
    {
	    var selections = $ ("#dg").datagrid ("getSelections");
	    if (selections.length != 1)
	    {
		    $.messager.alert ("tip", "选择一条数据");
		    return;
	    }
	    var row = selections[0];
	    $.messager.confirm ("tip", "确认删除？", function (r)
	    {
		    if (r)
		    {
			    $.post ("${pageContext.request.contextPath}/diaryType?action=delete",
			    {
				    "diaryTypeId" : row.diaryTypeId
			    });
			    $.messager.confirm ("tip", "正在刷新页面...", function (r)
			    {
				    if (r)
				    {
					    $ ("#dg").datagrid ("reload");
				    }
			    });
		    }
	    });
    }

    function saveDiaryType ()
    {
	    var typeName = $ ("#typeName").val ();
	    if (typeName == null || typeName == '')
	    {
		    $.messager.alert ("tip", "类别名称不能为空");
		    return;
	    }
	    $ ("#fm").form ("submit",
	    {
		    url : url
	    })
	    closeDialog ();
	    $.messager.confirm ("tip", "正在刷新页面...", function (r)
	    {
		    if (r)
		    {
			    $ ("#dg").datagrid ("reload");
		    }
	    });
    }

    function closeDialog ()
    {
	    $ ("#typeName").val ("");
	    $ ("#diaryTypeId").val ("");
	    $ ("#dlg").dialog ("close");
    }
</script>

</head>
<body>
	<table id="dg" title="日记类别管理" class="easyui-datagrid" fitColumns="true" pagination="true" rownumbers="true"
		url="${pageContext.request.contextPath}/diaryTypeList" fit="true" toolbar="#tb">
		<thead>
			<tr>
				<th field="cb" checkbox="true" align="center"></th>
				<th field="diaryTypeId" width="50" align="center">编号</th>
				<th field="typeName" width="100" align="center">类别名称</th>
			</tr>
		</thead>
	</table>

	<div id="tb">
		<div style="margin-bottom: 5px">
			<a href="javascript:openAddDlg()" class="easyui-linkbutton" iconCls="icon-add" plain="true"></a>
			<a href="javascript:openModifyDlg()" class="easyui-linkbutton" iconCls="icon-edit" plain="true"></a>
			<a href="javascript:removeDiaryType()" class="easyui-linkbutton" iconCls="icon-remove" plain="true"></a>
		</div>
	</div>


	<div class="easyui-dialog" id="dlg" style="width: 400px; height: 300px;" closed="true" buttons="#dlg-buttons">
		<form action="" id="fm" method="post">
			<table cellpadding="8px;">
				<tr>
					<td>日记类别名称</td>
					<td><input type="text" id="typeName" name="typeName" /></td>
					<td><input type="hidden" id="diaryTypeId" name="diaryTypeId" /></td>
				</tr>
			</table>
		</form>
	</div>


	<div id="dlg-buttons">
		<a href="javascript:saveDiaryType()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
		<a href="javascript:closeDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>



</body>
</html>