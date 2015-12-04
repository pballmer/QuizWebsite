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
					System.out.println("This is the URL " + request.getRequestURL());
					String id = request.getParameter("id").toString();
					DBConnection dbconn = new DBConnection();
					Quiz quiz = QuizHelper.getQuizByID(conn, Integer.parseInt(id));
					//QuizHelper.addQuizToTake(conn, quiz, name);
					//System.out.println(quiz.getQuestions().size());
				%>
				
	<div id ="content">

	
		<h1> You're Taking <%=quiz.getName() %></h1>
		<form action="quizResults.jsp">
		
			<%
/* 			ArrayList<String> mcArray = new ArrayList<String>();
			ArrayList<String> fbArray = new ArrayList<String>();
			ArrayList<String> answer = new ArrayList<String>();
			answer.add("A");
			mcArray.add("A");
			mcArray.add("B");
			mcArray.add("C");
			mcArray.add("D");
			fbArray.add("The letter ");
			fbArray.add(" is the first letter of the alphabet.");
			String mcQ = "Which is A?";
			//COLIN WILL FIX BELOW
			//MultipleChoice mc1 = new MultipleChoice(1, 2, mcQ, answer, QuestionHelper.MULTIPLE_CHOICE, mcArray);
			QuestionResponse qr1 = new QuestionResponse(2,"What's the first letter of the alphabet?", answer.get(0));
			FillBlank fb1 = new FillBlank(3, "The first letter of the alphabet is ", "", answer.get(0));
			PictureResponse pr1 = new PictureResponse(4, "http://www.havefunteaching.com/wp-content/uploads/2013/06/letter-a.png"
					, answer.get(0));

			ArrayList<QuestionAbstract> questions = new ArrayList<QuestionAbstract>();
			//questions.add(mc1);
			questions.add(pr1);
			questions.add(fb1);
			questions.add(qr1); */
			
			for(int i = 0 ; i < quiz.getQuestions().size(); i++){
				/*
				for(int i = 0 ; i < questions.size(); i++){
					
					switch (questions.get(i).getType())
					{
						case QuestionHelper.MULTIPLE_CHOICE:
							MultipleChoice mc = (MultipleChoice) questions.get(i);
							//Colin's going to fix this so it's correct
							//out.println("<h2>" + mc.getText() + "<h2/>"); 
							for(int j = 0; j < mc.getOptions().size(); j++){
								out.println("<input type=\"radio\" id=\""+ mc.getQuestionID() +  "" +"\">" + mc.getOptions().get(j) +"<br>"); 
								System.out.println("j is " + j);
							}
							continue;
						case QuestionHelper.QUESTION_RESPONSE:
							QuestionResponse questionResponse = (QuestionResponse)questions.get(i);
							out.println("<h2>" + questionResponse.getText() + "<h2/>");
							out.println("Answer: <input type=\"text\" id= "+questionResponse.getQuestionID()  +"><br>");	
							continue;
						case QuestionHelper.FILL_IN_BLANK:
							FillBlank fillBlank = (FillBlank)questions.get(i);
							out.print("<h2>" +  fillBlank.getTextBefore() + "<h2>");
							out.print("<input type=\"text\" id= "+ fillBlank.getQuestionID()  +">");
							out.print("<h2>" +  fillBlank.getTextAfter() + "</h2>");	
							continue;
						case QuestionHelper.PICTURE_RESPONSE:
							PictureResponse pr = (PictureResponse)questions.get(i);
							out.println("<img src="+ pr.getText() +"><br>");
							out.println("Answer: <input type=\"text\" id= "+ pr.getQuestionID()  +" alt=\"PictureResponse\" height=\"100\" width=\"100\"><br>");
							continue;*/

				switch (quiz.getQuestions().get(i).getType())
					{
						case QuestionHelper.MULTIPLE_CHOICE:
							MultipleChoice mc = (MultipleChoice) quiz.getQuestions().get(i);
							out.println("<h2>" + mc.getText() + "<h2/>"); 
							for(int j = 0; j < mc.getOptions().size(); j++){
								out.println("<input type=\"radio\" id=\""+ mc.getQuestionID() +  "" +"\">" + mc.getOptions().get(j) +"<br>"); 
								System.out.println("j is " + j);
							}
							continue;
						case QuestionHelper.QUESTION_RESPONSE:
							QuestionResponse questionResponse = (QuestionResponse)quiz.getQuestions().get(i);
							out.println("<h2>" + questionResponse.getText() + "<h2/>");
							out.println("Answer: <input type=\"text\" id= "+questionResponse.getQuestionID()  +"><br>");	
							continue;
						case QuestionHelper.FILL_IN_BLANK:
							FillBlank fillBlank = (FillBlank)quiz.getQuestions().get(i);
							out.print("<h2>" +  fillBlank.getTextBefore() + "<h2>");
							out.print("<input type=\"text\" id= "+ fillBlank.getQuestionID()  +">");
							out.print("<h2>" +  fillBlank.getTextAfter() + "</h2>");	
							continue;
						case QuestionHelper.PICTURE_RESPONSE:
							PictureResponse pr = (PictureResponse)quiz.getQuestions().get(i);
							out.println("<img src="+ pr.getText() +"><br>");
							out.println("Answer: <input type=\"text\" id= "+ pr.getQuestionID()  +" alt=\"PictureResponse\" height=\"100\" width=\"100\"><br>");
							continue;
						
					}					
				}
			%>
			<button type="submit">Submit Quiz</button>
		</form>
	</div>
</body>
</html>