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
<title>Browse Quizzes</title>
<% String name =(String)session.getAttribute("name");
ServletContext context = pageContext.getServletContext();
DBConnection conn = (DBConnection) context.getAttribute("Database Connection");
String tag = (String)request.getParameter("tag");
String message = (String)request.getParameter("message");
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
		<%
			if (message != null)
			{
				out.println("<h3 style=\"color:red\">Error when searching for tag.</h3>");
			}
		%>
		<h1 style="color:white"> What tag would you like to search?</h1>
			<div id = "form">
				<form action="QuizSearchServlet" method="post">
				<p>Tag:
				<input type="text" name="tag" />
				<input type="submit" value="Search"/></p>
				</form>
			</div>
			
		<%
			if (tag!= null)
			{
				ArrayList<Integer> ids = QuizHelper.getQuizIDsFromTag(conn, tag);
				out.println("<br><div id=\"form\"");
				if (ids.size() == 0)
				{
					out.println("<p> No quizzes found with the tag " + tag + "</p>");
				}
				else
				{
					out.println("<p><b> Quizzes found: </b> </p>");
					out.println("<ul>");
					for (int i = 0; i < ids.size(); i++)
					{
						Quiz quiz = QuizHelper.getQuizByID(conn, ids.get(i));
						out.println("<li><a href=\"quizsummary.jsp?id=" + quiz.getId() + "\">" + quiz.getName() + "</a></li>");
					}
					out.println("</ul>");
				}
				out.println("</div>");
			}
		%>
		<h1 style="color:white">List of all quizzes</h1>
			<div id="form">
				<ul>
					<%
						ArrayList<Quiz> quizzes = QuizHelper.getQuizzes(conn, -1, "", "");
						for (int i = 0; i < quizzes.size(); i++)
						{
							Quiz quiz = quizzes.get(i);
							out.println("<li><a href=\"quizsummary.jsp?id=" + quiz.getId() + "\">" + quiz.getName() + "</a></li>");
						}
					%>
				</ul>
		   </div>
	</div>
</body>
</html>