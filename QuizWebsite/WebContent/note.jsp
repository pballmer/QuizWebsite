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
<title>Note</title>
<% String name =(String)session.getAttribute("name");
String NoteID = (String)request.getParameter("id");
String type = (String)request.getParameter("type");
String user = (String)request.getParameter("to");
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
		<div id ="form">
			<%
				if (type != null)
				{
					if (type.equals("send"))
					{
						out.println("<h1>You are sending a note to " + user + "</h4>");
						out.println("<form action=\"NotificationServlet\" method=\"post\">");
							out.println("<p>Note:");
							out.println("<textarea name=\"text\" rows=\"30\" cols=\"50\"/>");
							out.println("</textarea>");
							out.println("</p>");
							out.println("<input type=\"hidden\" name=\"type\" value=\"note\"/>");
							out.println("<input type=\"hidden\" name=\"recipient\" value=\"" + user + "\"/>");
							out.println("<input type=\"hidden\" name=\"sender\" value=\"" + name + "\"/>");
							out.println("<input type=\"submit\" value=\"Send!\"/>");
						out.println("</form>");				
					}
					else if (type.equals("read"))
					{
						out.println("<h2>" + user + " has sent you this note: </h2>");
						Note note = NotificationsHelper.getNote(conn, Integer.parseInt(NoteID));
						NotificationsHelper.readNote(conn, Integer.parseInt(NoteID));
						out.println("<p>" + note.getText() + "</p>");
						out.println("<a href=\"note.jsp?type=send&to=" + user + "\"> Reply? </a>");			
					}
					else if (type.equals("submit"))
					{
						out.println("<h1> Your note has been sent to " + user + "</h1>");
					}
				}
			%>

		</div>	
	</div>
</body>
</html>