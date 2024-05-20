<html>
<head>
    <title>
        Create a New Ticket
    </title>
</head>
<body>
<a href="<c:url value='/login'>
    <c:param name='logout'/>
</c:url>">Logout</a>
    <h2>Create a Ticket</h2>
    <form method="POST" action="ticket" enctype="multipart/form-data">
        <input type="hidden" name="action" value="create">
        First and Last Name:<br>
        <input type="text" name="customerName"><br><br>
        Name Of the Event:<br>
        <input type="text" name="subject"><br><br>
        Event details:<br>
        <textarea name="bodyTicket" rows="25" cols="100"></textarea><br><br>
        <b>Attachments</b><br>
        <input type="file" name="attachments"><br><br>
        <input type="submit" value="Submit">
    </form>
</body>
</html>
