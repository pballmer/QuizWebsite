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
<p>Name:</p>
<p><input type="text" name="name" form="submit" /></p> <!-- TODO Can I use form? -->
<p>Description:</p>
<textarea name="desc" form="submit" placeholder="Quiz Description"></textarea>
<!-- <p><input type="text" name="desc" /></p> <!-- TODO Make this field bigger --> 
</div>

<form action="QuizCreationServlet" method="post" id="submit">
<p><input type="submit" value="Finish!"/></p>
</form>
</div>
</body>
</html>