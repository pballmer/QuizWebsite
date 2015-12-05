<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="db.*"
    import="java.util.*"
    import="entities.*"
    import ="java.util.concurrent.TimeUnit"
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz Results</title>
<% String name =(String)session.getAttribute("name");
ServletContext context = pageContext.getServletContext();
DBConnection conn = (DBConnection) context.getAttribute("Database Connection");
Integer quizID = (Integer)session.getAttribute("quizID");
String id = request.getParameter("id").toString();
int quizIDOff = 0;
if (id != null)
{
	quizIDOff = Integer.parseInt(id);
}
//double score = QuizHelper.getScore(conn, quizID, name);
double score = QuizHelper.getScore(conn, quizIDOff, name);
long time = QuizHelper.getTimeDiff(conn, quizIDOff, name);
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
	
	<br>
	<div id = "content">
		<h1 style="color:white">Quiz Results:</h1>
		<div id = "form">
		<%	
			out.println("<h1>Score: " + score + "</h1>");
			out.println("<h2>Time: " + String.format("%d min, %d sec", 
				    TimeUnit.MILLISECONDS.toMinutes(time),
				    TimeUnit.MILLISECONDS.toSeconds(time) - 
				    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time))
				) + "</h2>");
		%>
		</div>
	</div>

</body>
</html>