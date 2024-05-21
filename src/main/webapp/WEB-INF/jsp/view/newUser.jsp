<html>
<head>
    <title>New User</title>
</head>
<body>
<h2>Login</h2>
Please create your Log in info and keep it saved somewhere safe<br><br>
<c:if test="${loginFailed == true}">
    <b><c:out value="The username you have entered is already in the system"/> </b><br>
</c:if>

<form method="post" action="<c:url value='/login'/>">
    Username: <input type="text" name="username"><br><br>
    Password: <input type="password" name="password"><br><br>
    <input type="submit" name="submit" value="New User">
</form>
</body>
</html>
