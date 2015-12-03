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
<title> <%= request.getParameter("id") %>'s Profile Page</title>
<% String name =(String)session.getAttribute("name");
String user = (String)request.getParameter("id");
ServletContext context = pageContext.getServletContext();
DBConnection conn = (DBConnection) context.getAttribute("Database Connection");
%>
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
		<h1 style="text-align: center"> <%= user %>'s Profile Page </h1>
		<center>
		<div id = "form">
		<center>
		<% 
			if (!name.equals(user) && !UserHelper.getFriends(conn, name).contains(user))
			{
					out.println("<a href=\"friendreq.jsp?id=" + user + "&type=pending\" class = \"reg-button\"> Add Friend</a>");
			}
			else
			{
				out.println("<a href=\"friendreq.jsp?id=" + user + "&type=reject\" class = \"reg-button\"> Remove</a>");
			}
			out.println("<a href =\"note.jsp?to=" + user + "&type=send\" class = \"reg-button\"> Send Note </a>");
			out.println("<a href =\"challenge.jsp?to=" + user + "&type=send\" class = \"reg-button\"> Challenge </a>");
		%>
		</center>
		</div>
		</center>
		<% out.println("<h2> " + user + "'s Quiz Activity</h2>");
		out.println("<div id=\"tabs\">");
		out.println("<div class=\"tab-nav\">");
		out.println("<ul>");
		out.println("<li class = \"active\"><a href=\"#yourquizcreated\"> Recently Created Quizzes</a></li>");
		out.println("<li><a href=\"#yourquiztaken\">Recently Taken Quizzes</a> </li>");
		out.println("</ul>");
		out.println("</div>");
		
		out.println("<div class=\"tab-content\">");
			out.println("<div id=\"yourquizcreated\" class = \"tab active\">");
				ArrayList<Quiz> quizzesMade = QuizHelper.getQuizzesMade(conn, user, -1);
				for (int i = 0; i < quizzesMade.size(); i++)
				{
					Quiz quiz = quizzesMade.get(i);
					String quizName = quiz.getName();
					int quizID = quiz.getId();
					String description = quiz.getDescription();
					
					out.println("<h5>" + quizName + "</h5>");
					out.println("<p>" + description + "</p>");
					out.println("<br>");
				}
			out.println("</div>");
			
			out.println("<div id =\"yourquiztaken\" class =\"tab\">");
			ArrayList<Quiz> quizzesTaken = QuizHelper.getQuizzesTaken(conn, user, -1);
				for (int i = 0; i < quizzesTaken.size(); i++)
				{
					Quiz quiz = quizzesTaken.get(i);
					String quizName = quiz.getName();
					int quizID = quiz.getId();
					String description = quiz.getDescription();
					
					out.println("<h5>" + quizName + "</h5>");
					out.println("<p>" + description + "</p>");
					out.println("<br>");
				}
			out.println("</div>");

		out.println("</div>");
	out.println("</div>");
		out.println("<br><br>");
			out.println("<div id=\"form\"");
				out.println("<h3> <b>Achievements </b></h3>");
				out.println("<ul>");
					ArrayList<String> achievements = UserHelper.getAchievements(conn, user);
					for (int i = 0; i < achievements.size(); i++)
					{
						out.println("<li>" + achievements.get(i) + "</li>");
					}
				out.println("</ul>");
			out.println("</div>"); 
			out.println("<br><br>");
				out.println("<div id=\"form\"");
				out.println("<h3><b> Friends </b></h3>");
				out.println("<ul>");
					ArrayList<String> friends = UserHelper.getFriends(conn, user);
					for (int i = 0; i < friends.size(); i++)
					{
						out.println("<li>" + friends.get(i) + "</li>");
					}
				out.println("</ul>");
			out.println("</div>");%>
	</div>
</body>
</html>