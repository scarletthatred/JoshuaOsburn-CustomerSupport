<html>
<head>
    <title>Login page</title>
</head>
<body>
<h2>Login</h2>
Please Log in to see the Tickets<br><br>
<c:if test="${loginFailed == true}">
    <b><c:out value="The username or Password you have entered is incorrect"/> </b><br>
</c:if>
<form method="POST" action="<c:url value='/login'/>">
    Username: <input type="text" name="username"><br><br>
    Password: <input type="password" name="password"><br><br>
    <input type="submit" value="Log In">
</form>
</body>
</html>
