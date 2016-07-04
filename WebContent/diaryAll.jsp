<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<script src="${pageContext.request.contextPath}/My97DatePicker/WdatePicker.js"></script>

<!-- 	引入ckeditor，一个在线文本编辑器 -->
<script src="${pageContext.request.contextPath}/js/ckeditor/ckeditor.js"></script>

<script type="text/javascript">
	var url;
    function openAddDlg ()
    {
	    $ ("#dlg").dialog ("open").dialog ("setTitle", "添加日记");
	    url = '${pageContext.request.contextPath}/diary?action=save';
    }

    function openModifyDlg ()
    {
	    var selections = $ ("#dg").datagrid ("getSelections");
	    if (selections.length != 1)
	    {
		    $.messager.alert ("tip", "只能选择一条数据");
		    return;
	    }
	    $ ("#dlg").dialog ("open").dialog ("setTitle", "修改日记");
	    var row = selections[0];
	    url = "${pageContext.request.contextPath}/diary?action=save&diaryId=" + row.diaryId;
	    CKEDITOR.instances.content.setData (row.content);
	    $ ("#fm").form ("load", row);
    }

    //dialog的关闭/打开事件 要写在function中
    $ (function ()
    {
	    $ ('#dlg').dialog (
	    {
		    onClose : function ()
		    {
			    $ ("#title").val ("");
			    $ ("#typeId").combobox ("setValue", "");
			    //	CKEDITOR.instances.XXXXX.setData 其中XXXXX 要是 textarea 的id值
			    CKEDITOR.instances.content.setData ("");
		    },
	    });
	    
    })

    function closeDialog ()
    {
	    $ ("#dlg").dialog ("close");
	    $ ("#title").val ("");
	    $ ("#typeId").combobox ("setValue", "");
	    // 	  一，在页面中加载CKEDITOR编辑器：
	    // 	    1：CKEDITOR.replace（element,config):用编辑器取代element
	    // 	    2：CKEDITOR.appendTo(element,config,html)：在element后面添加编辑器
	    // 	    二，常用的方法：
	    // 	    获得编辑器中的内容：getData();
	    // 	    设置编辑器中的内容：setData();
	    // CKEDITOR.instances.XXXXX.setData 其中XXXXX 要是 textarea 的id值
	    CKEDITOR.instances.content.setData ("");
    }

    function saveDiary ()
    {
	    var title = $ ("#title").val ();
	    //CKEDITOR.instances.XXXXX.setData 其中XXXXX 要是 textarea 的id值
	    var content = CKEDITOR.instances.content.getData ();
	    var typeId = $ ("#typeId").combobox ("getValue");
	    if (title == null || title == "")
	    {
		    $.messager.alert ("tips", "标题不能为空");
		    return;
	    }
	    if (content == null || content == "")
	    {
		    $.messager.alert ("tips", "内容不能为空");
		    return;
	    }
	    if (typeId == null || typeId == "")
	    {
		    $.messager.alert ("tips", "类别不能为空");
		    return;
	    }
	    $ ("#fm").form ("submit",
	    {
		    url : url,
	    // 	        可省略
	    // 	        onSubmit : function ()
	    // 	        {
	    // 	        },
	    // 	        success : function (result)
	    // 	        {
	    // 	        }
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

    function formatContent (val, row)
    {
	    return val + ".....";
    }

    function diaryDetail ()
    {
	    var selections = $ ("#dg").datagrid ("getSelections");
	    if (selections.length != 1)
	    {
		    $.messager.alert ("tip", "只能选择一条数据");
		    return;
	    }
	    var row = selections[0];
	    $ ("#dlg2").dialog ("open").dialog ("setTitle", "查看日记详情");
	    $ ("#fm2").form ("load", row);
    }

    function searchDiary ()
    {
	    $ ("#dg").datagrid ("load",
	    {
	        "s_title" : $ ("#s_title").val (),
	        "releaseDate" : $ ("#releaseDate").val ()
	    });
    }

    function closeDialog2 ()
    {
	    $ ("#dlg2").dialog ("close");
    }

    function removeDiary ()
    {
	    var selections = $ ("#dg").datagrid ("getSelections");
	    if (selections.length != 1)
	    {
		    $.messager.alert ("tip", "只能选择一条数据");
	    }
	    var row = selections[0];
	    $.messager.confirm ("tip", "确认删除？", function (r)
	    {
		    if (r)
		    {
			    $.post ("${pageContext.request.contextPath}/diary?action=delete",
			    {
				    diaryId : row.diaryId
			    })
		    }
		    $.messager.confirm ("tip", "删除成功", function (r)
		    {
			    if (r)
			    {
				    $ ("#dg").datagrid ("reload");
			    }
		    });
	    })

    }
</script>
</head>
<body>

	<table id="dg" title="用户管理" class="easyui-datagrid" fitColumns="true" pagination="true" rownumbers="true"
		url="${pageContext.request.contextPath}/diaryList" fit="true" toolbar="#tb">
		<thead>
			<tr>
				<th field="cb" checkbox="true" align="center"></th>
				<th field="diaryId" width="50" align="center">编号</th>
				<th field="title" width="100" align="center">标题</th>
				<th hidden="true" field="content" width="100" align="center">内容</th>
				<th field="typeName" width="150" align="center">类别名称</th>
				<th field="releaseDate" width="150" align="center">发布日期</th>
			</tr>
		</thead>
	</table>


	<div id="tb" style="padding: 5px; height: auto">
		<div style="margin-bottom: 5px">
			<a href="javascript:openAddDlg()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
			<a href="javascript:openModifyDlg()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑</a>
			<a href="javascript:removeDiary()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
			<a href="javascript:diaryDetail()" class="easyui-linkbutton" iconCls="icon-tip" plain="true">查看详情</a>
		</div>

		<div>
			日记标题：
			<input type="text" id="s_title" name="s_title" onkeydown="if(event.keyCode==13) searchDiary()" />
			&nbsp;&nbsp; 发布日期：
			<input type="text" id="releaseDate" class="Wdate" onClick="WdatePicker()" name="releaseDate"
				onkeydown="if(event.keyCode==13) searchDiary()" />
			<%-- value="<fmt:formatDate value="${question.joinTime }" type="date" pattern="yyyy-MM-dd"/>" /> --%>
			<a class="easyui-linkbutton" iconCls="icon-search" href="javascript:searchDiary()">搜索</a>
		</div>
	</div>


	<div id="dlg" class="easyui-dialog" style="width: 800px; height: 500px; padding: 10px;20px;" closed="true" buttons="#dlg-buttons">
		<form action="" method="post" id="fm">
			<table cellspacing="8px;">
				<tr>
					<td>日记标题：</td>
					<td><input type="text" id="title" name="title" class="easyui-validatebox" required="true" /> &nbsp;<font color="red">*</font>&nbsp;</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>日记类别：</td>
					<!-- <td><input class="easyui-combobox" id="typeId" name="typeId" required="true" -->
					<%-- 	data-options="panelHeight:'auto',editable:false,valueField:'diaryTypeId',textField:'typeName',url:'${pageContext.request.contextPath}/diaryTypeList'" /> --%>
					<td><select class="easyui-combobox" id="typeId" name="typeId" style="width: 154px;" editable="false" panelHeight="auto">
							<option value="">请选择...</option>
							<c:forEach var="diaryType" items="${ diaryTypeCountList}">
								<option value="${diaryType.diaryTypeId }">${diaryType.typeName }</option>
							</c:forEach>
						</select> &nbsp; <font color="red">*</font> &nbsp;</td>
				</tr>
				<tr>
					<td>日记内容：</td>
					<td colspan="4"><textarea rows="3" cols="50" required="true" class="ckeditor" id="content" name="content"></textarea></td>
				</tr>
			</table>
		</form>
	</div>

	<div id="dlg2" class="easyui-dialog" style="width: 800px; height: 400px; padding: 10px;20px;" closed="true" buttons="#dlg2-buttons">
		<form action="" id="fm2" method="post">
			<table cellspacing="8px;">
				<tr>
					<td>标题</td>
					<td colspan="2"><input type="text" id="title" name="title" readonly="readonly" /></td>
				</tr>
				<tr>
					<td>日记类别</td>
					<td colspan="2"><input type="text" id="typeName" name="typeName" readonly="readonly" /></td>
				</tr>
				<tr>
					<td>日记内容</td>
					<td colspan="9"><textarea style="width: 600px; height: 200px;" rows="8" cols="10" name="content" id="content"
							readonly="readonly"></textarea></td>
				</tr>
			</table>
		</form>
	</div>


	<div id="dlg-buttons">
		<a href="javascript:saveDiary()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
		<a href="javascript:closeDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>

	<div id="dlg2-buttons">
		<a href="javascript:closeDialog2()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>


</body>
</html>