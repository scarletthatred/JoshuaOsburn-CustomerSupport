<%
    @SuppressWarnings("unchecked")
     Ticket ticket = (Ticket)request.getAttribute("ticket");
    String idString = (String)request.getAttribute("idString");
%>

<html>
    <body><h2>Ticket</h2>
    <h3>Customer Name:<%=ticket.getCustomerName()%></h3>
    <p>Event name:<%=ticket.getSubject()%></p>
    <p><%=ticket.getBodyTicket()%></p>
    <% if(ticket.hasAttachment()){ %>
    <b><a href="ticket?action=downloadAttachment&ticketId=<%=idString%>&attachment=<%=ticket.getAttachments().getName()%>">
    <%=ticket.getAttachments().getName()%> </a></b><br><br>
    <%}%>
    <a href="ticket">Return To Ticket List</a>
    </body>
</html>