package com.example.customersupport;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

@WebServlet(name="loginServlet", value="/login")
public class LoginServlet extends HttpServlet{

    public static final Map<String, String> userDB = new Hashtable<>();
    static {
        userDB.put("Joshua","admin456");
        userDB.put("Rachel","wife123");
        userDB.put("Hiccup","CatMeow");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if(req.getParameter("logout") != null){
            //logout exists logs out
            session.invalidate();
            resp.sendRedirect("login");
            return;
        }
        else if(session.getAttribute("username")!=null){
            resp.sendRedirect("ticket");
            return;
        }
        //not logged in
        req.setAttribute("loginFailed", false);
        req.getRequestDispatcher("/WEB-INF/jsp/view/login.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      HttpSession session = req.getSession();
      //check if logged in already
        if(session.getAttribute("username")!= null){
            resp.sendRedirect("ticket");
            return;
        }
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if("New User".equals(req.getParameter("submit"))){
            if(username == null || password == null || username.isEmpty() || password.isEmpty()){
                req.setAttribute("loginFailed",true);
                req.getRequestDispatcher("/WEB-INF/jsp/view/newUser.jsp").forward(req,resp);
                return;
            }
            if(LoginServlet.userDB.containsKey(username)){
                req.setAttribute("loginFailed", true);
                req.getRequestDispatcher("/WEB-INF/jsp/view/login.jsp").forward(req,resp);
            }
            LoginServlet.userDB.put(username, password);
            session.setAttribute("username",username);
            resp.sendRedirect("ticket");
            return;
        }

        if(username == null || password == null || !LoginServlet.userDB.containsKey(username)||!password.equals(LoginServlet.userDB.get(username))){
            req.setAttribute("loginFailed", true);
            req.getRequestDispatcher("/WEB-INF/jsp/view/login.jsp").forward(req,resp);
            return;
        }


        //login is successful
        else{
            session.setAttribute("username",username);
            req.changeSessionId();//session fixation
            resp.sendRedirect("ticket");
        }
    }
}
