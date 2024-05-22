<html>
<head><title>Ticket #<c:out value="${ticketId}"/> </title></head>
<body>
<a href="<c:url value='/logout'/>">Logout</a>

<h2>Ticket #<c:out value="${ticketId}"/> </h2>
<h3>Customer Name:<c:out value="${ticket.customerName}"/></h3>
<p>Event name:<c:out value="${ticket.subject}"/></p>
<p><c:out value="${ticket.bodyTicket}"/></p>
<%--        <c:out value="${ticket.attachments.name}"/>--%>

<c:if test="${ticket.hasAttachment()}">
    <a href="<c:url value='/ticket/${ticketId}/attachment/${ticket.attachment.name}'/>">
        <c:out value="${ticket.attachment.name}"/>
    </a><br>
</c:if>
<br>
<a href="<c:url value="/ticket/list"/>">Return To Ticket List</a>
</body>
</html>