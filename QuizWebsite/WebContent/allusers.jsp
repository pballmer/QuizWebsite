<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="db.*"
    import="java.util.*"
    import="entities.*"
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Browse Users </title>
<% String name =(String)session.getAttribute("name");
	String message = (String)request.getParameter("message");
ServletContext context = pageContext.getServletContext();
DBConnection conn = (DBConnection) context.getAttribute("Database Connection");
%>
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
	<div id ="content">
			<% 
				if (message != null)
				{
					out.println("<h3 style=\"color: red\"> User doesn't exist. Try again. </h3>");
				}
			%>
			<h1 style="color:white"> Who would you like to search?</h1>
			<div id = "form">
				<form action="UserServlet" method="post">
				<p>User Name:
				<input type="text" name="name" />
				<input type="submit" value="Search"/></p>
				</form>
			</div>
			<br>
			<div id="form">
				<h1>List of all members</h1>
				<ul>
					<%
						ArrayList<User> users = UserHelper.getUsers(conn, "", "", "");
						int total = (users.size() > 100) ? 100 : users.size();
						for (int i = 0; i < total; i++)
						{
							User user = users.get(i);
							out.println("<li><a href=\"user.jsp?id=" + user.getUsername() + "\">" + user.getUsername() + "</a></li>");
						}
					%>
				</ul>
		   </div>
	</div>
</body>
</html>