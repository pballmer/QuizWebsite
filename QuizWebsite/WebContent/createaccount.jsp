<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Account </title>
<% String name =(String)session.getAttribute("name"); %>
<link rel="stylesheet" type="text/css" href="main.css">

</head>
<body>



	<div id = "container">
	
	<div id = "sidebar">
		<p style="color: white; margin: 0;"> No account? <a href="createaccount.jsp">Sign up. </a></p>
		<div id = "profpic">
		</div>
		<div id = "sidebar-content">
		
			<%if (name != null)
				{
				out.println("<h1>Welcome, " + name + ".</h1>");
				out.println("<a href=\"#createQuiz\" class=\"reg-button\">Create Quiz</a>");
				out.println("<a href=\"#browseQuiz\" class=\"reg-button\"> Browse Quiz</a>");
				out.println("<br>");
				out.println("<a href =\"#browseUser\" class = \"reg-button\"> Browse Users</a>");
				out.println("<a href =\"#history\" class = \"reg-button\"> Quiz History</a>");
				out.println("<br>");
				out.println("<a href = \"#logout\" class =\"big-button\"> Log out</a>");
				}
				else{
					out.println("<h1>Welcome, " + "stranger. </h1>");
					out.println("<h3> Please <a href=\"login.jsp\" class=\"reg-button\"> login.</a></h3>");
				}
			%>
			

		</div>
		
	</div>
			
	<div id = "logo">
		<h1> Quiz08 </h1>
	</div>
	</div>

	
	<div id = "filler">
	</div>
	
	<div id = "content">
	<h1>Create New Account</h1>

<div id="form">
	<p>Please enter a proposed username and password.</p>
	<form action="AccountCreationServlet" method="post">
	<p>User Name:
	<input type="text" name="name" />
	<p>Password:
	<input type="password" name="pass" />
	<input type="submit" value="Create account"/></p>
	</form>
</div>
	</div>
</body>
</html>