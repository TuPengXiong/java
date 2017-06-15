<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>">
</head>
<script type="text/javascript" src="js/index.js"></script>
<link rel="stylesheet" type="text/css" href="css/index.css">
<title>Login Or Sign</title>
<body>
	<form class="form" action="login.do" method="post">
		<h3>Logon or Sign</h3>
		<div>
			<span class="lable">用户名</span><input name="username" type="text"></input>
		</div>

		<div>
			<span class="lable">密    码</span> <input name="password" type="password"></input>
		</div>

		<div>
			<span>
				<button class="button">提交</button>
			</span>
		</div>
	</form>
</body>
</html>