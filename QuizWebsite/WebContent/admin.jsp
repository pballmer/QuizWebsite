<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="db.*"
    import="entities.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Settings </title>
<% String name =(String)session.getAttribute("name"); 
	ServletContext context = pageContext.getServletContext();
	DBConnection conn = (DBConnection)context.getAttribute("Database Connection");
	String message = request.getParameter("message");
	String color = request.getParameter("color");%>
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
				
				User user = UserHelper.getUserByID(conn, name);
				if (user.isAdmin())
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
	<div id ="content">
		<%
			if (message != null)
			{
				out.println("<div id=\"form\">");
				out.println("<h2 style=\"color:" + color + "\">" + message + "</h2>");
				out.println("</div>");
			}
		%>
		<h2 style="color:white">Site Statistics</h2>
			<div id="form">
				<h5>Number of Users: </h5>
				<p> <%= UserHelper.getTotalNumUsers(conn) %> </p>
				<h5>Number of Quizzes: </h5>
				<p> <%= QuizHelper.getTotalNumQuizzes(conn) %> </p>
			</div>
		<br>
		<h2 style="color:white">Create Announcements</h2>
			<div id="form">
				<form action="AdministrationServlet" method="post">
				<p>Text:
				<textarea name="text" rows="30" cols="50">
				</textarea>
				</p>
				<input type="hidden" name="type" value="announcement"/>
				<input type="submit" value="Create"/>
				</form>
			</div>
			<br>
		<h2 style="color:white">Remove User Accounts</h2>
			<div id="form">
				<form action="AdministrationServlet" method="post">
				<p> Username:
				<input type="text" name="user"/>
				</p>
				<input type="hidden" name="type" value="user"/>
				<input type="submit" value="Remove" />
				</form>
			</div>
			<br>
		<h2 style="color:white">Remove Quizzes</h2>
			<div id="form">
				<form action="AdministrationServlet" method="post">
				<p> ID or Link:
				<input type="text" name="link"/>
				</p>
				<input type="hidden" name="type" value="quiz"/>
				<input type="submit" value="Remove" />
				</form>
			</div>
			<br>
		<h2 style="color:white">Remove Quiz Histories </h2>
			<div id="form">
				<form action="AdministrationServlet" method="post">
				<p> ID or Link:
				<input type="text" name="link"/>
				</p>
				<input type="hidden" name="type" value="quizHistory"/>
				<input type="submit" value="Remove" />
				</form>
			</div>
			<br>
		<h2 style="color:white">Promote User to Administration</h2>
			<div id="form">
				<form action="AdministrationServlet" method="post">
				<p> Username
				<input type="text" name="name"/>
				</p>
				<input type="hidden" name="type" value="admin"/>
				<input type="submit" value="Promote" />
				</form>
			</div>
	</div>
</body>
</html>