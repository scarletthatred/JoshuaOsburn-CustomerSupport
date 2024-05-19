package com.example.customersupport;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "ticket", value="/ticket")
@MultipartConfig(fileSizeThreshold = 5_242_880, maxFileSize = 20971520L, maxRequestSize = 41_943_40L)

public class TicketServlet extends HttpServlet{
    private volatile int TICKET_ID = 1;
    private Map<Integer, Ticket> ticketDB = new LinkedHashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        String action = req.getParameter("action");
        if (action == null){
            action = "ticket";
        }
        switch(action){
            case "createTicket" -> showTicketForm(req,resp);
            case "view" -> viewTicket(req,resp);
            case "downloadAttachment" -> downloadAttachment(req,resp);
            default ->listTickets(req,resp);
        }
    }

    private void listTickets(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        //heading and link to create ticket
        out.println("<html><body><h2>Tickets</h2>");
        out.println("<a href=\"ticket?action=createTicket\">Create Ticket</a><br><br>");

        //list out the ticket
        if(ticketDB.size()==0){
            out.println("There Are No Tickets Yet...");
        }
        else {
            for (int id : ticketDB.keySet()){
                Ticket ticket = ticketDB.get(id);
                out.println("Ticket #" + id);
                out.println(": <a href=\"ticket?action=view&ticketId="+ id + "\">");
                out.println(ticket.getCustomerName()+"</a><br>");

            }
        }
        out.println("</body></html>");
    }

    private Ticket getTicket(String idString, HttpServletResponse response) throws ServletException, IOException{
        if(idString == null || idString.length() == 0){
            response.sendRedirect("ticket");
            return null;
        }
        try{
            int id = Integer.parseInt(idString);
            Ticket ticket = ticketDB.get(id);
            if(ticket == null){
                response.sendRedirect("ticket");
                return null;
            }
            return ticket;
        }
        catch(Exception e){
            response.sendRedirect("ticket");
            return null;
        }
    }

    private Attachment processAttachment(Part file) throws IOException{
        InputStream in = file.getInputStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        int read;
        final byte[] bytes = new byte[1024];
        while ((read = in.read(bytes)) != -1){
            out.write(bytes,0,read);
        }
        Attachment attachment = new Attachment();
        attachment.setName(file.getSubmittedFileName());
        attachment.setContents(out.toByteArray());
        return attachment;
    }

    private void downloadAttachment(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String idString = req.getParameter("ticketId");

        Ticket ticket = getTicket(idString,resp);

        String name = req.getParameter("attachment");
        if (name == null){
            resp.sendRedirect("ticket?action=view&ticketId="+idString);
        }
        Attachment attachment = (Attachment) ticket.getAttachments();
        if (attachment == null){
            resp.sendRedirect("ticket?action=view&ticketId="+idString);
            return;
        }

        resp.setHeader("Content-Disposition", "attachment; filename=" +attachment.getName());
        resp.setContentType("application/octet-stream");
        ServletOutputStream out = resp.getOutputStream();
        out.write(attachment.getContents());
    }

    private void viewTicket(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idString = req.getParameter("ticketId");
        Ticket ticket = getTicket(idString, resp);

        PrintWriter out = resp.getWriter();
        out.println("<html><body><h2>Ticket</h2>");
        out.println("<h3>Customer Name:"+ticket.getCustomerName()+ "</h3>");
        out.println("<p>Event name:"+ticket.getSubject()+"</p>");
        out.println("<p>"+ticket.getBodyTicket()+"</p>");
        if(ticket.hasAttachment()){
            out.println("<a href= \"ticket?action=downloadAttachment&ticketId="+idString+"&attachment="
                    +ticket.getAttachments().getName()+"\">"
                    +ticket.getAttachments().getName()+"</a><br><br>");
//            out.println("<a href=\"ticket\">this is the link</a>");
        }

        out.println("<a href=\"ticket\">Return To Ticket List</a>");
        out.println("</body></html>");
    }

    private void showTicketForm(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        PrintWriter out = resp.getWriter();

        out.println( "<html lang=\"en\"><body><h2>Create a Ticket</h2>");
        out.println("<form method=\"POST\" action=\"ticket\" enctype=\"multipart/form-data\">");
        out.println("<input type=\"hidden\" name = \"action\" value = \"create\">");
        out.println("First and Last Name:<br>");
        out.println("<input type=\"text\" name=\"customerName\"><br><br>");
        out.println("Name Of the Event:<br>");
        out.println("<input type=\"text\" name=\"subject\"><br><br>");
        out.println("Event details:<br>");
        out.println("<textarea name=\"bodyTicket\" rows=\"25\" cols=\"100\"></textarea><br><br>");
        out.println("<b>Attachments</b><br>");
        out.println("<input type=\"file\" name=\"attachments\"><br><br>");
        out.println("<input type=\"submit\" value=\"Submit\">");
        out.println("</form></body></html>");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        String action = req.getParameter("action");

        if (action == null){
            action = "ticket";
        }
        switch(action){
            case "create" -> createTicket(req,resp);
            default ->resp.sendRedirect("ticket");
        }
    }

    private void createTicket(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //creates the ticket
        Ticket ticket = new Ticket();
        ticket.setCustomerName(req.getParameter("customerName"));
        ticket.setSubject(req.getParameter("subject"));
        ticket.setBodyTicket(req.getParameter("bodyTicket"));

        Part file = req.getPart("attachments");
        if(file != null){
            Attachment attachment = this.processAttachment(file);
            if(attachment != null){
                ticket.setAttachments(attachment);
            }
        }
        int id;
        synchronized (this){
            id = this.TICKET_ID++;
            ticketDB.put(id,ticket);
        }
        System.out.println(ticket);
        resp.sendRedirect("ticket?action=view&ticketId="+id);
    }
}
