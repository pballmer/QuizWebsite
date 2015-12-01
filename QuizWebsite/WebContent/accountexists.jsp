<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create Account</title>
<link rel="stylesheet" type="text/css" href="main.css">
</head>
<body>
<h1>The Name <%= request.getParameter("name") %> is Already In Use</h1>
<p>Please enter another username and password.</p>
<form action="CreationServlet" method="post">
<fieldset>
<legend>Account Information</legend>
<p>Username:</p>
<p><input type="text" name="name" /></p>
<p>Password:</p>
<p><input type="password" name="pass" /></p>
<p><input type="submit" value="Create account"/></p>
</fieldset>
</form>
</body>
</html>