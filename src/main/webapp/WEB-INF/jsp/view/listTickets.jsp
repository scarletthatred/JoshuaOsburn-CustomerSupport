<html>
<head>
    <title>Tickets List</title>
</head>
<body>
<a href="<c:url value='/logout'/>">Logout</a>

<h2>Tickets</h2>
<a href="<c:url value='/ticket/create'/>">Create Ticket</a>
<br>

<c:choose>
    <c:when test="${ticketDatabase.size()==0}">
        <p>There Are No Tickets Yet...</p>
    </c:when>
    <c:otherwise>
    <c:forEach var="ticket" items="${ticketDatabase}">
        Ticket#: <c:out value="${ticket.id}"/>
        <a href="<c:url value='/ticket/view/${ticket.id}'/>">
            <c:out value="${ticket.customerName}"/></a>
        <br>
    </c:forEach>
    </c:otherwise>
</c:choose>
</body>
</html>