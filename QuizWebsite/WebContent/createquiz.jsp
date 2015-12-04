<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="db.*"
    import="entities.*"
    import="java.util.*"%>
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
		
	<div id = "filler">
	</div>
	<div id ="content">
		<div id ="form">
		<form action="QuizNameSaveServlet" method="post">
		Quiz Name:
		<br>
		<input type="text" name="name" placeholder="New Quiz" <%out.print("value=\"" + quizName + "\"");%> />
		<br>
		<input type="submit" value="Save"/>
		</p>
		</form>
		</div>
		<div id = "form">
		<form action="QuizDescSaveServlet" method="post" id="descform">
		<p>
		Description:
		<br>
		<textarea name="desc" placeholder="Quiz Description")><%out.print(quizDesc);%></textarea>
		<br>
		<input type="submit" value="Save"/>
		</p>
		</form>
		</div>
		
		<%ArrayList<QuestionAbstract> questions = QuizHelper.getQuizQuestions(conn, quizID);
			for (int i = 0; i < questions.size(); ++i) {
				out.println("<div id =\"form\">");
				QuestionAbstract curr = questions.get(i);
				//String answer = "";
				//if (!curr.getAnswers().isEmpty()) answer = curr.getAnswers().get(0);
				int questionID = curr.getQuestionID();
				int type = curr.getType();
				String answer = curr.getAnswer();
				switch (type) {
					case QuestionHelper.MULTIPLE_CHOICE:
						ArrayList<String> options = ((MultipleChoice)curr).getOptions();
						int numOptions = options.size();
						out.println("<form action=\"MCServlet\" method=\"post\">");
						out.println("<input type=\"hidden\" name=\"questionID\" value=" + questionID + " />");
						out.println("<input type=\"hidden\" name=\"numOptions\" value=\"" + numOptions + "\" />");
						out.println("<p>");
						out.println("Question " + i + ":");
						out.println("<br>");
						out.println("<input type=\"text\" name=\"text\" placeholder=\"Question Text\" value=\"" + ((MultipleChoice)curr).getText() + "\" />");
						//out.println("<br>");
						out.println("<ol type=\"A\">");
						for (int optionNum = 0; optionNum < numOptions; ++optionNum) {
							out.println("<li><input type=\"text\" name=\"option" + optionNum + "\" placeholder=\"Option " + optionNum + "\" value=\"" + options.get(optionNum) + "\" /></li>");
							//out.println("<br>");
						}
						out.println("<li><input type=\"text\" name=\"option" + numOptions + "\" placeholder=\"Option " + numOptions + "\" /></li>");
						out.println("</ol>");
						//out.println("<br>");
						out.println("<input type=\"text\" name=\"answer\" placeholder=\"Answer\" value=\"" + answer + "\"");
						out.println("<br>");
						out.println("<input type=\"submit\" value=\"Save\"/>");
						out.println("</p>");
						out.println("</form>");
						break;
					case QuestionHelper.QUESTION_RESPONSE:
						out.println("<form action=\"QRServlet\" method=\"post\">");
						out.println("<input type=\"hidden\" name=\"questionID\" value=" + questionID + " />");
						out.println("<p>");
						out.println("Question " + i + ":");
						out.println("<br>");
						out.println("<input type=\"text\" name=\"text\" placeholder=\"Question Text\" value=\"" + ((QuestionResponse)curr).getText() + "\"");
						out.println("<br>");
						//out.println("<input type=\"submit\" value=\"Save\"/>");
						out.println("<br>");
						out.println("<input type=\"text\" name=\"answer\" placeholder=\"Answer\" value=\"" + answer + "\"");
						out.println("<br>");
						out.println("<input type=\"submit\" value=\"Save\"/>");
						out.println("</p>");
						out.println("</form>");
						break;
					case QuestionHelper.FILL_IN_BLANK:
						out.println("<form action=\"FBServlet\" method=\"post\">");
						out.println("<input type=\"hidden\" name=\"questionID\" value=" + questionID + " />");
						out.println("<p>");
						out.println("Question " + i + ":");
						out.println("<br>");
						out.println("<input type=\"text\" name=\"textBefore\" placeholder=\"Text Before Blank\" value=\"" + ((FillBlank)curr).getTextBefore() + "\"");
						out.println("<br>");
						out.println("<input type=\"text\" name=\"answer\" placeholder=\"Answer\" value=\"" + answer + "\"");
						out.println("<br>");
						out.println("<input type=\"text\" name=\"textAfter\" placeholder=\"Text After Blank\" value=\"" + ((FillBlank)curr).getTextAfter() + "\"");
						out.println("<br>");
						out.println("<input type=\"submit\" value=\"Save\"/>");
						out.println("</p>");
						out.println("</form>");
						break;
					case QuestionHelper.PICTURE_RESPONSE:
						out.println("<form action=\"PRServlet\" method=\"post\">");
						out.println("<input type=\"hidden\" name=\"questionID\" value=" + questionID + " />");
						out.println("<p>");
						out.println("Question " + i + ":");
						out.println("<br>");
						out.println("<input type=\"text\" name=\"text\" placeholder=\"Image URL\" value=\"" + ((PictureResponse)curr).getText() + "\"");
						out.println("<br>");
						//out.println("<input type=\"submit\" value=\"Save\"/>");
						out.println("<br>");
						out.println("<input type=\"text\" name=\"answer\" placeholder=\"Answer\" value=\"" + answer + "\"");
						out.println("<br>");
						out.println("<input type=\"submit\" value=\"Save\"/>");
						out.println("</p>");
						out.println("</form>");
						break;
					}
				out.println("</div>");
			}
		%>
		
		<div id="form">
		<form action="AddQuestionServlet" method="post">
		<p style="white:color">Add a new question:
		<br>
		<select name="qtype">
		<option selected="selected" disabled="disabled">Select a question type...</option>
		<option value="MULTIPLE_CHOICE">Multiple Choice</option>
		<option value="QUESTION_RESPONSE">Question-Response</option>
		<option value="FILL_IN_BLANK">Fill in the Blank</option>
		<option value="PICTURE_RESPONSE">Picture-Response</option>
		</select>
		<input type="submit" value="Add"/>
		</p>
		</form>
		</div>
		
		<form action="QuizCreationServlet" method="post">
		<p><input type="submit" value="Finish!"/></p>
		</form>
	</div>
</div>
</body>
</html>