<html>
<head>
    <title>Tickets List</title>
</head>
<body>
<a href="<:c:url value='/login'>
    <c:param name='logout'/>
</:c:url>">Logout</a>
<h2>Tickets</h2>
<a href="<c:url value='/ticket'>
<c:param name='action' value='createTicket'/></c:url>">Create Ticket</a>
<br>

<c:choose>
    <c:when test="${ticketDatabase.size()==0}">
        <p>There Are No Tickets Yet...</p>
    </c:when>
    <c:otherwise>
    <c:forEach var="ticket" items="${ticketDatabase}">
        Ticket#: <c:out value="${ticket.key}"/>
        <a href="<c:url value='/ticket'>
        <c:param name='action' value='view'/>
        <c:param name='ticketId' value='${ticket.key}'/>
</c:url>"><c:out value="${ticket.value.customerName}"/></a>
        <br>
    </c:forEach>
    </c:otherwise>
</c:choose>
</body>
</html>