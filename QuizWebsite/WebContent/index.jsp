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
%>
</title>

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
<div id="main">

<div id="navigation" style="width:100%">
<ul>
	<li> <a class="active" href="index.jsp">Home</a></li>
	<li> <a href="#createquiz">Create Quiz</a></li>
	<li> <a href="#browsequiz">Browse Quizzes</a></li>
	<li> <a href="#about">About</a></li>
	<li style="float:right">
		<ul style="float:right; list-style-type:none;">
			<li> <a href="login.html">Login</a></li>
			<li> <a href="createaccount.html">Make an Account</a></li>
			<li> <a href="#signout">Log out</a></li>
		</ul>
	</li>
</ul>
</div>

<br>
<br>
<h1 style="color:black" align="center"> Quiz08 </h1>

<div id="announcements">
	<h3 align="center"> Announcements </h3>
	<ul>
		<li>Announcement 1</li>
		<li>Announcement 2</li>
	</ul>
</div>

<br>

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
			<h3>Enter list of popular quizzes...</h3>
		</div>
		
		<div id ="recentquiz" class ="tab">
			<h3> Enter list of recent quizzes...</h3>
		</div>
		
		<div id="randomquiz" class="tab">
			<h3> Enter list of random quizzes...</h3>
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
			<h3>Enter list of your recently created quizzes...</h3>
		</div>
		
		<div id ="yourquiztaken" class ="tab">
			<h3> Enter list of your recently taken quizzes...</h3>
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
				</ul>
			</td>
		</tr>	
	</table>
</div>

<!--  
<div class="panel">
<h1>Announcements</h1>
</div>
<div class="panel">
<h1>Popular Quizzes</h1>
</div>
<div class="panel">
<h1>New Quizzes</h1>
</div>
</div>
-->
<div class="column standard-width"> <!-- Right column -->
<!--  
<div class="panel">
<h1>My Recent Scores</h1>
</div>
<div class="panel">
<h1>My Recently Created Quizzes</h1>
</div>
<div class="panel">
<h1>Achievements</h1>
</div>
</div>
-->
</div>
</div>
</body>
</html>

