<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
   	import = "db.*"
   	import = "entities.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Information Incorrect</title>
<% String name =(String)session.getAttribute("name"); 
	ServletContext context = pageContext.getServletContext();
	DBConnection conn = (DBConnection) context.getAttribute("Database Connection");%>
<link rel="stylesheet" type="text/css" href="main.css">

</head>
<body>
	<div id = "container">
	
	<div id = "sidebar">
		<%if (name == null) out.println("<p style=\"color: white; margin: 0;\"> No account? <a href=\"createaccount.jsp\">Sign up. </a></p>");%>
		<div id = "profpic">
		</div>
		<div id = "sidebar-content">
			<%if (name != null)
				{
				out.println("<h1>Welcome, " + name + ".</h1>");
				out.println("<a href=\"#createQuiz\" class=\"reg-button\">Create Quiz</a>");
				out.println("<a href=\"browseQuiz.jsp\" class=\"reg-button\"> Browse Quiz</a>");
				out.println("<br>");
				out.println("<a href =\"allusers.jsp\" class = \"reg-button\"> Browse Users</a>");
				out.println("<a href =\"quizHistory.jsp\" class = \"reg-button\"> Quiz History</a>");
				
				User check = UserHelper.getUserByID(conn, name);
				if (check.isAdmin())
				{
					out.println("<br>");
					out.println("<a href=\"admin.jsp\" class =\"big-button\"> Admin Settings </a>");
				}
				out.println("<br>");
				out.println("<a href = \"logout.jsp\" class =\"big-button\"> Log out</a>");
				
				}
				else{
					out.println("<h1>Welcome, " + "stranger. </h1>");
					out.println("<h3> Please <a href=\"login.jsp\" class=\"reg-button\"> login.</a></h3>");
				}
			

			%>
			<br>
			<a href ="index.jsp" class ="big-button"> Home </a>

		</div>
		
	</div>
			
	<div id = "logo">
		<h1> Quiz08 </h1>
	</div>
	</div>

	
	<div id = "filler">
	</div>
	
	<br>
	
	<div id ="content">
		<div id = "form">
			<h1>Please Try Again</h1>
				<p>Either your user name or password is incorrect. Please try again.</p>
				<form action="LoginServlet" method="post">
				<p>User Name:
				<input type="text" name="name" />
				<p>Password:
				<input type="text" name="pass" />
				<input type="submit" value="Login"/></p>
				</form>
				<a href="createaccount.jsp">Create New Account</a>
			</div>
	</div>
</body>
</html>