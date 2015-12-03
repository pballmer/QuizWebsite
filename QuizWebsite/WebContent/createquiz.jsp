<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="db.*"
    import="entities.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String name = (String)session.getAttribute("name");
	ServletContext context = pageContext.getServletContext();
	DBConnection conn = (DBConnection) context.getAttribute("Database Connection");
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Quiz</title>
<link rel="stylesheet" type="text/css" href="main.css">
</head>
<body>




	<div id = "container">
	
	<div id = "sidebar">
		<%if (name == null) out.println("<p style=\"color: white; margin: 0;\"> No account? <a href=\"createaccount.jsp\">Sign up. </a></p>");%>
		<div id = "profpic">
		</div>
		<div id = "sidebar-content">
		
			<% if (name != null)
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
		<form action="QuizNameSaveServlet" method="post">
		<p>Quiz Name:</p>
		<p><input type="text" name="name" placeholder="New Quiz" /></p>
		<p><input type="submit" value="Save"/></p>
		</form>
		<form action="QuizDescSaveServlet" method="post" id="descform">
		<p>Description:</p>
		<!--<textarea name="desc" form="descform" placeholder="Quiz Description" rows=5></textarea> Might not need the form thing -->
		<textarea name="desc" placeholder="Quiz Description" rows=5></textarea>
		<p><input type="submit" value="Save"/></p>
		</form>
		
		<div class="panel">
		<form action="AddQuestionServlet" method="post">
		<p>Add a new question:</p>
		<select name="qtype">
		<option value="Multiple Choice">Multiple Choice</option>
		<option value="Question-Response">Question-Response</option>
		<option value="Fill in the Blank">Fill in the Blank</option>
		<option value="Picture-Response">Picture-Response</option>
		</select>
		<p><input type="submit" value="Add"/></p>
		</form>
		
		<form action="QuizCreationServlet" method="post">
		<p><input type="submit" value="Finish!"/></p>
		</form>
	</div>
</div>
</body>
</html>