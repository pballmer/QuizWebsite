<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="java.util.*"
    import="db.*"
    import="entities.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz Website
<%
String name = (String)session.getAttribute("name");
if (name != null) out.println(" - " + name);
%></title>

<link rel="stylesheet" type="text/css" href="main.css">
<% 
	ServletContext context = pageContext.getServletContext();
	DBConnection conn = (DBConnection) context.getAttribute("Database Connection");
%>


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
	</div>"
	<div id ="content">
		<div id= "box">
			<h3 style="text-align: center;"> Announcements </h3>
			<%
				ArrayList<String> announcements = AnnounceHelper.getAnnouncements(conn, 5);
				
				for (int i = 0; i < announcements.size(); i++)
				{
					out.println("<li>" + announcements.get(i) + "</li>");
					out.println("<br>");
				}
			%>
		</div>
		
		
		<h2> Sitewide Quiz Activity </h2>
		
		
		<div id="tabs">
			<div class="tab-nav">
				<ul>
					<li class = "active"><a href="#popularquiz"> Popular Quizzes </a></li>
					<li><a href="#recentquiz"> Recent Quizzes </a> </li>
				</ul>	
			</div>
			<div class="tab-content">
				<div id="popularquiz" class = "tab active">
					<%
					ArrayList<Quiz> popularQuizzes = QuizHelper.getPopularQuizzes(conn, 10);
					for (int i= 0; i < popularQuizzes.size(); i++)
					{
						Quiz quiz = popularQuizzes.get(i);
						String quizName = quiz.getName();
						int quizID = quiz.getId();
						String description = quiz.getDescription();
						
						out.println("<b><a href=\"quizsummary.jsp?id=" + quizID + "\">" + quizName + "</a></b>");
						out.println("<p>" + description + "</p>");
						out.println("<br>");
					}
				%>
			</div>
			
			<div id ="recentquiz" class ="tab">
				<%
					ArrayList<Quiz> recentQuizzes = QuizHelper.getRecentQuizzes(conn, 10);
					for (int i= 0; i < recentQuizzes.size(); i++)
					{
						Quiz quiz = recentQuizzes.get(i);
						String quizName = quiz.getName();
						int quizID = quiz.getId();
						String description = quiz.getDescription();
						
						out.println("<b><a href=\"quizsummary.jsp?id=" + quizID + "\">" + quizName + "</a></b>");
						out.println("<p>" + description + "</p>");
						out.println("<br>");
					}
				%>
				</div>
			</div>
		</div>
		
		<%if (name != null)
		{
			out.println("<h2>Your Quiz Activity</h2>");
			out.println("<div id=\"tabs\">");
			out.println("<div class=\"tab-nav\">");
			out.println("<ul>");
			out.println("<li class = \"active\"><a href=\"#yourquizcreated\"> Your Recently Created Quizzes</a></li>");
			out.println("<li><a href=\"#yourquiztaken\">Your Recently Taken Quizzes</a> </li>");
			out.println("</ul>");
			out.println("</div>");
			
			out.println("<div class=\"tab-content\">");
				out.println("<div id=\"yourquizcreated\" class = \"tab active\">");
					ArrayList<Quiz> quizzesMade = QuizHelper.getQuizzesMade(conn, name, -1);
					for (int i = 0; i < quizzesMade.size(); i++)
					{
						Quiz quiz = quizzesMade.get(i);
						String quizName = quizzesMade.get(i).getName();
						int quizID = quizzesMade.get(i).getId();
						String description = quizzesMade.get(i).getDescription();
						
						out.println("<b><p>" + quizName + "</p></b>");
						out.println("<p>" + description + "</p>");
						out.println("<br>");
					}
				out.println("</div>");
				
				out.println("<div id =\"yourquiztaken\" class =\"tab\">");
				ArrayList<Quiz> quizzesTaken = QuizHelper.getQuizzesTaken(conn, name, -1);
					for (int i = 0; i < quizzesTaken.size(); i++)
					{
						Quiz quiz = quizzesTaken.get(i);
						String quizName = quiz.getName();
						int quizID = quiz.getId();
						String description = quiz.getDescription();
						double score = QuizHelper.getScore(conn, quizID, name);
						
						out.println("<b><p>" + quizName + "</p></b>");
						out.println("<p>" + description + "</p>");
						out.println("<p>Score: " + score + "</p>");
						out.println("<br>");
					}
				out.println("</div>");
		
			out.println("</div>");
		out.println("</div>");
		
		out.println("<br> <br>");
		out.println("<div id=\"miscinfo\">");
			out.println("<table style=\"width:100%\">");
				out.println("<tr>");
					String[] headerNames = {"Achievements", "Messages", "Friend Activity"};
					for (int i = 0; i < headerNames.length; i++)
					{
						out.println("<td class = \"header\">");
						out.println("<h3 align=\"center\">" + headerNames[i] + "</h3>");
						out.println("</td>");
					}
				out.println("</tr>");	
				out.println("<tr>");
					out.println("<td>");
						out.println("<ul>");
						ArrayList<String> achievements = UserHelper.getAchievements(conn, name);
						for (int i = 0; i < achievements.size(); i++)
						{
							out.println("<li>" + achievements.get(i) + "</li>");
						}
					out.println("</ul>");
					out.println("</td>");
					out.println("<td>");
							ArrayList<Challenge> challenges = NotificationsHelper.getUnreadChallenges(conn, name);
							ArrayList<Note> notes = NotificationsHelper.getUnreadNotes(conn, name);
							ArrayList<String> friends = NotificationsHelper.getPendingFrendRequests(conn, name);
							
							out.println("<h5> You have " + friends.size() + " NEW Friend Requests </h5>");
							for (int i = 0; i < friends.size(); i++)
							{
								out.println("<p><a href = \"user.jsp?id=" + friends.get(i) + "\">" + friends.get(i) + "</a> <a href=\"friendreq.jsp?id=" + friends.get(i) + "&type=accept\">Accept.</a> <a href=\"friendreq.jsp?id=" + friends.get(i) + "&type=reject\">Reject.</a>");
								out.println("<br>");
							}
							
							out.println("<h5> You have " + challenges.size() + " NEW Challenges </h5>");
							
							for (int i = 0; i < challenges.size(); i++)
							{
								Challenge challenge = challenges.get(i);
								String sender = challenge.getSenderName();
								String link = challenge.getQuizLink();
								double score = challenge.getScore();
								Quiz quiz = QuizHelper.getQuizByID(conn, challenge.getQuizID());
								out.println("<a href=\"user.jsp?id=" + sender + "\">" + sender + "</a> has sent you a <a href=\"challenge.jsp?id=" + challenge.getID() + "&type=read&to="+ sender + "\"> challenge.</a>");	
								out.println("<br>");
							}
							
							out.println("<h5> You have " + notes.size() + " NEW Notes </h5>");
							
							for (int i = 0; i < notes.size(); i++)
							{
								Note note = notes.get(i);
								String sender = note.getSenderName();
								out.println("<p><a href=\"user.jsp?id=" + sender + "\">" + sender + "</a> has sent you a <a href=\"note.jsp?id=" + note.getID() + "&type=read&to=" + sender + "\"> Note </a>");
								out.println("<br>");
							}
					out.println("</td>");
					out.println("<td>");
						out.println("<ul>");
							ArrayList<String> friendNames = UserHelper.getFriends(conn, name);
							for (int i = 0; i < friendNames.size(); i++)
							{
								String friendName = friendNames.get(i);
								ArrayList<Quiz> recentQuizTaken = QuizHelper.getQuizzesTaken(conn, friendName, 3);
								ArrayList<Quiz> recentQuizMade = QuizHelper.getQuizzesMade(conn, friendName, 3);
								ArrayList<String> recAchieves = UserHelper.getAchievements(conn, friendName);
								
								out.println("<b><p> Your friend <a href=\"user.jsp?id=" + friendName + "\">" + friendName + "</a> has </p></b>");
								
								out.println("<p>Taken the following quizzes: </p>");
								out.println("<ul>");
								for (int j = 0; j < recentQuizTaken.size(); j++)
								{
									Quiz quiz = recentQuizTaken.get(j);
									String quizName = quiz.getName();
									int quizID = quiz.getId();
									String description = quiz.getDescription();
									
									out.println("<li><a href=\"quizsummary.jsp?id=" + quizID + "\">" + quizName + "</a></li>");
								}
								out.println("</ul>");
								
								out.println("<p>Made the following quizzes: </p>");
								out.println("<ul>");
								for (int j = 0; j < recentQuizMade.size(); j++)
								{
									Quiz quiz = recentQuizMade.get(j);
									String quizName = quiz.getName();
									int quizID = quiz.getId();
									String description = quiz.getDescription();
									
									out.println("<li><a href=\"quizsummary.jsp?id=" + quizID + "\">"  + quizName + "</a></li>");
								}
								out.println("</ul>");
								
								out.println("<p>Got the following achievements: </p>");
								out.println("<ul>");
								for (int j = 0; j < recAchieves.size(); j++)
								{
									out.println("<li>" + recAchieves.get(j) + "</li>");
								}
								out.println("</ul>");
							}	
						out.println("</ul>");
					out.println("</td>");
				out.println("</tr>");	
			out.println("</table>");
		out.println("</div>");
				
			out.println("</div>");
			
		}%>
		
			</div>
		</div>
</body>
</html>
