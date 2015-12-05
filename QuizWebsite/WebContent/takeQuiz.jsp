<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="db.*"
    import ="java.util.*"
    import="entities.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Take quiz</title>
<% String name =(String)session.getAttribute("name");
String user = (String)request.getParameter("id");
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
	
	<br>
				<% //get Quiz title, list of quiz quesitons, 
					String id = request.getParameter("id").toString();
					//session.setAttribute("id", id);
					DBConnection dbconn = new DBConnection();
					Quiz quiz = QuizHelper.getFullQuizByID(conn, Integer.parseInt(id));
					List<QuestionAbstract> questions = quiz.getQuestions();
					/*
					if (quiz.isRandom()) Collections.shuffle(questions);
					boolean onePage = quiz.isOnePage();
					String pageString = request.getParameter("page");
					int pageNum = 0;
					if (!onePage && pageString != null) {
						pageNum = Integer.parseInt(pageString);
					}
					boolean immediateCorrection = quiz.isImmediateCorrection();
					*/
					//QuizHelper.addQuizToTake(conn, quiz, name);
				%>
	<div id ="content">		
		<div id ="form">
			<h1> You're Taking <%=quiz.getName() %></h1>
			<form action="ScoreServlet" method="post">
				<input type="hidden" name="quizID" value="<%=id%>" />
			
				<%
				//if (onePage || pageNum == 0) {
					QuizHelper.addQuizToTake(dbconn, quiz, name);
				//}
				Date date = new Date();
				long currTime = date.getTime();
				//if (onePage) {
					for(int i = 0 ; i < questions.size(); i++){
						QuestionAbstract curr = questions.get(i);
		
						switch (curr.getType()) {
							case QuestionHelper.MULTIPLE_CHOICE:
								MultipleChoice mc = (MultipleChoice) curr;
								out.println("<h2>" + mc.getText() + "<h2/>"); 
								for(int j = 0; j < mc.getOptions().size(); j++){
									out.println("<input type=\"radio\" name=\"question"+ i + "" +"\" value=\"" + mc.getOptions().get(j) + "\"/>" + mc.getOptions().get(j) +"<br>"); 
								}
								break;
							case QuestionHelper.QUESTION_RESPONSE:
								QuestionResponse questionResponse = (QuestionResponse) curr;
								out.println("<h2>" + questionResponse.getText() + "<h2/>");
								out.println("Answer: <input type=\"text\" name= \"question"+ i +"\" /><br>");	
								break;
							case QuestionHelper.FILL_IN_BLANK:
								FillBlank fillBlank = (FillBlank) curr;
								out.print("<h2>" +  fillBlank.getTextBefore() + "<h2>");
								out.print("<input type=\"text\" name= \"question"+ i +"\" />");
								out.print("<h2>" +  fillBlank.getTextAfter() + "</h2>");	
								break;
							case QuestionHelper.PICTURE_RESPONSE:
								PictureResponse pr = (PictureResponse) curr;
								out.println("<img src="+ pr.getText() +"><br>");
								out.println("Answer: <input type=\"text\" name= \"question"+ i +"\" alt=\"PictureResponse\" height=\"100\" width=\"100\" /><br>");
								break;
						}					
					}
				/*} else {
					QuestionAbstract curr = questions.get(pageNum);
	
					switch (curr.getType()) {
						case QuestionHelper.MULTIPLE_CHOICE:
							MultipleChoice mc = (MultipleChoice) curr;
							out.println("<h2>" + mc.getText() + "<h2/>"); 
							for(int j = 0; j < mc.getOptions().size(); j++){
								out.println("<input type=\"radio\" name=\"question"+ pageNum + "" +"\" />" + mc.getOptions().get(j) +"<br>"); 
							}
							break;
						case QuestionHelper.QUESTION_RESPONSE:
							QuestionResponse questionResponse = (QuestionResponse) curr;
							out.println("<h2>" + questionResponse.getText() + "<h2/>");
							out.println("Answer: <input type=\"text\" name= \"question"+ pageNum +"\" /><br>");	
							break;
						case QuestionHelper.FILL_IN_BLANK:
							FillBlank fillBlank = (FillBlank) curr;
							out.print("<h2>" +  fillBlank.getTextBefore() + "<h2>");
							out.print("<input type=\"text\" name= \"question"+ pageNum +"\" />");
							out.print("<h2>" +  fillBlank.getTextAfter() + "</h2>");	
							break;
						case QuestionHelper.PICTURE_RESPONSE:
							PictureResponse pr = (PictureResponse) curr;
							out.println("<img src="+ pr.getText() +"><br>");
							out.println("Answer: <input type=\"text\" name= \"question"+ pageNum +"\" alt=\"PictureResponse\" height=\"100\" width=\"100\" /><br>");
							break;
					}					
				
				*/
				%>
				<button type="submit">Submit Quiz</button>
			</form>
			</div>
		
	</div>
</body>
</html>