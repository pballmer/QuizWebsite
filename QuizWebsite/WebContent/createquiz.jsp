<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="db.*"
    import="entities.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Quiz</title>
<%String name =(String)session.getAttribute("name");
ServletContext context = getServletContext();
DBConnection conn = (DBConnection) context.getAttribute("Database Connection");
Integer quizID = (Integer)session.getAttribute("quizID");
Quiz currQuiz = null;
String quizName = "";
String quizDesc = "";
if (quizID == null) {
	quizID = QuizHelper.addQuiz(conn, new Quiz());
	session.setAttribute("quizID", quizID);
} else {
	currQuiz = QuizHelper.getQuizByID(conn, quizID);
	quizName = currQuiz.getName();
	quizDesc = currQuiz.getDescription();
}
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
	
	<div id = "filler">
	</div>
	<div id ="content">
		<form action="QuizNameSaveServlet" method="post">
		<p>
		Quiz Name:
		<br>
		<input type="text" name="name" placeholder="New Quiz" <%out.print("value=\"" + quizName + "\"");%> />
		<br>
		<input type="submit" value="Save"/>
		</p>
		</form>
		
		<form action="QuizDescSaveServlet" method="post" id="descform">
		<p>
		Description:
		<br>
		<textarea name="desc" placeholder="Quiz Description")><%out.print(quizDesc);%></textarea>
		<br>
		<input type="submit" value="Save"/>
		</p>
		</form>
		
		<form action="AddQuestionServlet" method="post">
		Add a new question:
		<br>
		<select name="qtype">
		<option selected="selected" disabled="disabled">Select a question type...</option>
		<option value="MULTIPLE_CHOICE">Multiple Choice</option>
		<option value="QUESTION_RESPONSE">Question-Response</option>
		<option value="FILL_IN_BLANK">Fill in the Blank</option>
		<option value="PICTURE_RESPONSE">Picture-Response</option>
		</select>
		<input type="submit" value="Add"/>
		</form>
		
		<form action="QuizCreationServlet" method="post">
		<p><input type="submit" value="Finish!"/></p>
		</form>
	</div>
</div>
</body>
</html>