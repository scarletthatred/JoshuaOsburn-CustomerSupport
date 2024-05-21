<html>
<head>
    <title>Login page</title>
</head>
<body>
<h2>Login</h2>
Please Log in to see the Tickets<br><br>
<c:if test="${loginFailed == true}">
    <b><c:out value="The username or Password you have entered is incorrect or already in use"/> </b><br>
</c:if>

<form:form method="POST" action ="login" modelAttribute="loginForm">
    <form:label path="username">Username:&nbsp;</form:label>
    <form:input path="username"/><br><br>
    <form:label path="password">Password:&nbsp;</form:label>
    <form:password path="password"/><br><br>
    <input type="submit" value="Log In">
</form:form>
<a href="<c:url value='/newUser'/>">New User</a>
</body>
</html>
