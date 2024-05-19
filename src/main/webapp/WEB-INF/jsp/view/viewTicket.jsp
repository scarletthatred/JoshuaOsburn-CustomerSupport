<html>
<head><title>Ticket #<c:out value="${idString}"/> </title></head>
<body><h2>Ticket #<c:out value="${idString}"/> </h2>
<h3>Customer Name:<c:out value="${ticket.getCustomerName()}"/></h3>
<p>Event name:<c:out value="${ticket.getSubject()}"/></p>
<p><c:out value="${ticket.getBodyTicket()}"/></p>
<c:if test="${ticket.hasAttachment()}">
    <a href="<c:url value='/ticket' >
    <c:param name='action' value='downloadAttachment'/>
    <c:param name='ticketId' value='${idString}'/>
    <c:param name='attachment' value='${ticket.getAttachments().getName()}'/>
</c:url>"><c:out value="${ticket.getAttachments().getName()}"/> </a><br>
</c:if>
<br>
<a href="ticket">Return To Ticket List</a>
</body>
</html>