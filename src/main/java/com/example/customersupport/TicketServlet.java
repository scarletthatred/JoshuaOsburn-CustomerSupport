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
        //check for login
        if(req.getSession().getAttribute("username")==null){
            resp.sendRedirect("login");
            return;
        }

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

    private void listTickets(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("ticketDatabase", ticketDB);

        req.getRequestDispatcher("WEB-INF/jsp/view/listTickets.jsp").forward(req,resp);

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
        req.setAttribute("idString",idString);
        req.setAttribute("ticket", ticket);
req.getRequestDispatcher("WEB-INF/jsp/view/viewTicket.jsp").forward(req,resp);

    }

    private void showTicketForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      req.getRequestDispatcher("WEB-INF/jsp/view/ticketForm.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //check for login
        if(req.getSession().getAttribute("username")==null){
            resp.sendRedirect("login");
            return;
        }

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
