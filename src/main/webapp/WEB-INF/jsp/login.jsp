<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>
	<script src="./resources/vendors/jquery.js"></script>
	<script type="text/javascript" src="./resources/common/js/global.js"></script>
	<%-- <form:form id="loginForm" modelAttribute="login" action="loginProcess"
		method="post"> --%>
		<table align="center">
			<tr>
				<td><label path="userName">Username: </label></td>
				<td><input path="userName" name="userName" id="userName" />
				</td>
			</tr>
			<tr>
				<td><label path="password">Password:</label></td>
				<td><input type="password" path="password" name="password" id="password" /></td>
			</tr>
			<tr></tr>
			<tr>
				<td></td>
				<td><button id="loginProcess" name="loginProcess" onclick="Global.loginProcess()">Login</button>
				</td>
				<%-- <td align="left"><form:button id="login" name="login">Login</form:button>
				</td> --%>
			</tr>	
			<tr></tr>
			<tr></tr>	
			<tr></tr>		
			<tr>
				<td></td>
				<td><a href="/">Home</a></td>
			</tr>		
		</table>
	<%-- </form:form> --%>	
	<table align="center">
		<tr>
			<td style="font-style: italic; color: red;">${message}</td>
		</tr>
	</table>
</body>
</html>
