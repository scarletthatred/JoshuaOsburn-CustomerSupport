//package com.example.customersupport;
//
//import java.io.*;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.*;
//import jakarta.servlet.annotation.*;
//
//@WebServlet(name = "cstmerspt", value="/cstmerspt")
//public class CustomerSupport extends HttpServlet {
//    private volatile int TICKET_NUMBER = 1;
//    private Map<Integer, Attachment> ticketDB = new LinkedHashMap<>();
//
//
//@Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//    resp.setContentType("text/html");
//    String action = req.getParameter("action");
//
//    if (action == null){
//        action = "list";
//    }
//    switch(action){
//        case "listTickets"-> listTickets(req,resp);
//        case "viewTicket"->viewTicket(req,resp);
//        case "downloadAttachment"->downloadAttachment(req,resp);
//        case "showTicketForm"->showTicketForm(req,resp);
//        case "processAttachment"->processAttachment(req,resp);
//        case "getTicket"->getTicket(req,resp);
//    }
//
//    }
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("text/html");
//        String action = req.getParameter("action");
//
//        if (action == null){
//            action = "list";
//        }
//        switch(action){
//            case "create"-> createTicket(req,resp);
//            default -> resp.sendRedirect("cstmerspt");
//        }
//    }
//
//    private void getTicket(HttpServletRequest req, HttpServletResponse resp) {
//    }
//
//    private void processAttachment(HttpServletRequest req, HttpServletResponse resp) {
//
//    }
//
//    private void showTicketForm(HttpServletRequest req, HttpServletResponse resp) {
//
//    }
//
//    private void downloadAttachment(HttpServletRequest req, HttpServletResponse resp) {
//
//    }
//
//    private void createTicket(HttpServletRequest req, HttpServletResponse resp) {
//
//    }
//
//    private void viewTicket(HttpServletRequest req, HttpServletResponse resp) {
//
//    }
//
//    private void listTickets(HttpServletRequest req, HttpServletResponse resp) {
////PrintWriter out = resp.getWriter();
//
////out.println("<html?><body><h2>Ticket List</h2>");
////out.println("<a href=\"");
//    }
//
//
//
//}