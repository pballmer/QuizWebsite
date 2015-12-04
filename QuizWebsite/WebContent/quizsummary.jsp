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
<% 
	String name =(String)session.getAttribute("name");
	String quizIdString = (String)request.getParameter("id");
	int quizId = -1;
	if (quizIdString != null)
	{
		quizId = Integer.parseInt(quizIdString);
	}
	ServletContext context = pageContext.getServletContext();
	DBConnection conn = (DBConnection) context.getAttribute("Database Connection");
	Quiz quiz = QuizHelper.getQuizByID(conn, quizId);
	String quizname = "unknown quiz";
	if (quiz != null)
	{
		quizname = quiz.getName();
	}
%>
<title> <%= quizname %>'s Profile Page</title>

<link rel="stylesheet" type="text/css" href="main.css">

<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script> 

<script type="text/javascript">
	jQuery(document).ready(function(){
		jQuery('.tab-nav a').on('click', function(e)
		{
			var link = jQuery(this).attr('href');
			jQuery('.tabs ' + link).show();
			jQuery('.tabs ' + link).siblings().hide();
			
			jQuery(this).parent('li').addClass('active');
			jQuery(this).parent('li').siblings().removeClass('active');
			
			jQuery('.tab-content ' + link).addClass('active');
			jQuery('.tab-content ' + link).siblings().removeClass('active');
			e.preventDefault();
		});	
		
		jQuery('#navigation li a').on('click', function(e)
				{
					var link = jQuery(this).attr('href');
					jQuery('#navigation li a').removeClass('active');
					jQuery(this).addClass('active');
				});
		
	});
	
</script>

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
		<h1 style="text-align: center; color:white"> <%= quizname %>'s Summary Page </h1>
		<br>
		<div id = "form">
		<center>
		<% 
			if (name.equals(QuizHelper.getQuizMaker(conn, quizId).getUsername()))
			{
				out.println("<a href=\"editquiz.jsp?id=" + quizId + "\" class = \"reg-button\"> Edit</a>");
			}
			out.println("<a href =\"takeQuiz.jsp?id=" + quizId + "\" class = \"reg-button\"> Take Quiz</a>");
		%>
	</center>
		</div>
		<h2 style="color:white"> Summary Statistics</h2>
		<div id ="form">
			<% 
			ArrayList<Double> Scores = QuizHelper.getAllScores(conn, quizId);
			double avg = 0;
			for (int i = 0; i < Scores.size(); i++)
			{
				avg += Scores.get(i);
			}
			if (Scores.size() == 0)
			{
				avg = 0;
			}
			else
			{
				avg /= Scores.size();
			}
			out.println("<p> <b>Average Score: </b>" + avg);
			out.println("<p> <b> Number of times taken: </b>" + Scores.size());
			%>
			
		
		</div>
		<h2 style="color:white"> Basic Information</h2>
		<div id = "form">
		<% 
			out.println("<p> <b>Created by:</b> " + QuizHelper.getQuizMaker(conn, quizId).getUsername() + " </p>");
			out.println("<p> <b> Description:</b> " + quiz.getDescription() + "</p>");
		%>
		</div>
		<br>
		<% 
		out.println("<div id=\"tabs\">");
		out.println("<div class=\"tab-nav\">");
		out.println("<ul>");
		out.println("<li class = \"active\"><a href=\"#alltimehigh\">All Time High Scorers</a></li>");
		out.println("<li><a href=\"#dailytop\">Top Scorers Today</a> </li>");
		out.println("<li><a href=\"#recenttop\">Recent Scores Today</a> </li>");
		out.println("<li><a href=\"#yourperformance\">Your Scores</a> </li>");
		out.println("</ul>");
		out.println("</div>");
		out.println("<div class=\"tab-content\">");
				out.println("<div id=\"alltimehigh\" class = \"tab active\">");
					HashMap<String, Double> allTimeScorers = QuizHelper.getTopScorers(conn, quizId);
					Set<String> keys = allTimeScorers.keySet();
					out.println("<ul>");
					for (String currUser: keys)
					{
						out.println("<li><b> Username: </b>" + currUser + " <b> Score: </b>" + allTimeScorers.get(currUser) + " </li>");
						
					}
					out.println("</ul>");
				out.println("</div>");
				
				out.println("<div id =\"dailytop\" class =\"tab\">");
					Date date = new Date();
					int year = date.getYear()+1900;
					int month = date.getMonth()+1;
					int day = date.getDate();
					String today = year + "-" + month + "-" + day + " 00:00:00";
					String tomorrow = year + "-" + month + "-" + (day+1) + " 00:00:00";
					HashMap<String, Double> scores = QuizHelper.getDailyTopScorers(conn, quizId, today, tomorrow);
					Set<String> dailyScorers = scores.keySet();
					out.println("<ul>");
						for (String username: dailyScorers)
						{
							out.println("<li><b> Username: </b>" + username + " <b> Score: </b>" + scores.get(username) + " </li>");
							
						}
					out.println("</ul>");
				out.println("</div>");
				
				out.println("<div id =\"recenttop\" class =\"tab\">");
					HashMap<String, Double> recent = QuizHelper.getRecentDailyScorers(conn, quizId, today, tomorrow);
					Set<String> recentkeys = scores.keySet();
					out.println("<ul>");
						for (String username: dailyScorers)
						{
							out.println("<li><b> Username: </b>" + username + " <b> Score: </b>" + recent.get(username) + " </li>");
							
						}
				out.println("</ul>");
				out.println("</div>");
				
				out.println("<div id =\"yourperformance\" class =\"tab\">");
				ArrayList<Double> yourScore = QuizHelper.getScores(conn, quizId, name);
				out.println("<ul>");
					for (int i = 0; i < yourScore.size(); i++)
					{
						out.println("<li>" + yourScore.get(i) + "</li>");
					}
				out.println("</ul>");
				out.println("</div>");
		
			out.println("</div>");
		out.println("</div>");
		out.println("</div");
		%>
	</div>
</body>
</html>