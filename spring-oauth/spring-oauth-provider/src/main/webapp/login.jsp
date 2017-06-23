<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>SPRING OUATH PROVIDER</title>
<link type="text/css" rel="stylesheet"
	href="http://ipaiban.com/xhrnew/css/bootstrap.min.css" />
<script type="text/javascript" src="http://ipaiban.com/xhrnew/js/jquery.min.js"></script>
<script type="text/javascript"
	src="http://ipaiban.com/xhrnew/js/bootstrap.min.js"></script>
</head>

<body>

	<div class="container">

		<h1>Login</h1>

		<c:if test="${not empty param.authentication_error}">
			<h1>Woops!</h1>

			<p class="error">Your login attempt was not successful.</p>
		</c:if>
		<c:if test="${not empty param.authorization_error}">
			<h1>Woops!</h1>

			<p class="error">You are not permitted to access that resource.</p>
		</c:if>

		<div class="form-horizontal">
			<p>a_admin/a_admin|a_user/a_user</p>
			<form action="<c:url value="/login"/>" method="post" role="form">
				<fieldset>
					<div class="form-group">
						<label for="username">Username:</label> <input id="username"
							class="form-control" type='text' name='username'
							value="a_admin" />
					</div>
					<div class="form-group">
						<label for="password">Password:</label> <input id="password"
							class="form-control" type='text' name='password' value="a_admin" />
					</div>
					<button class="btn btn-primary" type="submit">Login</button>
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</fieldset>
			</form>

		</div>

		<div class="footer">
			Sample application for <a
				href="http://github.com/spring-projects/spring-security-oauth"
				target="_blank">Spring Security OAuth</a>
		</div>

	</div>


</body>
</html>