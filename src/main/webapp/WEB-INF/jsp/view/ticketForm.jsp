<html>
<head>
    <title>
        Create a New Ticket
    </title>
</head>
<body>
<a href="<c:url value='/logout'/>">Logout</a>
    <h2>Create a Ticket</h2>

<form:form method="POST" action="create" modelAttribute="ticket" enctype="multipart/form-data">
    <form:label path="customerName">Name:</form:label><br>
    <form:input path="customerName"/><br><br>
    <form:label path="subject">Event Subject:</form:label><br>
    <form:input path="subject"/><br><br>
    <form:label path="bodyTicket">Event Information</form:label><br>
    <form:textarea path="bodyTicket"/><br><br>
    <b>Attachments</b><br>
    <form:input path="attachment" type="file"/><br><br>
    <input type="submit" value="Submit">
    </form:form>
</body>
</html>
