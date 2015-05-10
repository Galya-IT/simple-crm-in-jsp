<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>CRM Log-in</title>
	<link rel='stylesheet' href='http://codepen.io/assets/libs/fullpage/jquery-ui.css'>
	<link rel="stylesheet" href="./view/css/general.css" media="screen" type="text/css" />
	<link rel="stylesheet" href="./view/css/login.css" media="screen" type="text/css" />
</head>

<body>
	<c:if test="${not empty param.error}">
		<c:if test="${param.error == 'login'}">
			<div class="error">Wrong username or password</div>
		</c:if>
		<c:if test="${param.error == 'nologin'}">
			<div class="error">Please login first!</div>
		</c:if>
	</c:if>
	
	<div class="login-card">
		<h1>CRM Log-in</h1>
		<h3>user: admin, pass: qwerty</h3>
		<form action="Login" method="POST">
			<input type="text" name="user" placeholder="Username">
			<input type="password" name="pass" placeholder="Password">
			<input type="submit" name="login" class="login login-submit" value="login">
		</form>
	</div>

	<script src='http://codepen.io/assets/libs/fullpage/jquery_and_jqueryui.js'></script>
</body>
</html>