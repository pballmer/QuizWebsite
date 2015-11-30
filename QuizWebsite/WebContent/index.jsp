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
</head>
<body>
<<<<<<< HEAD
<h1>Hi This is a QuizWebSite!</h1>
=======
<div id="main">

<div id="navbar">
Navigation?
<!-- Make this an if to see if logged in. also maybe separate page entirely if logged in -->
<a href="login.html">Log in</a>
</div>

<div class="column wide"> <!-- Left column -->

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

<div class="column standard-width"> <!-- Right column -->
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

</div>
>>>>>>> master
</body>
</html>