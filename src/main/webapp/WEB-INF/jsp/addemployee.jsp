<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration</title>
</head>
<body>
	<script src="./resources/vendors/jquery.js"></script>
	<script type="text/javascript" src="./resources/common/js/global.js"></script>
	
	
		<table align="center">
			<tr>
				<td><label>name</label></td>
				<td><input text="" name="name" id="name"/>
				</td>
			</tr>			
			<tr>
				<td><label >salary</label></td>
				<td><input text="" name="salary" id="salary"/></td>
			</tr>			
			<tr></tr>
			<tr></tr>
			<tr>
				<td></td>
				<td><button id="addEmployee" name="addEmployee" onclick="Global.addEmployee()">Add Employee</button>
				</td>
			</tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr>
				<td></td>
				<td><a href="/">Home</a></td>
			</tr>
		</table>
		<table align="center">
		<tr>
			<td style="font-style: italic; color: red;">${message}</td>
		</tr>
		</table>
	</body>
</html>