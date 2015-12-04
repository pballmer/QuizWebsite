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
<title>Friend Request</title>
<% String name =(String)session.getAttribute("name");
String user = (String)request.getParameter("id");
String type = (String)request.getParameter("type");
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
				out.println("<a href=\"createquiz.jsp\" class=\"reg-button\">Create Quiz</a>");
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
		<div id ="form">
			<%
				if (type != null)
				{
					if (type.equals("accept"))
					{
						out.println("<h2>You and " + user + " are now friends!</h2>");
						NotificationsHelper.respondToFriendRequest(conn, user, name, NotificationsHelper.ACCEPTED);						
					}
					else if (type.equals("reject"))
					{
						out.println("<h2>You have rejected " + user + "'s friend request. </h2>");
						NotificationsHelper.respondToFriendRequest(conn, user, name, NotificationsHelper.REJECTED);						
					}
					else if (type.equals("pending"))
					{
						out.println("<h2>You have sent " + user + " a friend request! </h2>");
						NotificationsHelper.addFriendRequest(conn, name, user, NotificationsHelper.PENDING);
					}
				}
			%>

		</div>	
	</div>
</body>
</html>