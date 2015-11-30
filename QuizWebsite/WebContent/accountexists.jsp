<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create Account</title>
</head>
<body>
<h1>The Name <%= request.getParameter("name") %> is Already In Use</h1>
<p>Please enter another username and password.</p>
<form action="CreationServlet" method="post">
<p>User Name:
<input type="text" name="name" />
<p>Password:
<input type="password" name="pass" />
<input type="submit" value="Create account"/></p>
</form>
</body>
</html>