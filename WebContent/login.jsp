<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" import="model.User"%>

<%
	// 第一次登陆
	if (request.getAttribute("user") == null) {
		String userName = "";
		String password = "";
		// 		从cookie中获值
		Cookie[] cookies = request.getCookies();
		for (int i = 0; cookies != null && i < cookies.length; i++) {
			if (cookies[i].getName().equals("user")) {
				// 				存储的Cookie形式 （key-value）形式
				// 				Cookie cookie=new Cookie("user", userName+"-"+password);
				userName = cookies[i].getValue().split("-")[0];
				password = cookies[i].getValue().split("-")[1];
			}
		}

		// 		EL表达式优先从Page范围寻找数据
		User user = new User(userName, password);
		pageContext.setAttribute("user", user);
	}
%>
<!-- html5的标准 -->
<html lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- bootstrap的响应式开发 根据不同的设备，选择合适的大小 -->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>个人日记本登录</title>

<!-- 都使用绝对路径 -->
<!--核心CSS文件-->
<link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap3/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap3/css/bootstrap-responsive.css">
<!--Jquery文件，且必须在bootstrap.min.js之前引入-->
<script src="${pageContext.request.contextPath}/bootstrap3/js/jQuery.js"></script>
<!--核心JavaScript文件-->
<script src="${pageContext.request.contextPath}/bootstrap3/js/bootstrap.min.js"></script>

<style type="text/css">
/* CSS样式 */
body {
	padding-top: 200px;
	padding-bottom: 40px;
	background-image: url('images/star.gif');
}

.form-signin-heading {
	text-align: center;
}

.form-signin {
	max-width: 300px;
	padding: 19px 29px 0px;
	margin: 0 auto 20px;
	background-color: #fff;
	border: 1px solid #e5e5e5;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	border-radius: 5px;
	-webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	-moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
}

.form-signin .form-signin-heading, .form-signin .checkbox {
	margin-bottom: 10px;
}

/* 带属性的CSS样式选择器 */
.form-signin input[type="text"], .form-signin input[type="password"] {
	font-size: 16px;
	height: auto;
	margin-bottom: 15px;
	padding: 7px 9px;
}
</style>
<!-- JS方法 -->
<script type="text/javascript">
	function checkForm ()
    {
	    var userName = document.getElementById ("userName").value;
	    var password = document.getElementById ("password").value;
	    var yangshi = document.getElementById ("yangshi").value;
	    if (userName == null || userName == "")
	    {
		    document.getElementById ("error").innerHTML = "用户名不能为空";
		    //表单不提交
		    return false;
	    }
	    if (password == null || password == "")
	    {
		    document.getElementById ("error").innerHTML = "密码不能为空";
		    return false;
	    }
	    if (yangshi == null || yangshi == "")
	    {
		    document.getElementById ("error").innerHTML = "请选择样式";
		    return false;
	    }
	    //提交表单
	    return true;
    }
</script>

</head>
<body>
	<!-- class为bootstrap的布局样式  固定长度布局-->
	<div class="container">
		<!-- class为bootstrap的布局样式 onsubmit为提交时执行的方法-->
		<form name="myForm" class="form-signin" action="login" method="post" onsubmit="return checkForm()">
			<h2 class="form-signin-heading">日记本</h2>
			<!-- class为bootstrap的布局样式 块级输入框（独占一行） placeholder为提示-->
			<input id="userName" name="userName" type="text" value="${ user.userName}" class="input-block-level" placeholder="用户名">
			<input id="password" name="password" type="password" value="${ user.password}" class="input-block-level" placeholder="密码">
			<label class="checkbox">
				<input id="remember" name="remember" type="checkbox" value="remember-me">
				记住我 &nbsp;&nbsp;&nbsp;&nbsp;
				<font id="error" color="red">${error }</font>
			</label>
			<select class="form-control" id="yangshi" name="yangshi">
				<option value="">选择要呈现的页面样式</option>
				<option value="b">Bootstrap3样式</option>
				<option value="e">EasyUi样式</option>
			</select>
			<br />
			<!-- class为按钮的样式 btn-large为大号的按钮 btn-primary为按钮的颜色 -->
			<button class="btn btn-large btn-primary" type="submit">登录</button>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<button class="btn btn-large btn-primary" type="button">重置</button>
		</form>
	</div>
</body>
</html>