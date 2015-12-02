<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz Website
<%
String name = (String)session.getAttribute("name");
if (name != null) out.println(" - " + name);
%></title>

<link rel="stylesheet" type="text/css" href="main2.css">


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
		<p style="color: white; margin: 0;"> No account? <a href="createaccount.jsp">Sign up. </a></p>
		<div id = "profpic">
		</div>
		<div id = "sidebar-content">
		
			<%if (name != null)
				{
				out.println("<h1>Welcome, " + name + ".</h1>");
				out.println("<a href=\"#createQuiz\" class=\"reg-button\">Create Quiz</a>");
				out.println("<a href=\"#browseQuiz\" class=\"reg-button\"> Browse Quiz</a>");
				out.println("<br>");
				out.println("<a href =\"#browseUser\" class = \"reg-button\"> Browse Users</a>");
				out.println("<a href =\"#history\" class = \"reg-button\"> Quiz History</a>");
				out.println("<br>");
				out.println("<a href = \"#logout\" class =\"big-button\"> Log out</a>");
				}
				else{
					out.println("<h1>Welcome, " + "stranger. </h1>");
					out.println("<h3> Please <a href=\"login.jsp\" class=\"reg-button\"> login.</a></h3>");
				}
			%>
			

		</div>
		
	</div>
	
	<div id = "logo">
		<h1> Quiz08 </h1>
	</div>
	
	<div id = "filler">
	</div>"
	<div id ="content">
		<div id= "announcements">
			<h3 style="text-align: center;"> Announcements </h3>
			<ul>
			
				<li> Announcement 1</li>
				<li> Announcement 2 </li>
				<li> Announcement 3 </li>
				<li> Announcement 4 </li>
				<li> Announcement 5 </li>
			</ul>
		</div>
		
		
<h2> Sitewide Quiz Activity </h2>


<div id="tabs">
	<div class="tab-nav">
		<ul>
			<li class = "active"><a href="#popularquiz"> Popular Quizzes </a></li>
			<li><a href="#recentquiz"> Recent Quizzes </a> </li>
			<li><a href="#randomquiz"> Random Quizzes </a> </li>
		</ul>	
	</div>
	<div class="tab-content">
		<div id="popularquiz" class = "tab active">
			<ul>
				<li> Popular quiz 1</li>
				<li> Popular quiz 2</li>
				<li> Popular quiz 3</li>
				<li> Popular quiz 4</li>
				<li> Popular quiz 5</li>				
			</ul>
		</div>
		
		<div id ="recentquiz" class ="tab">
			<ul>
				<li> Recent quiz 1</li>
				<li> Recent quiz 2</li>
				<li> Recent quiz 3</li>
				<li> Recent quiz 4</li>
				<li> Recent quiz 5</li>				
			</ul>
		</div>
		
		<div id="randomquiz" class="tab">
			<ul>
				<li> Random quiz 1</li>
				<li> Random quiz 2</li>
				<li> Random quiz 3</li>
				<li> Random quiz 4</li>
				<li> Random quiz 5</li>				
			</ul>
		</div>
	</div>
</div>

<h2>Your Quiz Activity</h2>
<div id="tabs">
	<div class="tab-nav">
		<ul>
			<li class = "active"><a href="#yourquizcreated"> Your Recently Created Quizzes</a></li>
			<li><a href="#yourquiztaken">Your Recently Taken Quizzes</a> </li>
		</ul>	
	</div>
	
	<div class="tab-content">
		<div id="yourquizcreated" class = "tab active">
			<ul>
				<li> Quiz you created 1</li>
				<li> Quiz you created 2</li>
				<li> Quiz you created 3</li>
				<li> Quiz you created 4</li>
				<li> Quiz you created 5</li>
				<li> Quiz you created 6</li>
				<li> Quiz you created 7</li>
			</ul>
		</div>
		
		<div id ="yourquiztaken" class ="tab">
			<h3> Enter list of your recently taken quizzes...</h3>
			<ul>
				<li> Quiz you took 1</li>
				<li> Quiz you took 2</li>
				<li> Quiz you took 3</li>
				<li> Quiz you took 4</li>
				<li> Quiz you took 5</li>
				<li> Quiz you took 6</li>
				<li> Quiz you took 7</li>
			</ul>
		</div>

	</div>
</div>

<br> <br>
<div id="miscinfo">
	<table style="width:100%">
		<tr>
			<td class="header">
				<h3 align="center"> Achievements </h3>
			 </td>
			<td class="header"> 
				<h3 align="center"> Messages </h3>
			</td>
			<td class="header"> 
				<h3 align="center"> Friend Activity</h3>
			</td>
		</tr>
		<tr>
			<td>
				<ul>
					<li> Achievement 1</li>
					<li> Achievement 2</li>
					<li> Achievement 2</li>
					<li> Achievement 2</li>
					<li> Achievement 2</li>
					<li> Achievement 2</li>
					<li> Achievement 2</li>
					<li> Achievement 2</li>
					<li> Achievement 2</li>
					<li> Achievement 2</li>
					<li> Achievement 2</li>
					<li> Achievement 2</li>
					
				</ul>
			 </td>
			<td> 
				<ul>
					<li> 3 NEW Friend Requests</li>
					<li> 2 NEW Challenges </li>
					<li> 10 NEW Notes </li>
				</ul>
			</td>
			<td> 
				<ul>
					<li>Activity of Friend 1</li>
					<li>Activity of Friend 2</li>
					<li>Activity of Friend 3</li>
					<li>Activity of Friend 3</li>
					<li>Activity of Friend 3</li>
					<li>Activity of Friend 3</li>
					<li>Activity of Friend 3</li>
					<li>Activity of Friend 3</li>
					<li>Activity of Friend 3</li>
					<li>Activity of Friend 3</li>
					
				</ul>
			</td>
		</tr>	
	</table>
</div>
		
	</div>
	
	
</div>
</body>
</html>
