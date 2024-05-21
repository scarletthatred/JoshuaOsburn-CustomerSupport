<html>
<head>
    <title>Ticket Session Admin View</title>
</head>
<body>
<a href="<c:url value='/logout'/>">Logout</a>

<h2>Sessions</h2>
There are a total of <c:out value="${numSessions}"/> active sessions going on.
<ul>
    <c:forEach items="${sessionList}" var="s">
        <li>
            <c:out value="${s.id} - ${s.getAttribute('username')} - Last active ${(now-s.getLastAccessedTime())/1000} seconds ago"/>
        </li>
    </c:forEach>
</ul>
</body>
</html>
