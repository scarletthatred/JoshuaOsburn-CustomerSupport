package com.example.customersupport;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name="sessionListServlet", value="/sessions")
public class SessionListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getSession().getAttribute("username")==null){
            resp.sendRedirect("login");
            return;
        }

        req.setAttribute("numSessions", SessionListUtil.getNumberOfSessions());
        req.setAttribute("sessionList", SessionListUtil.getAllSessions());
        req.getRequestDispatcher("/WEB-INF/jsp/view/sessions.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
