<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Quiz</title>
<link rel="stylesheet" type="text/css" href="main.css">
</head>
<body>
<div id="main">
<div class="panel">
<form action="QuizNameSaveServlet" method="post">
<p>Quiz Name:</p>
<p><input type="text" name="name" placeholder="New Quiz" /></p>
<p><input type="submit" value="Save"/></p>
</form>
<form action="QuizDescSaveServlet" method="post" id="descform">
<p>Description:</p>
<!--<textarea name="desc" form="descform" placeholder="Quiz Description" rows=5></textarea> Might not need the form thing -->
<textarea name="desc" placeholder="Quiz Description" rows=5></textarea>
<p><input type="submit" value="Save"/></p>
</form>
</div>

<div class="panel">
<form action="AddQuestionServlet" method="post">
<p>Add a new question:</p>
<select name="qtype">
<option value="Multiple Choice">Multiple Choice</option>
<option value="Question-Response">Question-Response</option>
<option value="Fill in the Blank">Fill in the Blank</option>
<option value="Picture-Response">Picture-Response</option>
</select>
<p><input type="submit" value="Add"/></p>
</form>
</div>

<form action="QuizCreationServlet" method="post">
<p><input type="submit" value="Finish!"/></p>
</form>
</div>
</body>
</html>